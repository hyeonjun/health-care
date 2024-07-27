package com.example.healthcare.application.exercise.controller.dto;

import com.example.healthcare.application.exercise.domain.code.WeightUnitType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateUserExerciseRoutineDTO(
  Long restTime,
  Integer order,
  WeightUnitType weightUnitType,
  @NotNull Long exerciseId,

  @Valid @Size(max = 1000) List<CreateUserExerciseSetDTO> createUserExerciseSetDTOS
) {
}
