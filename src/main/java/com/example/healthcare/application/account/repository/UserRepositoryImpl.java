package com.example.healthcare.application.account.repository;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.account.repository.param.SearchUserParam;
import com.example.healthcare.application.vo.QUserVO;
import com.example.healthcare.application.vo.UserVO;
import com.example.healthcare.util.repository.CustomQuerydslRepositorySupport;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

import static com.example.healthcare.application.account.domain.QUser.user;

public class UserRepositoryImpl extends CustomQuerydslRepositorySupport implements
  UserRepositoryCustom {

  public UserRepositoryImpl() {
    super(User.class);
  }

  @Override
  public Page<UserVO> findAll(SearchUserParam param, Pageable pageable) {
    BooleanBuilder whereCondition = getWhereCondition(param);

    JPAQuery<UserVO> query = select(getUserVO())
      .from(user)
      .where(whereCondition)
      .orderBy(user.id.desc());
    return applyPagination(pageable, query);
  }

  private QUserVO getUserVO() {
    return new QUserVO(
      user.id,
      user.email,
      user.nickname,
      user.mobile,
      user.name,
      user.userStatus,
      user.authorityType,
      user.signUpDateTime,
      user.recentSignInDateTime,
      user.recentChangeStatusDateTime
    );
  }

  private BooleanBuilder getWhereCondition(SearchUserParam param) {
    BooleanBuilder whereCondition = new BooleanBuilder();

    if (Objects.nonNull(param.email())) {
      whereCondition.and(user.email.contains(param.email()));
    }

    if (Objects.nonNull(param.nickname())) {
      whereCondition.and(user.nickname.contains(param.nickname()));
    }

    if (Objects.nonNull(param.mobile())) {
      whereCondition.and(user.mobile.contains(param.mobile()));
    }

    if (Objects.nonNull(param.name())) {
      whereCondition.and(user.name.contains(param.name()));
    }

    if (!CollectionUtils.isEmpty(param.userStatuses())) {
      whereCondition.and(user.userStatus.in(param.userStatuses()));
    }

    if (!CollectionUtils.isEmpty(param.authorities())) {
      whereCondition.and(user.authorityType.in(param.authorities()));
    }

    if (Objects.nonNull(param.signUpDateFrom())) {
      whereCondition.and(user.signUpDateTime.goe(param.signUpDateFrom().atStartOfDay()));
    }

    if (Objects.nonNull(param.signUpDateTo())) {
      whereCondition.and(user.signUpDateTime.lt(
        param.signUpDateTo().plusDays(1).atStartOfDay()));
    }

    if (Objects.nonNull(param.recentSignInDateFrom())) {
      whereCondition.and(user.recentSignInDateTime.goe(param.recentSignInDateFrom().atStartOfDay()));
    }

    if (Objects.nonNull(param.recentSignInDateTo())) {
      whereCondition.and(user.recentSignInDateTime.lt(
        param.recentSignInDateTo().plusDays(1).atStartOfDay()));
    }

    if (Objects.nonNull(param.recentChangeStatusDateFrom())) {
      whereCondition.and(user.recentChangeStatusDateTime.goe(param.recentChangeStatusDateFrom().atStartOfDay()));
    }

    if (Objects.nonNull(param.recentChangeStatusDateFrom())) {
      whereCondition.and(user.recentChangeStatusDateTime.lt(
        param.recentChangeStatusDateFrom().plusDays(1).atStartOfDay()));
    }

    return whereCondition;
  }
}
