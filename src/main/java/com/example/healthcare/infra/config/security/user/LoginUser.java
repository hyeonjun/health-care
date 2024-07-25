package com.example.healthcare.infra.config.security.user;


import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.account.domain.code.AuthorityType;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@Getter
public class LoginUser extends org.springframework.security.core.userdetails.User {

    private final Long id;
    private final String email;
    private final String name;
    private final AuthorityType authorityType;

    public LoginUser(User user) {
        super(user.getEmail(), user.getPassword(),
          Collections.singleton(new SimpleGrantedAuthority(String.valueOf(user.getAuthorityType()))));
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.authorityType = user.getAuthorityType();
    }
}
