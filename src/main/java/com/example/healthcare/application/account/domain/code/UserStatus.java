package com.example.healthcare.application.account.domain.code;

import lombok.Getter;

import java.util.Set;

@Getter
public enum UserStatus {

  ACTIVATED("활성화"),
  DEACTIVATED("비활성화"),
  CANCELLED("탈퇴"),
  ;

  private final String description;

  UserStatus(String description) {
    this.description = description;
  }

  private static final Set<UserStatus> SUSPENDED_STATUSES = Set.of(DEACTIVATED, CANCELLED);

  public static Set<UserStatus> getSuspendedStatuses() {
    return SUSPENDED_STATUSES;
  }
}
