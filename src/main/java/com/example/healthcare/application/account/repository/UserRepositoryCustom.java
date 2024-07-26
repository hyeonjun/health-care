package com.example.healthcare.application.account.repository;

import com.example.healthcare.application.account.repository.param.SearchUserParam;
import com.example.healthcare.application.vo.UserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

  Page<UserVO> findAll(SearchUserParam param, Pageable pageable);
}
