package com.example.healthcare.application.exercise.helper;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.common.exception.AuthException;
import com.example.healthcare.application.common.exception.AuthException.AuthExceptionCode;
import com.example.healthcare.application.exercise.controller.dto.CreateExerciseDTO;
import com.example.healthcare.application.exercise.domain.Exercise;
import com.example.healthcare.application.exercise.domain.ExerciseTypeRelation;
import com.example.healthcare.application.exercise.domain.code.ExerciseType;
import com.example.healthcare.application.exercise.exception.ExerciseException;
import com.example.healthcare.application.exercise.exception.ExerciseException.ExerciseExceptionCode;
import com.example.healthcare.application.exercise.repository.ExerciseRepository;
import com.example.healthcare.application.exercise.repository.ExerciseTypeRelationRepository;
import com.example.healthcare.application.vo.ExerciseDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExerciseHelper {

  private final ExerciseRepository exerciseRepository;
  private final ExerciseTypeRelationRepository exerciseTypeRelationRepository;

  public void checkAuthorization(User loginUser, User resourceUser) {
    if (!loginUser.equals(resourceUser)) {
      throw new AuthException(AuthExceptionCode.NOT_AUTHORIZED);
    }
  }

  @Transactional
  public void createExercise(User user, CreateExerciseDTO dto) {
    Exercise exercise = Exercise.createExercise(dto, user);
    exerciseRepository.save(exercise);

    List<ExerciseTypeRelation> relations = new ArrayList<>();
    for (ExerciseType exerciseType : dto.exerciseTypes()) {
      relations.add(ExerciseTypeRelation.createExerciseTypeRelation(exerciseType, exercise));
    }
    exerciseTypeRelationRepository.saveAll(relations);
  }

  @Transactional(readOnly = true)
  public ExerciseDetailVO getExercise(Exercise exercise) {
    List<ExerciseTypeRelation> exerciseTypeRelations = exerciseTypeRelationRepository.findAllByExercise(exercise);
    if (CollectionUtils.isEmpty(exerciseTypeRelations)) {
      throw new ExerciseException(ExerciseExceptionCode.NOT_FOUND_RELATION_EXERCISE_TYPE);
    }

    return ExerciseDetailVO.valueOf(exercise, exerciseTypeRelations);
  }

}
