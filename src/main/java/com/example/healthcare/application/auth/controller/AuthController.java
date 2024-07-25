package com.example.healthcare.application.auth.controller;

import com.example.healthcare.application.auth.controller.dto.LogoutDTO;
import com.example.healthcare.application.auth.service.AuthService;
import com.example.healthcare.application.auth.controller.dto.LoginDTO;
import com.example.healthcare.application.auth.controller.dto.ReissueTokenDTO;
import com.example.healthcare.application.common.response.CommonResponse;
import com.example.healthcare.application.vo.TokenVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
  public CommonResponse<TokenVO> login(@Valid @RequestBody LoginDTO dto) {
    return CommonResponse.success(authService.login(dto));
  }

  @PostMapping(value = "/logout", consumes = MediaType.APPLICATION_JSON_VALUE)
  public CommonResponse<TokenVO> logout(@Valid @RequestBody LogoutDTO dto) {
    authService.logout(dto);
    return CommonResponse.success();
  }

  @PostMapping(value = "/reissue:token", consumes = MediaType.APPLICATION_JSON_VALUE)
  public CommonResponse<TokenVO> reissueToken(@Valid @RequestBody ReissueTokenDTO dto) {
    return CommonResponse.success(authService.reissueToken(dto));
  }
}
