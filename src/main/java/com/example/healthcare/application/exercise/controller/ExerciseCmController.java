package com.example.healthcare.application.exercise.controller;

import com.example.healthcare.application.common.response.CommonResponse;
import com.example.healthcare.application.exercise.controller.dto.CreateExerciseDTO;
import com.example.healthcare.application.exercise.controller.dto.SearchExerciseDTO;
import com.example.healthcare.application.exercise.controller.dto.UpdateExerciseDTO;
import com.example.healthcare.application.exercise.service.ExerciseCmService;
import com.example.healthcare.application.vo.ExerciseDetailVO;
import com.example.healthcare.application.vo.ExerciseVO;
import com.example.healthcare.infra.config.security.user.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cm/exercises")
public class ExerciseCmController {

  private final ExerciseCmService exerciseCmService;

  // 개인 운동 종목 생성
  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
  public CommonResponse<Void> createExercise(@Valid @RequestBody CreateExerciseDTO dto,
    @AuthenticationPrincipal LoginUser loginUser) {
    exerciseCmService.createExercise(loginUser, dto);
    return CommonResponse.success();
  }

  // 운동 종목 목록 조회
  @GetMapping
  public CommonResponse<Page<ExerciseVO>> getExercises(@Valid SearchExerciseDTO dto,
    @AuthenticationPrincipal LoginUser loginUser) {
    return CommonResponse.success(exerciseCmService.getExercises(loginUser, dto));
  }

  // 운동 종목 상세 조회
  @GetMapping("/{exercise-id}")
  public CommonResponse<ExerciseDetailVO> getExerciseDetail(@PathVariable(value = "exercise-id") Long exerciseId,
    @AuthenticationPrincipal LoginUser loginUser) {
    return CommonResponse.success(exerciseCmService.getExercise(loginUser, exerciseId));
  }

  // 개인 운동 종목 수정
  @PutMapping("/{exercise-id}")
  public CommonResponse<Void> updateExercise(@PathVariable(value = "exercise-id") Long exerciseId,
    @Valid @RequestBody UpdateExerciseDTO dto,
    @AuthenticationPrincipal LoginUser loginUser) {
    exerciseCmService.updateExercise(loginUser, exerciseId, dto);
    return CommonResponse.success();
  }

  // 개인 운동 종목 삭제
  @DeleteMapping("/{exercise-id}")
  public CommonResponse<Void> deleteExercise(@PathVariable(value = "exercise-id") Long exerciseId,
    @AuthenticationPrincipal LoginUser loginUser) {
    exerciseCmService.deleteExercise(loginUser, exerciseId);
    return CommonResponse.success();
  }

}
