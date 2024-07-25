package com.example.healthcare.application.auth.repository;

import com.example.healthcare.application.auth.domain.AccessTokenBlackList;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenBlackListRepository extends CrudRepository<AccessTokenBlackList, String> {
}
