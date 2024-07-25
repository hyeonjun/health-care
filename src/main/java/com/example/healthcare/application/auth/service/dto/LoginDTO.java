package com.example.healthcare.application.auth.service.dto;

import com.example.healthcare.util.RegexUtil;
import com.example.healthcare.util.VerifyUtil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record LoginDTO(
  @NotBlank
  @Length(min = VerifyUtil.MIN_DEFAULT_LENGTH, max = VerifyUtil.MAX_EMAIL_LENGTH)
  @Email(regexp = RegexUtil.REGEXP_EMAIL)
  String email,
  @NotBlank
  @Length(min = 8, max = VerifyUtil.MAX_DEFAULT_LENGTH)
  @Pattern(regexp = RegexUtil.REGEXP_PASSWORD, message = "invalid password format")
  String password) {
}
