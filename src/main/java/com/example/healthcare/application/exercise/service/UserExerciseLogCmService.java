package com.example.healthcare.application.exercise.service;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.account.domain.code.UserStatus;
import com.example.healthcare.application.account.helper.UserHelper;
import com.example.healthcare.application.account.repository.UserRepository;
import com.example.healthcare.application.common.exception.DuplicateException;
import com.example.healthcare.application.common.exception.DuplicateException.DuplicateExceptionCode;
import com.example.healthcare.application.common.exception.InvalidInputValueException;
import com.example.healthcare.application.common.exception.InvalidInputValueException.InvalidInputValueExceptionCode;
import com.example.healthcare.application.common.exception.ResourceException;
import com.example.healthcare.application.common.exception.ResourceException.ResourceExceptionCode;
import com.example.healthcare.application.exercise.controller.dto.CreateUserExerciseLogDTO;
import com.example.healthcare.application.exercise.controller.dto.CreateUserExerciseRoutineDTO;
import com.example.healthcare.application.exercise.controller.dto.UpdateUserExerciseLogDTO;
import com.example.healthcare.application.exercise.domain.Exercise;
import com.example.healthcare.application.exercise.domain.ExerciseTypeRelation;
import com.example.healthcare.application.exercise.domain.UserExerciseLog;
import com.example.healthcare.application.exercise.domain.UserExerciseRoutine;
import com.example.healthcare.application.exercise.domain.UserExerciseSet;
import com.example.healthcare.application.exercise.domain.code.ExerciseTimeType;
import com.example.healthcare.application.exercise.domain.code.ExerciseType;
import com.example.healthcare.application.exercise.exception.ExerciseException;
import com.example.healthcare.application.exercise.exception.ExerciseException.ExerciseExceptionCode;
import com.example.healthcare.application.exercise.helper.ExerciseHelper;
import com.example.healthcare.application.exercise.repository.ExerciseRepository;
import com.example.healthcare.application.exercise.repository.ExerciseTypeRelationRepository;
import com.example.healthcare.application.exercise.repository.UserExerciseLogRepository;
import com.example.healthcare.application.exercise.repository.UserExerciseRoutineRepository;
import com.example.healthcare.application.exercise.repository.UserExerciseSetRepository;
import com.example.healthcare.application.exercise.repository.param.SearchUserExerciseLogParam;
import com.example.healthcare.application.exercise.service.data.UserExerciseData.UserExerciseLogData;
import com.example.healthcare.application.vo.UserExerciseLogDetailVO;
import com.example.healthcare.application.vo.UserExerciseLogSummaryVO;
import com.example.healthcare.application.vo.UserExerciseLogVO;
import com.example.healthcare.application.vo.UserExerciseRoutineVO;
import com.example.healthcare.application.vo.UserExerciseSetVO;
import com.example.healthcare.infra.config.security.user.LoginUser;
import com.example.healthcare.util.VerifyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserExerciseLogCmService {

  private static final PageRequest DEFAULT_PAGE_REQUEST = PageRequest.of(0, VerifyUtil.MAX_PAGE_SIZE);

  private final UserHelper userHelper;
  private final ExerciseHelper exerciseHelper;
  private final UserRepository userRepository;
  private final ExerciseRepository exerciseRepository;
  private final ExerciseTypeRelationRepository exerciseTypeRelationRepository;
  private final UserExerciseLogRepository userExerciseLogRepository;
  private final UserExerciseRoutineRepository userExerciseRoutineRepository;
  private final UserExerciseSetRepository userExerciseSetRepository;

  @Transactional
  public void createExerciseLog(LoginUser loginUser, CreateUserExerciseLogDTO dto) {
    User user = userRepository.findByIdAndUserStatusIs(loginUser.getId(), UserStatus.ACTIVATED)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    // 기존 등록된 기록이 있는지 확인
    // DEFAULT ->  DEFAULT, AM, PM X
    // AM -> PM만 존재 / DEFAULT, AM X
    // PM -> AM만 존재 / DEFAULT, PM X
    if (userExerciseLogRepository.existExerciseLog(
      user, SearchUserExerciseLogParam.valueOf(dto.exerciseDate(), ExerciseTimeType.getNotAvailableTimeTypes(dto.exerciseTimeType())))) {
      throw new ExerciseException(ExerciseExceptionCode.ALREADY_CREATED_AVAILABLE_EXERCISE_LOG);
    }


    // 운동 종록(routine) + 운동 세트(set)
    UserExerciseLogData logData = new UserExerciseLogData();
    List<UserExerciseRoutine> routineEntityList = exerciseHelper.createExerciseRoutine(dto.routineDTOList(), logData);

    // 운동 기록(log)
    UserExerciseLog logEntity = UserExerciseLog.createLog(dto, user, logData);
    userExerciseLogRepository.save(logEntity);

    routineEntityList.forEach(routine -> routine.applyLog(logEntity));
    userExerciseRoutineRepository.saveAll(routineEntityList);
    userExerciseSetRepository.saveAll(logData.setEntityList);
  }

  // Paging Max Size 28 ~ 31
  public Page<UserExerciseLogVO> getExerciseLogMonthly(LoginUser loginUser, Integer year, Integer month) {
    User user = userRepository.findByIdAndUserStatusIs(loginUser.getId(), UserStatus.ACTIVATED)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    return userExerciseLogRepository.findExerciseLogMonthly(user, year, month, DEFAULT_PAGE_REQUEST);
  }

  // Paging (max 2)
  public Page<UserExerciseLogSummaryVO> getExerciseLogDaily(LoginUser loginUser, Integer year, Integer month, Integer day) {
    User user = userRepository.findByIdAndUserStatusIs(loginUser.getId(), UserStatus.ACTIVATED)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    return userExerciseLogRepository.findExerciseLogDaily(user, year, month, day, DEFAULT_PAGE_REQUEST);
  }

  // log calc info + inner (routine + set) info
  public UserExerciseLogDetailVO getExerciseLogDetail(LoginUser loginUser, Long exerciseLogId) {
    User user = userRepository.findByIdAndUserStatusIs(loginUser.getId(), UserStatus.ACTIVATED)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    UserExerciseLog userExerciseLog = userExerciseLogRepository.findById(exerciseLogId)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    userHelper.checkAuthorization(user, userExerciseLog.getUser());

    if (userExerciseLog.isDeleted()) {
      throw new ExerciseException(ExerciseExceptionCode.DELETED_EXERCISE_LOG);
    }

    // get related routine and related set
    Map<Long, List<UserExerciseSetVO>> routineSetMaps = userExerciseLogRepository.findExerciseSetAllByLog(
        userExerciseLog, DEFAULT_PAGE_REQUEST)
      .stream()
      .collect(Collectors.groupingBy(UserExerciseSetVO::routineId));

    List<UserExerciseRoutineVO> routines = userExerciseLogRepository.findExerciseRoutineAllByLog(
        userExerciseLog, DEFAULT_PAGE_REQUEST)
      .stream()
      .peek(routine -> routine.setExerciseSetList(routineSetMaps.get(routine.getRoutineId())))
      .toList();

    return UserExerciseLogDetailVO.valueOf(userExerciseLog, routines);
  }

  @Transactional
  public void updateExerciseLog(LoginUser loginUser, Long exerciseLogId, UpdateUserExerciseLogDTO dto) {
    User user = userRepository.findByIdAndUserStatusIs(loginUser.getId(), UserStatus.ACTIVATED)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    UserExerciseLog userExerciseLog = userExerciseLogRepository.findById(exerciseLogId)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    userHelper.checkAuthorization(user, userExerciseLog.getUser());

    if (userExerciseLog.isDeleted()) {
      throw new ExerciseException(ExerciseExceptionCode.DELETED_EXERCISE_LOG);
    }

    // 기존 등록된 기록이 있는지 확인
    // DEFAULT ->  DEFAULT, AM, PM X
    // AM -> PM만 존재 / DEFAULT, AM X
    // PM -> AM만 존재 / DEFAULT, PM X
    if (userExerciseLogRepository.existExerciseLog(
      user, SearchUserExerciseLogParam.valueOf(userExerciseLog, ExerciseTimeType.getNotAvailableTimeTypes(dto.exerciseTimeType())))) {
      throw new ExerciseException(ExerciseExceptionCode.ALREADY_CREATED_AVAILABLE_EXERCISE_LOG);
    }

    // 기존 관련 루틴 제거 -> 새로 생성
    userExerciseRoutineRepository.deleteAllByUserExerciseLogIdQuery(userExerciseLog.getId());

    // 운동 종록(routine) + 운동 세트(set)
    UserExerciseLogData logData = new UserExerciseLogData();
    List<UserExerciseRoutine> routineEntityList = exerciseHelper.createExerciseRoutine(dto.routineDTOList(), logData);
    routineEntityList.forEach(routine -> routine.applyLog(userExerciseLog));

    // 운동 기록(log)
    userExerciseLog.updateLog(dto, logData);
    userExerciseLogRepository.save(userExerciseLog);
    userExerciseRoutineRepository.saveAll(routineEntityList);
    userExerciseSetRepository.saveAll(logData.setEntityList);
  }

  @Transactional
  public void deleteExerciseLog(LoginUser loginUser, Long exerciseLogId) {
    User user = userRepository.findByIdAndUserStatusIs(loginUser.getId(), UserStatus.ACTIVATED)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    UserExerciseLog userExerciseLog = userExerciseLogRepository.findById(exerciseLogId)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    userHelper.checkAuthorization(user, userExerciseLog.getUser());

    if (userExerciseLog.isDeleted()) {
      throw new ExerciseException(ExerciseExceptionCode.DELETED_EXERCISE_LOG);
    }

    // UserExerciseLog Related UserExerciseRoutine delete(update)
    userExerciseRoutineRepository.deleteAllByUserExerciseLogIdQuery(userExerciseLog.getId());

    // UserExerciseLog delete(update)
    userExerciseLogRepository.deleteById(exerciseLogId);
  }

}
