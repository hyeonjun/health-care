package com.example.healthcare.application.exercise.controller.dto;

import com.example.healthcare.application.exercise.domain.code.ExerciseTimeType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateUserExerciseLogDTO(
  @NotNull @Max(86400) Long exerciseTime,
  @NotNull ExerciseTimeType exerciseTimeType,
  @Valid @Size(max = 1000) List<UpdateUserExerciseRoutineDTO> routineDTOList
) {
}
