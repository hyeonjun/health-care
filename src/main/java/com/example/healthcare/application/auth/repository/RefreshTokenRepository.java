package com.example.healthcare.application.auth.repository;

import com.example.healthcare.application.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
