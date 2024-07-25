package com.example.healthcare.application.exercise.controller.dto;

import com.example.healthcare.application.exercise.domain.code.ExerciseBodyType;
import com.example.healthcare.application.exercise.domain.code.ExerciseToolType;
import jakarta.validation.constraints.NotBlank;

public record CreateSyExerciseDTO(
        @NotBlank
        String name,
        @NotBlank
        ExerciseBodyType bodyType,
        @NotBlank
        ExerciseToolType toolType) {
}
