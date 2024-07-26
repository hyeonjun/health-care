package com.example.healthcare.application.exercise.repository;

import com.example.healthcare.application.exercise.repository.param.SearchExerciseParam;
import com.example.healthcare.application.vo.ExerciseVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExerciseRepositoryCustom {

  Page<ExerciseVO> findAll(SearchExerciseParam param, Pageable pageable);

}
