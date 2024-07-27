package com.example.healthcare.application.exercise.controller;

import com.example.healthcare.application.common.response.CommonResponse;
import com.example.healthcare.application.exercise.controller.dto.CreateUserExerciseLogDTO;
import com.example.healthcare.application.exercise.controller.dto.UpdateUserExerciseLogDTO;
import com.example.healthcare.application.exercise.service.UserExerciseLogCmService;
import com.example.healthcare.application.vo.UserExerciseLogSummaryVO;
import com.example.healthcare.application.vo.UserExerciseLogVO;
import com.example.healthcare.infra.config.security.user.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cm/exercise-logs")
public class UserExerciseLogCmController {

  private final UserExerciseLogCmService userExerciseLogCmService;

  @PostMapping
  public CommonResponse<Void> createExerciseLog(@AuthenticationPrincipal LoginUser loginUser,
     @Valid @RequestBody CreateUserExerciseLogDTO dto) {
    userExerciseLogCmService.createExerciseLog(loginUser, dto);
    return CommonResponse.success();
  }

  // 운동 기록 월별 조회
  @GetMapping("/monthly")
  public CommonResponse<Page<UserExerciseLogVO>> getExerciseLogMonthly(@AuthenticationPrincipal LoginUser loginUser,
    @RequestParam("yesr") Integer year,
    @RequestParam("month") Integer month) {
    return CommonResponse.success(userExerciseLogCmService.getExerciseLogMonthly(loginUser, year, month));
  }

  // 운동 기록 일별 (시간대별) 조회
  @GetMapping("/daily")
  public CommonResponse<Page<UserExerciseLogSummaryVO>> getExerciseLogDaily(@AuthenticationPrincipal LoginUser loginUser,
    @RequestParam("yesr") Integer year,
    @RequestParam("month") Integer month,
    @RequestParam("day") Integer day) {
    return CommonResponse.success(userExerciseLogCmService.getExerciseLogDaily(loginUser, year, month, day));
  }

  // 운동 기록 상세 조회
  @GetMapping("/{exercise-log-id}")
  public CommonResponse<Void> getExerciseLogDetail(@AuthenticationPrincipal LoginUser loginUser,
    @PathVariable(value = "exercise-log-id") Long exerciseLogId) {
    return CommonResponse.success();
  }

  // 운동 기록 편집 (생성DTO+각 id를 받아 전체 수정)
  @PutMapping("/{exercise-log-id}")
  public CommonResponse<Void> updateExerciseLog(@AuthenticationPrincipal LoginUser loginUser,
    @PathVariable(value = "exercise-log-id") Long exerciseLogId,
    @Valid @RequestBody UpdateUserExerciseLogDTO dto) {
    return CommonResponse.success();
  }

  // 운동 기록 삭제
  @DeleteMapping("/{exercise-log-id}")
  public CommonResponse<Void> deleteExerciseLog(@AuthenticationPrincipal LoginUser loginUser,
    @PathVariable(value = "exercise-log-id") Long exerciseLogId) {
    return CommonResponse.success();
  }
}
