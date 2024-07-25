package com.example.healthcare.application.vo;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.account.domain.code.AuthorityType;
import com.example.healthcare.application.account.domain.code.UserStatus;

import java.time.LocalDateTime;

public record UserVO(
  Long id,
  String email,
  String nickname,
  String mobile,
  String name,
  UserStatus userStatus,
  AuthorityType authorityType,
  LocalDateTime signUpDateTime,
  LocalDateTime recentSignInDateTime,
  LocalDateTime recentChangeStatusDateTime) {

  public static UserVO valueOf(User user) {
    return new UserVO(
      user.getId(),
      user.getEmail(),
      user.getNickname(),
      user.getMobile(),
      user.getName(),
      user.getUserStatus(),
      user.getAuthorityType(),
      user.getSignUpDateTime(),
      user.getRecentSignInDateTime(),
      user.getRecentChangeStatusDateTime());
  }

}
