package com.example.healthcare.application.exercise.repository;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.exercise.domain.UserExerciseLog;
import com.example.healthcare.application.exercise.repository.param.SearchUserExerciseLogParam;
import com.example.healthcare.application.vo.UserExerciseLogSummaryVO;
import com.example.healthcare.application.vo.UserExerciseLogVO;
import com.example.healthcare.application.vo.UserExerciseRoutineVO;
import com.example.healthcare.application.vo.UserExerciseSetVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserExerciseLogRepositoryCustom {

  boolean existExerciseLog(User user, SearchUserExerciseLogParam param);

  Page<UserExerciseLogVO> findExerciseLogMonthly(User user, Integer year, Integer month, Pageable pageable);
  Page<UserExerciseLogSummaryVO> findExerciseLogDaily(User user, Integer year, Integer month, Integer day, Pageable pageable);
  Page<UserExerciseRoutineVO> findExerciseRoutineAllByLog(UserExerciseLog userExerciseLog, Pageable pageable);
  Page<UserExerciseSetVO> findExerciseSetAllByLog(UserExerciseLog userExerciseLog, Pageable pageable);

}
