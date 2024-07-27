package com.example.healthcare.util;

import lombok.NoArgsConstructor;

import java.math.BigInteger;

@NoArgsConstructor
public class CalculatorUtil {

  public static BigInteger add(BigInteger owner, BigInteger target) {
    if (target != null) {
      if (owner == null) owner = BigInteger.ZERO;
      return owner.add(target);
    }
    return owner;
  }

}
