package com.example.healthcare.application.exercise.repository.param;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.exercise.controller.dto.SearchExerciseDTO;
import com.example.healthcare.application.exercise.domain.code.ExerciseBodyType;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SearchExerciseParam(
  String exerciseName,
  ExerciseBodyType exerciseBodyType,
  Long userId
) {

  public static SearchExerciseParam valueOf(SearchExerciseDTO dto) {
    return builder()
      .exerciseName(dto.getName())
      .exerciseBodyType(dto.getExerciseBodyType())
      .build();
  }

  public static SearchExerciseParam valueOf(SearchExerciseDTO dto, User user) {
    return builder()
      .exerciseName(dto.getName())
      .exerciseBodyType(dto.getExerciseBodyType())
      .userId(user.getId())
      .build();
  }
}
