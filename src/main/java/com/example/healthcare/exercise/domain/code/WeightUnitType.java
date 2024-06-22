package com.example.healthcare.exercise.domain.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeightUnitType {

  KILOGRAM("kg"),
  POUND("lbs");

  private final String unit;

  public static long kgToLbs(long kg) {
    return (long) (kg * 2.20462);
  }

  public static long lbsToKg(long lbs) {
    return (long) (lbs * 0.45359237);
  }
}
