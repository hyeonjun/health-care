package com.example.healthcare.auth.repository;

import com.example.healthcare.auth.domain.AccessTokenBlackList;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenBlackListRepository extends CrudRepository<AccessTokenBlackList, String> {
}
