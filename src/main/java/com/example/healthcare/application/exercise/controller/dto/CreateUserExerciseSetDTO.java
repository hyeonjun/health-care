package com.example.healthcare.application.exercise.controller.dto;

import com.example.healthcare.application.common.exception.InvalidInputValueException;
import com.example.healthcare.application.common.exception.InvalidInputValueException.InvalidInputValueExceptionCode;
import com.example.healthcare.application.exercise.domain.code.ExerciseSetType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateUserExerciseSetDTO(
  @NotNull @Min(1) Long serNumber,
  @NotNull ExerciseSetType exerciseSetType,
  Long weight,
  Integer reps,
  Long time,
  @NotNull Boolean complete
) {

  public CreateUserExerciseSetDTO {
    if (weight == null && reps == null && time == null) {
      throw new InvalidInputValueException(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
    }
  }
}
