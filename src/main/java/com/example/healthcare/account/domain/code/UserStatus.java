package com.example.healthcare.account.domain.code;

import lombok.Getter;

@Getter
public enum UserStatus {

  ACTIVATED("활성화"),
  DEACTIVATED("비활성화"),
  SECESSION("탈퇴"),
  ;

  private final String description;

  UserStatus(String description) {
    this.description = description;
  }
}
