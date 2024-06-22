package com.example.healthcare.account.service.dto;

import com.example.healthcare.util.RegexUtil;
import com.example.healthcare.util.VerifyUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

public record LoginDTO(
        @NotBlank
        @Length(min = VerifyUtil.MIN_DEFAULT_LENGTH, max = VerifyUtil.MAX_DEFAULT_LENGTH)
        @Pattern(regexp = RegexUtil.REGEXP_LOGIN_ID, message = "invalid login id")
        String email,
        @NotBlank
        @Length(min = 8, max = VerifyUtil.MAX_DEFAULT_LENGTH)
        @Pattern(regexp = RegexUtil.REGEXP_PASSWORD, message = "invalid password format")
        String password) {}
