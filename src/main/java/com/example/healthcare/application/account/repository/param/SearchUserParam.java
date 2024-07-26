package com.example.healthcare.application.account.repository.param;

import com.example.healthcare.application.account.controller.dto.SearchUserDTO;
import com.example.healthcare.application.account.domain.code.AuthorityType;
import com.example.healthcare.application.account.domain.code.UserStatus;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record SearchUserParam(
  String email,
  String nickname,
  String mobile,
  String name,
  List<UserStatus> userStatuses,
  List<AuthorityType> authorities,
  LocalDate signUpDateFrom,
  LocalDate signUpDateTo,
  LocalDate recentSignInDateFrom,
  LocalDate recentSignInDateTo,
  LocalDate recentChangeStatusDateFrom,
  LocalDate recentChangeStatusDateTo
) {
  public static SearchUserParam valueOf(SearchUserDTO dto) {
    return builder()
      .email(dto.getEmail())
      .nickname(dto.getNickname())
      .mobile(dto.getMobile())
      .name(dto.getName())
      .userStatuses(dto.getUserStatuses())
      .authorities(dto.getAuthorities())
      .signUpDateFrom(dto.getSignUpDateFrom())
      .signUpDateTo(dto.getSignUpDateTo())
      .recentSignInDateFrom(dto.getRecentSignInDateFrom())
      .recentSignInDateTo(dto.getRecentSignInDateTo())
      .recentChangeStatusDateFrom(dto.getRecentChangeStatusDateFrom())
      .recentChangeStatusDateTo(dto.getRecentChangeStatusDateTo())
      .build();
  }
}
