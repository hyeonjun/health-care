package com.example.healthcare.application.exercise.helper;

import com.example.healthcare.application.common.exception.DuplicateException;
import com.example.healthcare.application.common.exception.DuplicateException.DuplicateExceptionCode;
import com.example.healthcare.application.common.exception.InvalidInputValueException;
import com.example.healthcare.application.common.exception.InvalidInputValueException.InvalidInputValueExceptionCode;
import com.example.healthcare.application.exercise.controller.dto.RoutineDTO;
import com.example.healthcare.application.exercise.domain.UserExerciseSet;
import com.example.healthcare.application.exercise.domain.code.ExerciseType;
import com.example.healthcare.application.exercise.service.data.UserExerciseData.UserExerciseRoutineData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserExerciseHelper {

  @Transactional
  public List<UserExerciseSet> createUserExerciseSet(RoutineDTO dto,
    Set<ExerciseType> exerciseTypes, UserExerciseRoutineData routineData) {
    int totalSetSize = dto.getExerciseSetList().size();
    Set<Long> setNumberMemo = new HashSet<>();
    return dto.getExerciseSetList().stream()
      .map(setDTO -> {
        // setNumber validation
        if (setDTO.getSetNumber() > totalSetSize) {
          throw new InvalidInputValueException(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
        }

        if (setNumberMemo.contains(setDTO.getSetNumber())) {
          throw new DuplicateException(DuplicateExceptionCode.DUPLICATE_SET_NUMBER);
        }
        setNumberMemo.add(setDTO.getSetNumber());

        // exerciseType validation
        checkExerciseType(exerciseTypes, ExerciseType.WEIGHT, setDTO.getWeight());
        checkExerciseType(exerciseTypes, ExerciseType.REPS, setDTO.getReps());
        checkExerciseType(exerciseTypes, ExerciseType.TIME, setDTO.getTime());

        UserExerciseSet setEntity = UserExerciseSet.createSet(setDTO);
        routineData.update(setDTO, dto.getWeightUnitType());
        return setEntity;
      }).toList();
  }

  private void checkExerciseType(Collection<ExerciseType> types, ExerciseType type, Object value) {
    if ((types.contains(type) && Objects.isNull(value)) || (!types.contains(type) && Objects.nonNull(value))) {
      throw new InvalidInputValueException(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
    }
  }

}
