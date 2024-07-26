package com.example.healthcare.application.exercise.domain.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExerciseTimeType {

  DAY("07:00 ~ 15:00"),
  EVENING("15:00 ~ 23:00"),
  NIGHT("23:00 ~ 07:00")
  ;

  private final String description;

}
