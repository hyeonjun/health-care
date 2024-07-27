package com.example.healthcare.application.exercise.domain.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExerciseTimeType {

  AM("오전"),
  PM("오후"),
  DEFAULT("default")
  ;

  private final String description;

}
