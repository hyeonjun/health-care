package com.example.healthcare.util;

import lombok.NoArgsConstructor;

import java.math.BigInteger;

@NoArgsConstructor
public class CalculatorUtil {

  public static <T extends Number> BigInteger add(BigInteger owner, T target) {
    if (target != null) {
      if (owner == null) owner = BigInteger.ZERO;
      return owner.add(BigInteger.valueOf(target.longValue()));
    }
    return owner;
  }

}
