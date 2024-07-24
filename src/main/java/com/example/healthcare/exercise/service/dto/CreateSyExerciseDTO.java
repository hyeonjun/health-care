package com.example.healthcare.exercise.service.dto;

import com.example.healthcare.exercise.domain.code.ExerciseBodyType;
import com.example.healthcare.exercise.domain.code.ExerciseToolType;
import jakarta.validation.constraints.NotBlank;

public record CreateSyExerciseDTO(
        @NotBlank
        String name,
        @NotBlank
        ExerciseBodyType bodyType,
        @NotBlank
        ExerciseToolType toolType) {
}
