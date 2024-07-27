package com.example.healthcare.application.exercise.service;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.account.domain.code.UserStatus;
import com.example.healthcare.application.account.helper.UserHelper;
import com.example.healthcare.application.account.repository.UserRepository;
import com.example.healthcare.application.common.exception.DuplicateException;
import com.example.healthcare.application.common.exception.DuplicateException.DuplicateExceptionCode;
import com.example.healthcare.application.common.exception.ResourceException;
import com.example.healthcare.application.common.exception.ResourceException.ResourceExceptionCode;
import com.example.healthcare.application.exercise.controller.dto.CreateUserExerciseLogDTO;
import com.example.healthcare.application.exercise.controller.dto.UpdateUserExerciseLogDTO;
import com.example.healthcare.application.exercise.domain.UserExerciseLog;
import com.example.healthcare.application.exercise.domain.UserExerciseRoutine;
import com.example.healthcare.application.exercise.exception.ExerciseException;
import com.example.healthcare.application.exercise.exception.ExerciseException.ExerciseExceptionCode;
import com.example.healthcare.application.exercise.helper.ExerciseHelper;
import com.example.healthcare.application.exercise.repository.ExerciseRepository;
import com.example.healthcare.application.exercise.repository.UserExerciseLogRepository;
import com.example.healthcare.application.exercise.repository.UserExerciseRoutineRepository;
import com.example.healthcare.application.exercise.repository.UserExerciseSetRepository;
import com.example.healthcare.application.exercise.service.data.UserExerciseData.UserExerciseLogData;
import com.example.healthcare.application.vo.UserExerciseLogDetailVO;
import com.example.healthcare.application.vo.UserExerciseLogSummaryVO;
import com.example.healthcare.application.vo.UserExerciseLogVO;
import com.example.healthcare.infra.config.security.user.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserExerciseLogCmService {

  private final UserHelper userHelper;
  private final ExerciseHelper exerciseHelper;
  private final UserRepository userRepository;
  private final ExerciseRepository exerciseRepository;
  private final UserExerciseLogRepository userExerciseLogRepository;
  private final UserExerciseRoutineRepository userExerciseRoutineRepository;
  private final UserExerciseSetRepository userExerciseSetRepository;

  @Transactional
  public void createExerciseLog(LoginUser loginUser, CreateUserExerciseLogDTO dto) {
    User user = userRepository.findByIdAndUserStatusIs(loginUser.getId(), UserStatus.ACTIVATED)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    Integer exerciseCount = 0;
    BigInteger totalSetCount = null;
    BigInteger totalWeight = null;
    BigInteger totalReps = null;
    BigInteger totalTime = null;

    // 운동 종록(routine) + 운동 세트(set)
    UserExerciseLogData logData = new UserExerciseLogData();

    int routineSize = dto.routineDTOList().size();
    Set<Integer> routineOrderMemo = new HashSet<>();
    List<UserExerciseRoutine> routineEntityList = dto.routineDTOList()
      .stream()
      .map(routineDTO -> {
        if (routineDTO.order() != null && routineOrderMemo.contains(routineDTO.order())) {
          throw new DuplicateException(DuplicateExceptionCode.DUPLICATE_ROUTINE_ORDER);
        }
        routineOrderMemo.add(routineDTO.order());

        return exerciseHelper.createUserExerciseRoutineAndSet(routineDTO, routineSize, logData);
      })
      .toList();

    // 운동 기록(log)
    UserExerciseLog logEntity = UserExerciseLog.createLog(dto, user, logData);
    userExerciseLogRepository.save(logEntity);

    routineEntityList.forEach(routine -> routine.applyLog(logEntity));
    userExerciseRoutineRepository.saveAll(routineEntityList);
    userExerciseSetRepository.saveAll(logData.setEntityList);
  }

  // Paging 28 ~ 31
  public Page<UserExerciseLogVO> getExerciseLogMonthly(LoginUser loginUser, Integer year, Integer month) {

    return null;
  }

  // Paging (max 2)
  public Page<UserExerciseLogSummaryVO> getExerciseLogDaily(LoginUser loginUser, Integer year, Integer month, Integer day) {

    return null;
  }

  // log calc info + inner (routine + set) info
  public UserExerciseLogDetailVO getExerciseLogDetail(LoginUser loginUser, Long exerciseLogId) {

    return null;
  }

  @Transactional
  public void updateExerciseLog(LoginUser loginUser, Long exerciseLogId, UpdateUserExerciseLogDTO dto) {
    // 삭제된 routine 없는지 확인
    // 삭제된 set 없는지 확인
  }

  @Transactional
  public void deleteExerciseLog(LoginUser loginUser, Long exerciseLogId) {
    // UserExerciseLog delete
    // UserExerciseRoutine bulk delete -> bulk update 구현
  }

}
