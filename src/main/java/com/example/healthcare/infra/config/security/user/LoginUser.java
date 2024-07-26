package com.example.healthcare.infra.config.security.user;


import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.account.domain.code.AuthorityType;
import com.example.healthcare.application.account.domain.code.UserStatus;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collections;

@Getter
public class LoginUser extends org.springframework.security.core.userdetails.User {

  private final Long id;
  private final String email;
  private final String nickname;
  private final String mobile;
  private final String name;
  private final AuthorityType authorityType;
  private final UserStatus userStatus;
  private final LocalDateTime signUpDateTime;
  private final LocalDateTime recentSignInDateTime;
  private final LocalDateTime recentChangeStatusDateTime;

  public LoginUser(User user) {
    super(user.getEmail(), user.getPassword(),
      Collections.singleton(new SimpleGrantedAuthority(String.valueOf(user.getAuthorityType()))));
    this.id = user.getId();
    this.email = user.getEmail();
    this.nickname = user.getNickname();
    this.mobile = user.getMobile();
    this.name = user.getName();
    this.authorityType = user.getAuthorityType();
    this.userStatus = user.getUserStatus();
    this.signUpDateTime = user.getSignUpDateTime();
    this.recentSignInDateTime = user.getRecentSignInDateTime();
    this.recentChangeStatusDateTime = user.getRecentChangeStatusDateTime();
  }
}
