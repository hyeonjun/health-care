package com.example.healthcare.application.exercise.controller.dto;

import com.example.healthcare.application.exercise.domain.code.ExerciseBodyType;
import com.example.healthcare.application.exercise.domain.code.ExerciseToolType;
import com.example.healthcare.application.exercise.domain.code.ExerciseType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.List;

import static com.example.healthcare.util.VerifyUtil.MAX_DESCRIPTION_LENGTH;

public record UpdateExerciseDTO(
  @NotBlank String name,
  @NotNull ExerciseBodyType bodyType,
  @NotNull ExerciseToolType toolType,
  @Length(max = MAX_DESCRIPTION_LENGTH) String description,
  @NotNull @Size(min = 1, max = 2) List<ExerciseType> exerciseTypes) {
}
