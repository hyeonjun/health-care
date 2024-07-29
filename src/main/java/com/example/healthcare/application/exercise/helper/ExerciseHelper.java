package com.example.healthcare.application.exercise.helper;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.common.exception.DuplicateException;
import com.example.healthcare.application.common.exception.DuplicateException.DuplicateExceptionCode;
import com.example.healthcare.application.common.exception.InvalidInputValueException;
import com.example.healthcare.application.common.exception.InvalidInputValueException.InvalidInputValueExceptionCode;
import com.example.healthcare.application.common.exception.ResourceException;
import com.example.healthcare.application.common.exception.ResourceException.ResourceExceptionCode;
import com.example.healthcare.application.exercise.controller.dto.CreateExerciseDTO;
import com.example.healthcare.application.exercise.controller.dto.RoutineDTO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExerciseHelper {

  private final UserExerciseHelper userExerciseHelper;
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

  @Transactional
  public List<UserExerciseRoutine> createExerciseRoutine(List<? extends RoutineDTO> dto, UserExerciseLogData logData) {
    // 운동 종록(routine) + 운동 세트(set)
    List<Long> exerciseIds = dto.stream()
      .map(RoutineDTO::getExerciseId)
      .toList();
    Map<Long, Exercise> exercises = exerciseRepository.findAllByIdIn(exerciseIds)
      .stream().collect(Collectors.toMap(Exercise::getId, exercise -> exercise));
    // 각 exercise 관련 exerciseType 들 조회
    Map<Exercise, List<ExerciseTypeRelation>> exerciseTypeMap = exerciseTypeRelationRepository.findAllByExerciseIdIn(
        exercises.keySet())
      .stream()
      .collect(Collectors.groupingBy(ExerciseTypeRelation::getExercise));

    int routineSize = dto.size();
    Set<Integer> routineOrderMemo = new HashSet<>();

    return dto
      .stream()
      .map(routineDTO -> {
        if (routineOrderMemo.contains(routineDTO.getOrder())) {
          throw new DuplicateException(DuplicateExceptionCode.DUPLICATE_ROUTINE_ORDER);
        }
        routineOrderMemo.add(routineDTO.getOrder());

        if (routineDTO.getOrder() > routineSize) {
          throw new InvalidInputValueException(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
        }

        Exercise exercise = exercises.get(routineDTO.getExerciseId());
        if (exercise == null) {
          throw new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND);
        }

        Set<ExerciseType> exerciseTypes = exerciseTypeMap.get(exercise)
          .stream()
          .map(ExerciseTypeRelation::getExerciseType)
          .collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(exerciseTypes)) {
          throw new ExerciseException(ExerciseExceptionCode.NOT_FOUND_RELATION_EXERCISE_TYPE);
        }

        UserExerciseRoutineData routineData = new UserExerciseRoutineData();
        List<UserExerciseSet> setList = userExerciseHelper.createUserExerciseSet(
          routineDTO, exerciseTypes, routineData);

        UserExerciseRoutine routineEntity = UserExerciseRoutine.createRoutine(routineDTO, routineData, exercise);
        setList.forEach(set -> set.applyRoutine(routineEntity));

        logData.setEntityList.addAll(setList);
        logData.update(routineData);
        return routineEntity;
      })
      .toList();
  }

}
