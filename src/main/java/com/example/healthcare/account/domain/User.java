package com.example.healthcare.account.domain;

import com.example.healthcare.account.domain.code.AuthorityType;
import com.example.healthcare.account.domain.code.UserStatus;
import com.example.healthcare.account.service.dto.CreateUserDTO;
import com.example.healthcare.common.domain.Base;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Objects;

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
    this.signUpDateTime = LocalDateTime.now();
    this.recentChangeStatusDateTime = LocalDateTime.now();
  }

  public User(Claims claims) {
    this.id = claims.get("id", Long.class);
    this.email = claims.get("email", String.class);
    this.name = claims.get("name", String.class);
    this.authorityType = AuthorityType.of(claims.get("authorityType", String.class));
    this.password = "";
  }

  public void changeAuthority(AuthorityType authorityType) {
    this.authorityType = authorityType;
  }

  public void updateSignInDateTime(LocalDateTime signInDateTime) {
    this.recentSignInDateTime = signInDateTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User user)) return false;
    return Objects.equals(getId(), user.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
      .append("id", id)
      .append("email", email)
      .append("nickname", nickname)
      .append("mobile", mobile)
      .append("name", name)
      .append("password", password)
      .append("userStatus", userStatus)
      .append("authorityType", authorityType)
      .append("signUpDateTime", signUpDateTime)
      .append("recentSignInDateTime", recentSignInDateTime)
      .append("recentChangeStatusDateTime", recentChangeStatusDateTime)
      .append("createdDateTime", createdDateTime)
      .append("updatedDateTime", updatedDateTime)
      .toString();
  }
}
