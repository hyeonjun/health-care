package com.example.healthcare.application.account.domain.code;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

@Getter
public enum AuthorityType {

  SYSTEM("SYSTEM"),
  CUSTOMER("CUSTOMER"),
  TRAINER("TRAINER"),
  GUEST("GUEST");
  ;

  private final String value;

  AuthorityType(String value) {
    this.value = value;
  }

  public static AuthorityType of(String name) {
    return Arrays.stream(AuthorityType.values())
      .filter(a -> a.name().equals(name))
      .findFirst()
      .orElse(GUEST);
  }

  private static final Set<AuthorityType> COMMON_AUTHORITY_TYPES = Set.of(CUSTOMER, TRAINER);

  public static Set<AuthorityType> getCommonAuthorityTypes() {
    return COMMON_AUTHORITY_TYPES;
  }

}
