package com.example.healthcare.application.exercise.repository.param;

import com.example.healthcare.application.exercise.domain.UserExerciseLog;
import com.example.healthcare.application.exercise.domain.code.ExerciseTimeType;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Collection;

@Builder(access = AccessLevel.PRIVATE)
public record SearchUserExerciseLogParam(
  Long exerciseLogId,
  LocalDate exerciseDate,
  Collection<ExerciseTimeType> exerciseTimeTypes
) {

  public static SearchUserExerciseLogParam valueOf(
    UserExerciseLog exerciseLog, Collection<ExerciseTimeType> exerciseTimeTypes) {
    return builder()
      .exerciseLogId(exerciseLog.getId())
      .exerciseDate(exerciseLog.getExerciseDate())
      .exerciseTimeTypes(exerciseTimeTypes)
      .build();
  }

  public static SearchUserExerciseLogParam valueOf(
    LocalDate exerciseDate, Collection<ExerciseTimeType> exerciseTimeTypes) {
    return builder()
      .exerciseDate(exerciseDate)
      .exerciseTimeTypes(exerciseTimeTypes)
      .build();
  }
}
