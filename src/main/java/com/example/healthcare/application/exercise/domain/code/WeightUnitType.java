package com.example.healthcare.application.exercise.domain.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeightUnitType {

  KILOGRAM("kg"),
  POUND("lbs");

  private final String unit;

  public static double kgToLbs(double kg) {
    return (kg * 2.20462);
  }

  public static double lbsToKg(double lbs) {
    return (lbs * 0.45359237);
  }
}
