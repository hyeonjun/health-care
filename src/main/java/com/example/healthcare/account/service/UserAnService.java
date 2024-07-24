package com.example.healthcare.account.service;

import com.example.healthcare.account.domain.User;
import com.example.healthcare.account.repository.UserRepository;
import com.example.healthcare.account.service.dto.CreateUserDTO;
import com.example.healthcare.account.service.dto.ReissueTokenDTO;
import com.example.healthcare.common.exception.AuthException;
import com.example.healthcare.common.exception.AuthException.AuthExceptionCode;
import com.example.healthcare.common.exception.ResourceException;
import com.example.healthcare.common.exception.ResourceException.ResourceExceptionCode;
import com.example.healthcare.config.security.jwt.JwtUtil;
import com.example.healthcare.util.PasswordProvider;
import com.example.healthcare.vo.TokenVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserAnService {

  private final UserRepository userRepository;
  private final PasswordProvider passwordEncoder;
  private final RedisTemplate redisTemplate;
  private final JwtUtil jwtUtil;

  @Transactional
  public void signUp(CreateUserDTO dto) {
    String encodedPassword = passwordEncoder.encode(dto.newPassword());
    User user = new User(dto, encodedPassword);
    userRepository.save(user);
  }

  public TokenVO reissueToken(ReissueTokenDTO dto) {
    ValueOperations<String, String> values = redisTemplate.opsForValue();
    User user = userRepository.findByEmail(dto.email())
      .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

    if (!Objects.equals(values.get(user.getEmail()), dto.refreshToken())) {
      throw new AuthException(AuthExceptionCode.JWT_REFRESH_TOKEN_VERIFICATION_FAIL);
    }

    String accessToken = jwtUtil.createAccessToken(user.getEmail(), user.getAuthorityType());
    return new TokenVO(accessToken, dto.refreshToken());
  }

}
