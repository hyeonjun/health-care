package com.example.healthcare.application.exercise.controller.dto;

import com.example.healthcare.application.exercise.domain.code.WeightUnitType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateUserExerciseRoutineDTO(
  @NotNull @Max(660) Long restTime, // second 단위, 최대 11분 -> 60 * 11
  @NotNull @Min(1) Integer order,
  WeightUnitType weightUnitType,
  @NotNull Long exerciseId,

  @Valid @Size(max = 1000) List<UpdateUserExerciseSetDTO> setDTOList
) implements RoutineDTO {

  @Override
  public Long getRestTime() {
    return restTime();
  }

  @Override
  public Integer getOrder() {
    return order();
  }

  @Override
  public WeightUnitType getWeightUnitType() {
    return weightUnitType();
  }

  @Override
  public Long getExerciseId() {
    return exerciseId();
  }

  @Override
  public List<? extends ExerciseSetDTO> getExerciseSetList() {
    return setDTOList();
  }
}
