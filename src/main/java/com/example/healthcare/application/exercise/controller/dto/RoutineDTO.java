package com.example.healthcare.application.exercise.controller.dto;

import com.example.healthcare.application.exercise.domain.code.WeightUnitType;

import java.util.List;

public interface RoutineDTO {

  Long getRestTime();
  Integer getOrder();
  WeightUnitType getWeightUnitType();
  Long getExerciseId();
  List<? extends ExerciseSetDTO> getExerciseSetList();

}
