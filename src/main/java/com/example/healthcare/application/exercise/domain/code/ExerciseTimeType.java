package com.example.healthcare.application.exercise.domain.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public enum ExerciseTimeType {

  AM("오전"),
  PM("오후"),
  DEFAULT("default")
  ;

  private final String description;

  public static Set<ExerciseTimeType> getNotAvailableTimeTypes(ExerciseTimeType type) {
    return switch (type) {
      case DEFAULT -> Set.of(DEFAULT, AM, PM);
      case AM -> Set.of(DEFAULT, AM);
      case PM -> Set.of(DEFAULT, PM);
    };
  }

}
