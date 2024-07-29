package com.example.healthcare.application.exercise.repository;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.exercise.domain.UserExerciseLog;
import com.example.healthcare.application.exercise.repository.param.SearchUserExerciseLogParam;
import com.example.healthcare.application.vo.QUserExerciseLogSummaryVO;
import com.example.healthcare.application.vo.QUserExerciseLogVO;
import com.example.healthcare.application.vo.QUserExerciseRoutineVO;
import com.example.healthcare.application.vo.QUserExerciseSetVO;
import com.example.healthcare.application.vo.UserExerciseLogSummaryVO;
import com.example.healthcare.application.vo.UserExerciseLogVO;
import com.example.healthcare.application.vo.UserExerciseRoutineVO;
import com.example.healthcare.application.vo.UserExerciseSetVO;
import com.example.healthcare.util.repository.CustomQuerydslRepositorySupport;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

import static com.example.healthcare.application.exercise.domain.QExercise.exercise;
import static com.example.healthcare.application.exercise.domain.QUserExerciseLog.userExerciseLog;
import static com.example.healthcare.application.exercise.domain.QUserExerciseRoutine.userExerciseRoutine;
import static com.example.healthcare.application.exercise.domain.QUserExerciseSet.userExerciseSet;

public class UserExerciseLogRepositoryImpl extends CustomQuerydslRepositorySupport implements
  UserExerciseLogRepositoryCustom {

  public UserExerciseLogRepositoryImpl() {
    super(UserExerciseLog.class);
  }

  @Override
  public boolean existExerciseLog(User user, SearchUserExerciseLogParam param) {
    return selectOne()
      .from(userExerciseLog)
      .where(getUserExerciseLogWhereCondition(param)
        .and(userExerciseLog.user.eq(user)))
      .fetchFirst() != null;
  }

  @Override
  public Page<UserExerciseLogVO> findExerciseLogMonthly(User user, Integer year, Integer month, Pageable pageable) {
    JPAQuery<UserExerciseLogVO> query = selectDistinct(new QUserExerciseLogVO(
        userExerciseLog.exerciseDate))
      .from(userExerciseLog)
      .where(userExerciseLog.isDeleted.isFalse()
        .and(userExerciseLog.user.eq(user))
        .and(userExerciseLog.exerciseDate.year().eq(year))
        .and(userExerciseLog.exerciseDate.month().eq(month)))
      .orderBy(userExerciseLog.exerciseDate.asc());

    return applyPagination(pageable, query);
  }

  @Override
  public Page<UserExerciseLogSummaryVO> findExerciseLogDaily(User user, Integer year, Integer month, Integer day, Pageable pageable) {
    JPAQuery<UserExerciseLogSummaryVO> query = select(new QUserExerciseLogSummaryVO(
      userExerciseLog.exerciseDate,
      userExerciseLog.id,
      userExerciseLog.exerciseCount,
      userExerciseLog.exerciseTime,
      userExerciseLog.isDeleted,
      userExerciseLog.totalSetCount,
      userExerciseLog.totalWeight,
      userExerciseLog.totalReps,
      userExerciseLog.totalTime,
      userExerciseLog.exerciseTimeType
    ))
      .from(userExerciseLog)
      .where(userExerciseLog.isDeleted.isFalse()
        .and(userExerciseLog.user.eq(user))
        .and(userExerciseLog.exerciseDate.year().eq(year))
        .and(userExerciseLog.exerciseDate.month().eq(month))
        .and(userExerciseLog.exerciseDate.dayOfMonth().eq(day)))
      .orderBy(userExerciseLog.exerciseDate.asc());

    return applyPagination(pageable, query);
  }

  @Override
  public Page<UserExerciseRoutineVO> findExerciseRoutineAllByLog(UserExerciseLog targetUserExerciseLog, Pageable pageable) {
    JPAQuery<UserExerciseRoutineVO> query = select(getRoutineVO())
      .from(userExerciseLog)
      .innerJoin(userExerciseRoutine).on(userExerciseRoutine.userExerciseLog.eq(userExerciseLog))
      .innerJoin(userExerciseRoutine.exercise, exercise)
      .where(userExerciseLog.eq(targetUserExerciseLog)
        .and(userExerciseRoutine.isDeleted.isFalse()))
      .orderBy(userExerciseRoutine.order.asc(), userExerciseRoutine.id.asc());

    return applyPagination(pageable, query);
  }

  @Override
  public Page<UserExerciseSetVO> findExerciseSetAllByLog(UserExerciseLog targetUserExerciseLog, Pageable pageable) {
    JPAQuery<UserExerciseSetVO> query = select(getSetVO())
      .from(userExerciseLog)
      .innerJoin(userExerciseRoutine).on(userExerciseRoutine.userExerciseLog.eq(userExerciseLog))
      .innerJoin(userExerciseSet).on(userExerciseSet.userExerciseRoutine.eq(userExerciseRoutine))
      .where(userExerciseLog.eq(targetUserExerciseLog)
        .and(userExerciseRoutine.isDeleted.isFalse()))
      .orderBy(userExerciseSet.setNumber.asc());

    return applyPagination(pageable, query);
  }

  private BooleanBuilder getUserExerciseLogWhereCondition(SearchUserExerciseLogParam param) {
    BooleanBuilder whereCondition = new BooleanBuilder();

    if (Objects.nonNull(param.exerciseLogId())) {
      whereCondition.and(userExerciseLog.id.eq(param.exerciseLogId()));
    }

    if (Objects.nonNull(param.exerciseDate())) {
      whereCondition.and(userExerciseLog.exerciseDate.eq(param.exerciseDate()));
    }

    if(!CollectionUtils.isEmpty(param.exerciseTimeTypes())) {
      whereCondition.and(userExerciseLog.exerciseTimeType.in(param.exerciseTimeTypes()));
    }

    return whereCondition;
  }

  private QUserExerciseRoutineVO getRoutineVO() {
    return new QUserExerciseRoutineVO(
      userExerciseRoutine.id,
      userExerciseRoutine.restTime,
      userExerciseRoutine.order,
      userExerciseRoutine.setCount,
      userExerciseRoutine.sumWeight,
      userExerciseRoutine.sumReps,
      userExerciseRoutine.sumTime,
      userExerciseRoutine.weightUnitType,
      exercise.id,
      exercise.name,
      exercise.bodyType
    );
  }

  private QUserExerciseSetVO getSetVO() {
    return new QUserExerciseSetVO(
      userExerciseRoutine.id,
      userExerciseSet.id,
      userExerciseSet.setNumber,
      userExerciseSet.exerciseSetType,
      userExerciseSet.weight,
      userExerciseSet.reps,
      userExerciseSet.time,
      userExerciseSet.complete
    );
  }
}
