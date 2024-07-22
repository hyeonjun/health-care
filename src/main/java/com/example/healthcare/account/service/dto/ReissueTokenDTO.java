package com.example.healthcare.account.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ReissueTokenDTO(
        @JsonProperty("accessToken")
        String accessToken,
        @JsonProperty("refreshToken")
        String refreshToken) {}
