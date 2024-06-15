package com.example.healthcare.account.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SignUpDTO {
  @NotBlank
  @Length(min = 1, max = 15)
//  @Pattern(regexp = StringUtil.REGEXP_LOGIN_ID, message = "invalid login id")
  private String loginId;

  @NotBlank
//  @Email(regexp = StringUtil.REGEXP_EMAIL)
//  @Length(min = 1, max = VerifyUtil.MAX_EMAIL_LENGTH)
  private String email;

  @NotBlank
  private String name;

  @NotBlank
  @Size(min = 2, max = 15)
//  @Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "영어와 숫자, 한글만 사용 가능합니다.")
  private String nickname;


  @NotBlank
  @Size(min = 2, max = 15)
  private String newPassword;
  @NotBlank
  private String confirmPassword;

}