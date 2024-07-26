package com.example.healthcare.application.vo;

import com.example.healthcare.application.account.domain.code.UserStatus;
import com.example.healthcare.infra.config.security.user.LoginUser;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record TokenVO(
  String email,
  UserStatus userStatus,
  String accessToken,
  String refreshToken)
{

  public static TokenVO valueOf(String email, UserStatus userStatus) {
    return builder()
      .email(email)
      .userStatus(userStatus)
      .build();
  }

  public static TokenVO valueOf(LoginUser loginUser,
    String accessToken, String refreshToken) {
    return builder()
      .email(loginUser.getEmail())
      .userStatus(loginUser.getUserStatus())
      .accessToken(accessToken)
      .refreshToken(refreshToken)
      .build();
  }

}
