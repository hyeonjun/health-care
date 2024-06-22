package com.example.healthcare.account.service;

import com.example.healthcare.account.domain.User;
import com.example.healthcare.account.domain.code.AuthorityType;
import com.example.healthcare.account.repository.UserRepository;
import com.example.healthcare.account.service.dto.CreateUserDTO;
import com.example.healthcare.account.service.dto.ReissueTokenDTO;
import com.example.healthcare.common.exception.CommonException;
import com.example.healthcare.common.response.CommonResponse;
import com.example.healthcare.common.response.CommonResponseCode;
import com.example.healthcare.security.jwt.JwtUtil;
import com.example.healthcare.util.PasswordProvider;
import com.example.healthcare.vo.ReissueTokenVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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

  public CommonResponse<ReissueTokenVO> reissueToken(ReissueTokenDTO dto, UserDetails userDetails) {
    ValueOperations<String, String> values = redisTemplate.opsForValue();

    if(Objects.equals(values.get(dto.userId()), dto.refreshToken())){
      String username = userDetails.getUsername();
      AuthorityType authorityType = convertAuthorities(userDetails.getAuthorities());
      String accessToken = jwtUtil.createAccessToken(username, authorityType);
      return CommonResponse.success(new ReissueTokenVO(accessToken, dto.refreshToken()));
    }
    else{
      throw new CommonException(CommonResponseCode.FAIL, "Refresh token verification failed");
    }
  }

  private AuthorityType convertAuthorities(Collection<? extends GrantedAuthority> authorities) {
    for (GrantedAuthority authority : authorities) {
      return AuthorityType.valueOf(authority.getAuthority());
    }
    throw new IllegalArgumentException("No valid authority found for user");
  }

}
