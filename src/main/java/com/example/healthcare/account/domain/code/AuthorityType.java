package com.example.healthcare.account.domain.code;

import lombok.Getter;

@Getter
public enum AuthorityType {

  SYSTEM("SYSTEM"),
  COMMON("COMMON"),
  TRAINER("TRAINER");

  private final String value;

  AuthorityType(String value) {
    this.value = value;
  }
}
