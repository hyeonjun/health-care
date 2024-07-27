package com.example.healthcare.application.exercise.service;

import com.example.healthcare.application.exercise.controller.dto.CreateUserExerciseLogDTO;
import com.example.healthcare.application.exercise.controller.dto.UpdateUserExerciseLogDTO;
import com.example.healthcare.application.exercise.repository.UserExerciseLogRepository;
import com.example.healthcare.application.exercise.repository.UserExerciseRoutineRepository;
import com.example.healthcare.application.exercise.repository.UserExerciseSetRepository;
import com.example.healthcare.application.vo.UserExerciseLogSummaryVO;
import com.example.healthcare.application.vo.UserExerciseLogDetailVO;
import com.example.healthcare.application.vo.UserExerciseLogVO;
import com.example.healthcare.infra.config.security.user.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserExerciseLogCmService {

  private final UserExerciseLogRepository userExerciseLogRepository;
  private final UserExerciseRoutineRepository userExerciseRoutineRepository;
  private final UserExerciseSetRepository userExerciseSetRepository;

  @Transactional
  public void createExerciseLog(LoginUser loginUser, CreateUserExerciseLogDTO dto) {

  }

  // Paging 28 ~ 31
  public Page<UserExerciseLogVO> getExerciseLogMonthly(LoginUser loginUser, Integer year, Integer month) {

    return null;
  }

  // Paging (max 2)
  public Page<UserExerciseLogSummaryVO> getExerciseLogDaily(LoginUser loginUser, Integer year, Integer month, Integer day) {

    return null;
  }

  // log calc info + inner (routine + set) info
  public UserExerciseLogDetailVO getExerciseLogDetail(LoginUser loginUser, Long exerciseLogId) {

    return null;
  }

  @Transactional
  public void updateExerciseLog(LoginUser loginUser, Long exerciseLogId, UpdateUserExerciseLogDTO dto) {
    // 삭제된 routine 없는지 확인
    // 삭제된 set 없는지 확인
  }

  @Transactional
  public void deleteExerciseLog(LoginUser loginUser, Long exerciseLogId) {
    // UserExerciseLog delete
    // UserExerciseRoutine bulk delete -> bulk update 구현
  }

}
