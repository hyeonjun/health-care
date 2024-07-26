package com.example.healthcare.application.exercise.service;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.account.domain.code.UserStatus;
import com.example.healthcare.application.account.repository.UserRepository;
import com.example.healthcare.application.common.exception.ResourceException;
import com.example.healthcare.application.common.exception.ResourceException.ResourceExceptionCode;
import com.example.healthcare.application.exercise.controller.dto.CreateExerciseDTO;
import com.example.healthcare.application.exercise.controller.dto.SearchExerciseDTO;
import com.example.healthcare.application.exercise.domain.Exercise;
import com.example.healthcare.application.exercise.domain.ExerciseTypeRelation;
import com.example.healthcare.application.exercise.domain.code.ExerciseType;
import com.example.healthcare.application.exercise.exception.ExerciseException;
import com.example.healthcare.application.exercise.exception.ExerciseException.ExerciseExceptionCode;
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
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExerciseSyService {

  private final ExerciseRepository exerciseRepository;
  private final ExerciseTypeRelationRepository exerciseTypeRelationRepository;
  private final UserRepository userRepository;

  @Transactional
  public void createExercise(LoginUser loginUser, CreateExerciseDTO dto) {
    User user = userRepository.findByIdAndUserStatusIs(loginUser.getId(), UserStatus.ACTIVATED)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    Exercise exercise = Exercise.createExercise(dto, user);
    exerciseRepository.save(exercise);

    List<ExerciseTypeRelation> relations = new ArrayList<>();
    for (ExerciseType exerciseType : dto.exerciseTypes()) {
      relations.add(ExerciseTypeRelation.createExerciseTypeRelation(exerciseType, exercise));
    }
    exerciseTypeRelationRepository.saveAll(relations);
  }

  public Page<ExerciseVO> getExercises(SearchExerciseDTO dto) {
    return exerciseRepository.findAll(
      SearchExerciseParam.valueOf(dto), dto.toPageRequest());
  }

  public ExerciseDetailVO getExercise(Long exerciseId) {
    Exercise exercise = exerciseRepository.findById(exerciseId)
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    List<ExerciseTypeRelation> exerciseTypeRelations = exerciseTypeRelationRepository.findAllByExercise(exercise);
    if (CollectionUtils.isEmpty(exerciseTypeRelations)) {
      throw new ExerciseException(ExerciseExceptionCode.NOT_FOUND_RELATION_EXERCISE_TYPE);
    }

    return ExerciseDetailVO.valueOf(exercise, exerciseTypeRelations);
  }
}
