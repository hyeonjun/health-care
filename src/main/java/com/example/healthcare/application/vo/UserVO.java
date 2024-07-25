package com.example.healthcare.application.vo;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.account.domain.code.AuthorityType;
import com.example.healthcare.application.account.domain.code.UserStatus;
import com.querydsl.core.annotations.QueryProjection;

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

  @QueryProjection
  public UserVO(Long id, String email, String nickname, String mobile, String name, UserStatus userStatus,
    AuthorityType authorityType, LocalDateTime signUpDateTime, LocalDateTime recentSignInDateTime,
    LocalDateTime recentChangeStatusDateTime) {
    this.id = id;
    this.email = email;
    this.nickname = nickname;
    this.mobile = mobile;
    this.name = name;
    this.userStatus = userStatus;
    this.authorityType = authorityType;
    this.signUpDateTime = signUpDateTime;
    this.recentSignInDateTime = recentSignInDateTime;
    this.recentChangeStatusDateTime = recentChangeStatusDateTime;
  }

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
