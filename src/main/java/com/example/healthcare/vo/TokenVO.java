package com.example.healthcare.vo;

public record TokenVO(
  String email,
  String accessToken,
  String refreshToken)
{

  public static TokenVO valueOf(String email, String accessToken, String refreshToken) {
    return new TokenVO(email, accessToken, refreshToken);
  }

}
