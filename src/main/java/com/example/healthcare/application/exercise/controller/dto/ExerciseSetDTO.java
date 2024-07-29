package com.example.healthcare.application.exercise.controller.dto;

import com.example.healthcare.application.exercise.domain.code.ExerciseSetType;

public interface ExerciseSetDTO {

  Long getSetNumber();
  ExerciseSetType getSetType();
  Double getWeight();
  Integer getReps();
  Long getTime();
  Boolean getComplete();
}
