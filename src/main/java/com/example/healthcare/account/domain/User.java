package com.example.healthcare.account.domain;

import com.example.healthcare.account.domain.code.AuthorityType;
import com.example.healthcare.account.domain.code.UserStatus;
import com.example.healthcare.account.service.dto.CreateUserDTO;
import com.example.healthcare.common.domain.Base;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

import static com.example.healthcare.account.domain.code.AuthorityType.COMMON;
import static com.example.healthcare.account.domain.code.UserStatus.ACTIVATED;

@Entity
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private Long id;
  @Column(name = "email", nullable = false, length = 191, unique = true)
  private String email;
  @Column(nullable = false)
  private String nickname;
  private String mobile;
  @Column(nullable = false)
  private String name;
  @JsonIgnore
  @Column(nullable = false)
  private String password;
  @Enumerated(value = EnumType.STRING)
  @Column(name = "user_status", length = 191)
  private UserStatus userStatus;
  @Enumerated(value = EnumType.STRING)
  @Column(name = "authority_type", length = 191)
  private AuthorityType authorityType;
  private LocalDateTime signUpDateTime;
  private LocalDateTime recentSignInDateTime;
  private LocalDateTime recentChangeStatusDateTime;

  public User(CreateUserDTO dto, String encodedPassword) {

    this.email = dto.email();
    this.nickname = dto.nickname();
    this.mobile = dto.mobile();
    this.name = dto.name();
    this.password = encodedPassword;
    this.userStatus = ACTIVATED;
    this.authorityType = COMMON;
    // 시간 추가

  }

}
