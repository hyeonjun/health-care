package com.example.healthcare.account.domain;

import com.example.healthcare.account.domain.code.AuthorityType;
import com.example.healthcare.common.domain.Base;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private Long id;

  @Column(name = "login_id", length = 191, unique = true)
  private String loginId;

  @Column(name = "email", nullable = false, length = 191, unique = true)
  private String email;

  @Column(nullable = false)
  private String nickname;

  private String mobile;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String password;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "authority_type", length = 191)
  private AuthorityType authorityType;

  private LocalDateTime signUpDateTime;
  private LocalDateTime recentSignInDateTime;
  private LocalDateTime recentChangeStatusDateTime;

}
