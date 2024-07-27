package com.example.healthcare.application.exercise.service;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.account.domain.code.AuthorityType;
import com.example.healthcare.application.account.domain.code.UserStatus;
import com.example.healthcare.application.account.helper.UserHelper;
import com.example.healthcare.application.account.repository.UserRepository;
import com.example.healthcare.application.common.exception.AuthException;
import com.example.healthcare.application.common.exception.AuthException.AuthExceptionCode;
import com.example.healthcare.application.common.exception.ResourceException;
import com.example.healthcare.application.common.exception.ResourceException.ResourceExceptionCode;
import com.example.healthcare.application.exercise.controller.dto.CreateExerciseDTO;
import com.example.healthcare.application.exercise.controller.dto.SearchExerciseDTO;
import com.example.healthcare.application.exercise.controller.dto.UpdateExerciseDTO;
import com.example.healthcare.application.exercise.domain.Exercise;
import com.example.healthcare.application.exercise.domain.ExerciseTypeRelation;
import com.example.healthcare.application.exercise.domain.code.ExerciseType;
import com.example.healthcare.application.exercise.exception.ExerciseException;
import com.example.healthcare.application.exercise.exception.ExerciseException.ExerciseExceptionCode;
import com.example.healthcare.application.exercise.helper.ExerciseHelper;
import com.example.healthcare.application.exercise.repository.ExerciseRepository;
import com.example.healthcare.application.exercise.repository.ExerciseTypeRelationRepository;
import com.example.healthcare.application.exercise.repository.param.SearchExerciseParam;
import com.example.healthcare.application.vo.ExerciseDetailVO;
import com.example.healthcare.application.vo.ExerciseVO;
import com.example.healthcare.infra.config.security.user.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExerciseCmService {

  private final UserHelper userHelper;
  private final ExerciseHelper exerciseHelper;
  private final ExerciseRepository exerciseRepository;
  private final ExerciseTypeRelationRepository exerciseTypeRelationRepository;
  private final UserRepository userRepository;

  @Transactional
  public void createExercise(LoginUser loginUser, CreateExerciseDTO dto) {
    User user = userRepository.findByIdAndUserStatusIs(loginUser.getId(), UserStatus.ACTIVATED)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    exerciseHelper.createExercise(user, dto);
  }

  public Page<ExerciseVO> getExercises(LoginUser loginUser, SearchExerciseDTO dto) {
    User user = userRepository.findByIdAndUserStatusIs(loginUser.getId(), UserStatus.ACTIVATED)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    return exerciseRepository.findAll(
      SearchExerciseParam.valueOf(dto, user.getId(), user.getAuthorityType()),
      dto.toPageRequest());
  }

  public ExerciseDetailVO getExercise(LoginUser loginUser, Long exerciseId) {
    User user = userRepository.findByIdAndUserStatusIs(loginUser.getId(), UserStatus.ACTIVATED)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    Exercise exercise = exerciseRepository.findById(exerciseId)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    User createdUser = exercise.getCreatedUser();
    if (!(AuthorityType.SYSTEM.equals(createdUser.getAuthorityType()) || user.equals(createdUser))) {
      throw new AuthException(AuthExceptionCode.NOT_AUTHORIZED);
    }

    if (exercise.isDeleted()) {
      throw new ExerciseException(ExerciseExceptionCode.DELETED_EXERCISE);
    }

    return exerciseHelper.getExercise(exercise);
  }

  @Transactional
  public void updateExercise(LoginUser loginUser, Long exerciseId, UpdateExerciseDTO dto) {
    User user = userRepository.findByIdAndUserStatusIs(loginUser.getId(), UserStatus.ACTIVATED)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND)); // 1

    Exercise exercise = exerciseRepository.findById(exerciseId)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND)); // 2

    // 자신이 생성한 운동 종목에 대해서만 삭제 가능
    userHelper.checkAuthorization(user, exercise.getCreatedUser());

    exercise.updateExercise(dto);
    exerciseRepository.save(exercise); // 6

    // 운동 관련 기존 ExerciseTypeRelation 제거
    exerciseTypeRelationRepository.deleteAllByExerciseId(exercise.getId()); // 3

    List<ExerciseTypeRelation> relations = new ArrayList<>();
    for (ExerciseType exerciseType : dto.exerciseTypes()) {
      relations.add(ExerciseTypeRelation.createExerciseTypeRelation(exerciseType, exercise));
    }
    exerciseTypeRelationRepository.saveAll(relations); // 4, 5
  }

  @Transactional
  public void deleteExercise(LoginUser loginUser, Long exerciseId) {
    User user = userRepository.findByIdAndUserStatusIs(loginUser.getId(), UserStatus.ACTIVATED)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    Exercise exercise = exerciseRepository.findById(exerciseId)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    // 자신이 생성한 운동 종목에 대해서만 삭제 가능
    userHelper.checkAuthorization(user, exercise.getCreatedUser());

    // soft delete 로 관련 relation 은 제거하지 않음
    exerciseRepository.deleteById(exercise.getId());
  }

}
