package com.example.healthcare.auth.controller;

import com.example.healthcare.auth.service.AuthService;
import com.example.healthcare.auth.service.dto.LoginDTO;
import com.example.healthcare.auth.service.dto.LogoutDTO;
import com.example.healthcare.auth.service.dto.ReissueTokenDTO;
import com.example.healthcare.common.response.CommonResponse;
import com.example.healthcare.vo.TokenVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public CommonResponse<TokenVO> login(@Valid @RequestBody LoginDTO dto) {
    return CommonResponse.success(authService.login(dto));
  }

  @PostMapping("/logout")
  public CommonResponse<TokenVO> logout(@Valid @RequestBody LogoutDTO dto) {
    authService.logout(dto);
    return CommonResponse.success();
  }

  @PostMapping("/reissue:token")
  public CommonResponse<TokenVO> reissueToken(@Valid @RequestBody ReissueTokenDTO dto) {
    return CommonResponse.success(authService.reissueToken(dto));
  }
}
