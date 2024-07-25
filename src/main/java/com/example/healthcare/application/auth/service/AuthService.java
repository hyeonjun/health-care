package com.example.healthcare.application.auth.service;

import com.example.healthcare.event.user.UserEventPublisher;
import com.example.healthcare.application.auth.domain.RefreshToken;
import com.example.healthcare.application.auth.repository.AccessTokenBlackListRepository;
import com.example.healthcare.application.auth.service.dto.LogoutDTO;
import com.example.healthcare.application.auth.service.dto.ReissueTokenDTO;
import com.example.healthcare.application.auth.repository.RefreshTokenRepository;
import com.example.healthcare.application.auth.service.dto.LoginDTO;
import com.example.healthcare.application.common.exception.AuthException;
import com.example.healthcare.application.common.exception.AuthException.AuthExceptionCode;
import com.example.healthcare.infra.config.security.jwt.JwtAuthenticationProvider;
import com.example.healthcare.infra.config.security.jwt.JwtUtil;
import com.example.healthcare.infra.config.security.user.LoginUser;
import com.example.healthcare.application.vo.TokenVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtUtil jwtUtil;
  private final JwtAuthenticationProvider jwtAuthenticationProvider;

  private final RefreshTokenRepository refreshTokenRepository;
  private final AccessTokenBlackListRepository accessTokenBlackListRepository;

  private final UserEventPublisher userEventPublisher;

  @Transactional
  public TokenVO login(LoginDTO dto) {
    Authentication authentication = authenticationManagerBuilder.getObject()
      .authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    LoginUser loginUser = (LoginUser) authentication.getPrincipal();
    TokenVO vo = jwtUtil.createToken(loginUser);

    RefreshToken refreshToken = RefreshToken.createRefreshToken()
      .email(loginUser.getEmail())
      .refreshToken(vo.refreshToken())
      .build();
    refreshTokenRepository.save(refreshToken);

    // 유저 로그인 이벤트 객체 게시
    userEventPublisher.publishEventUserLogin(loginUser.getEmail());
    return vo;
  }

  @Transactional
  public void logout(LogoutDTO dto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (Objects.isNull(authentication)) {
      throw new AuthException(AuthExceptionCode.ALREADY_LOGOUT);
    }

    // 현재 로그인된 유저
    LoginUser loginUser = (LoginUser) authentication.getPrincipal();

    // accessToken to LoginUser
    LoginUser loginUserFromToken = (LoginUser) jwtAuthenticationProvider.getAuthentication(
      dto.accessToken()).getPrincipal();

    if (!loginUser.getEmail().equals(loginUserFromToken.getEmail())) {
      throw new AuthException(AuthExceptionCode.NOT_AUTHORIZED);
    }

    refreshTokenRepository.deleteById(loginUser.getEmail());
    accessTokenBlackListRepository.deleteById(dto.accessToken());
    SecurityContextHolder.clearContext();
  }

  public TokenVO reissueToken(ReissueTokenDTO dto) {
    // refresh token validation
    jwtAuthenticationProvider.validateToken(dto.refreshToken());

    LoginUser loginUser = (LoginUser) jwtAuthenticationProvider.getAuthentication(
      dto.accessToken()).getPrincipal();

    // get refreshToken in redis and validation
    RefreshToken refreshToken = refreshTokenRepository
      .findById(loginUser.getEmail())
      .filter(token -> token.validate(dto.refreshToken()))
      .orElseThrow(() -> new AuthException(AuthExceptionCode.JWT_REFRESH_TOKEN_VERIFICATION_FAIL));

    String newAccessToken = jwtUtil.createAccessToken(loginUser, new Date());
    return TokenVO.valueOf(loginUser.getEmail(), newAccessToken, refreshToken.getRefreshToken());
  }
}
