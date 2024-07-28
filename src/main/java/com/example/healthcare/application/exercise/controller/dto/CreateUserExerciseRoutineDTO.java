package com.example.healthcare.application.exercise.controller.dto;

import com.example.healthcare.application.exercise.domain.code.WeightUnitType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateUserExerciseRoutineDTO(
  @NotNull @Max(660) Long restTime, // second 단위, 최대 11분 -> 60 * 11
  @NotNull @Min(1) Integer order,
  WeightUnitType weightUnitType,
  @NotNull Long exerciseId,

  @Valid @Size(max = 1000) List<CreateUserExerciseSetDTO> setDTOList
) {
}
