package com.example.healthcare.application.exercise.repository;

import com.example.healthcare.application.exercise.domain.UserExerciseLog;
import com.example.healthcare.application.vo.QUserExerciseRoutineVO;
import com.example.healthcare.application.vo.QUserExerciseSetVO;
import com.example.healthcare.application.vo.UserExerciseRoutineVO;
import com.example.healthcare.application.vo.UserExerciseSetVO;
import com.example.healthcare.util.repository.CustomQuerydslRepositorySupport;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
