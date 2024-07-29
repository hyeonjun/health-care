package com.example.healthcare.application.exercise.controller.dto;

import com.example.healthcare.application.common.exception.InvalidInputValueException;
import com.example.healthcare.application.common.exception.InvalidInputValueException.InvalidInputValueExceptionCode;
import com.example.healthcare.application.exercise.domain.code.ExerciseSetType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateUserExerciseSetDTO(
  @NotNull @Min(1) Long setNumber,
  @NotNull ExerciseSetType exerciseSetType,
  Long weight,
  Integer reps,
  Long time,
  @NotNull Boolean complete
) implements ExerciseSetDTO {

  public UpdateUserExerciseSetDTO {
    if (weight == null && reps == null && time == null) {
      throw new InvalidInputValueException(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
    }
  }

  @Override
  public Long getSetNumber() {
    return setNumber();
  }

  @Override
  public ExerciseSetType getSetType() {
    return exerciseSetType();
  }

  @Override
  public Long getWeight() {
    return weight();
  }

  @Override
  public Integer getReps() {
    return reps();
  }

  @Override
  public Long getTime() {
    return time();
  }

  @Override
  public Boolean getComplete() {
    return complete();
  }
}
