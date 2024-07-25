package com.example.healthcare.account.domain.code;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AuthorityType {

  SYSTEM("SYSTEM"),
  COMMON("COMMON"),
  TRAINER("TRAINER");

  private final String value;

  AuthorityType(String value) {
    this.value = value;
  }

  public static AuthorityType of(String name) {
    return Arrays.stream(AuthorityType.values())
      .filter(a -> a.name().equals(name))
      .findFirst()
      .orElse(null);
  }
}
