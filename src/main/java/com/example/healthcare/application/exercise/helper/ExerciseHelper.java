package com.example.healthcare.application.exercise.helper;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.common.exception.InvalidInputValueException;
import com.example.healthcare.application.common.exception.InvalidInputValueException.InvalidInputValueExceptionCode;
import com.example.healthcare.application.common.exception.ResourceException;
import com.example.healthcare.application.common.exception.ResourceException.ResourceExceptionCode;
import com.example.healthcare.application.exercise.controller.dto.CreateExerciseDTO;
import com.example.healthcare.application.exercise.controller.dto.CreateUserExerciseRoutineDTO;
import com.example.healthcare.application.exercise.domain.Exercise;
import com.example.healthcare.application.exercise.domain.ExerciseTypeRelation;
import com.example.healthcare.application.exercise.domain.UserExerciseRoutine;
import com.example.healthcare.application.exercise.domain.UserExerciseSet;
import com.example.healthcare.application.exercise.domain.code.ExerciseType;
import com.example.healthcare.application.exercise.exception.ExerciseException;
import com.example.healthcare.application.exercise.exception.ExerciseException.ExerciseExceptionCode;
import com.example.healthcare.application.exercise.repository.ExerciseRepository;
import com.example.healthcare.application.exercise.repository.ExerciseTypeRelationRepository;
import com.example.healthcare.application.exercise.service.data.UserExerciseData.UserExerciseLogData;
import com.example.healthcare.application.exercise.service.data.UserExerciseData.UserExerciseRoutineData;
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

  public UserExerciseRoutine createUserExerciseRoutineAndSet(CreateUserExerciseRoutineDTO dto,
    int totalRoutineSize, UserExerciseLogData userExerciseLogData) {
    Exercise exercise = exerciseRepository.findById(dto.exerciseId())
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    UserExerciseRoutineData routineData = new UserExerciseRoutineData();

    int totalSetSize = dto.setDTOList().size();
    List<UserExerciseSet> setList = dto.setDTOList().stream()
      .map(setDTO -> {
        if (setDTO.serNumber() > totalSetSize) {
          throw new InvalidInputValueException(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
        }

        UserExerciseSet setEntity = UserExerciseSet.createSet(setDTO);
        routineData.update(setDTO);
        return setEntity;
      }).toList();

    if (dto.order() != null && dto.order() > totalRoutineSize) {
      throw new InvalidInputValueException(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
    }
    UserExerciseRoutine routineEntity = UserExerciseRoutine.createRoutine(dto, routineData, exercise);

    setList.forEach(set -> set.applyRoutine(routineEntity));

    userExerciseLogData.setEntityList.addAll(setList);
    userExerciseLogData.update(routineData);
    return routineEntity;
  }

}
