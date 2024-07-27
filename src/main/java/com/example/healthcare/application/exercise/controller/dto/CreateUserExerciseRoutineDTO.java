package com.example.healthcare.application.exercise.controller.dto;

import com.example.healthcare.application.common.exception.InvalidInputValueException;
import com.example.healthcare.application.common.exception.InvalidInputValueException.InvalidInputValueExceptionCode;
import com.example.healthcare.application.exercise.domain.code.WeightUnitType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateUserExerciseRoutineDTO(
  @NotNull Long restTime, // second 단위, 최대 11분 -> 60 * 11
  Integer order,
  WeightUnitType weightUnitType,
  @NotNull Long exerciseId,

  @Valid @Size(max = 1000) List<CreateUserExerciseSetDTO> setDTOList
) {

  public CreateUserExerciseRoutineDTO {
    if (restTime > 60 * 11) {
      throw new InvalidInputValueException(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
    }
  }
}
