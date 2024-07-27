package com.example.healthcare.application.exercise.controller.dto;

import com.example.healthcare.application.exercise.domain.code.ExerciseTimeType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDate;
import java.util.List;

public record CreateUserExerciseLogDTO(
  // ExerciseLog
  @NotNull Long exerciseTime,
  @NotNull @DateTimeFormat(iso = ISO.DATE) LocalDate exerciseDate,
  @NotNull ExerciseTimeType exerciseTimeType,
  @Valid @Size(max = 1000) List<CreateUserExerciseRoutineDTO> createUserExerciseRoutineDTOS
  ) {
}
