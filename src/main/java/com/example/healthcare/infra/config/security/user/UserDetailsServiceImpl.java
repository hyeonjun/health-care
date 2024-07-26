package com.example.healthcare.infra.config.security.user;

import com.example.healthcare.application.account.domain.code.UserStatus;
import com.example.healthcare.application.account.repository.UserRepository;
import com.example.healthcare.application.common.exception.ResourceException;
import com.example.healthcare.application.common.exception.ResourceException.ResourceExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmailAndUserStatusIsNot(email, UserStatus.CANCELLED)
          .map(LoginUser::new)
          .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));
    }
}