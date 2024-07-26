package com.example.healthcare.application.exercise.repository;

import com.example.healthcare.application.account.domain.code.AuthorityType;
import com.example.healthcare.application.exercise.domain.Exercise;
import com.example.healthcare.application.exercise.repository.param.SearchExerciseParam;
import com.example.healthcare.application.vo.ExerciseVO;
import com.example.healthcare.application.vo.QExerciseVO;
import com.example.healthcare.util.repository.CustomQuerydslRepositorySupport;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

import static com.example.healthcare.application.account.domain.QUser.user;
import static com.example.healthcare.application.exercise.domain.QExercise.exercise;

public class ExerciseRepositoryImpl extends CustomQuerydslRepositorySupport implements
  ExerciseRepositoryCustom {

  public ExerciseRepositoryImpl() {
    super(Exercise.class);
  }

  @Override
  public Page<ExerciseVO> findAll(SearchExerciseParam param, Pageable pageable) {
    BooleanBuilder whereCondition = getWhereCondition(param);

    JPAQuery<ExerciseVO> query = select(getExerciseVO())
      .from(exercise)
      .innerJoin(exercise.createdUser, user)
      .where(whereCondition)
      .orderBy(orderByCase(), exercise.id.desc());
    return applyPagination(pageable, query);
  }

  private OrderSpecifier<Integer> orderByCase() {
    return new CaseBuilder()
      .when(user.authorityType.eq(AuthorityType.SYSTEM)).then(1)
      .otherwise(0).asc();
  }

  private QExerciseVO getExerciseVO() {
    return new QExerciseVO(
      exercise.id,
      exercise.name,
      exercise.isDeleted,
      exercise.bodyType,
      user.id,
      user.authorityType
    );
  }

  private BooleanBuilder getWhereCondition(SearchExerciseParam param) {
    BooleanBuilder whereCondition = new BooleanBuilder();

    if (Objects.nonNull(param.exerciseName())) {
      whereCondition.and(exercise.name.contains(param.exerciseName()));
    }

    if (Objects.nonNull(param.exerciseBodyType())) {
      whereCondition.and(exercise.bodyType.eq(param.exerciseBodyType()));
    }

    if (Objects.nonNull(param.userId())) { // 사용자 개인 + 시스템
      whereCondition.and(
        user.id.eq(param.userId())
          .or(user.authorityType.eq(AuthorityType.SYSTEM))
      );
    } else { // 시스템
      whereCondition.and(user.authorityType.eq(AuthorityType.SYSTEM));
    }

    return whereCondition;
  }

}
