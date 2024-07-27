package com.example.healthcare.application.exercise.repository;

import com.example.healthcare.application.exercise.domain.UserExerciseLog;
import com.example.healthcare.application.vo.UserExerciseRoutineVO;
import com.example.healthcare.application.vo.UserExerciseSetVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserExerciseLogRepositoryCustom {

  Page<UserExerciseRoutineVO> findExerciseRoutineAllByLog(UserExerciseLog userExerciseLog, Pageable pageable);
  Page<UserExerciseSetVO> findExerciseSetAllByLog(UserExerciseLog userExerciseLog, Pageable pageable);

}
