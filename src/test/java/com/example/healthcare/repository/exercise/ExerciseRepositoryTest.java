package com.example.healthcare.repository.exercise;

import com.example.healthcare.application.exercise.domain.code.ExerciseBodyType;
import com.example.healthcare.application.exercise.repository.ExerciseRepository;
import com.example.healthcare.application.exercise.repository.param.SearchExerciseParam;
import com.example.healthcare.application.vo.ExerciseVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ExerciseRepositoryTest {

  @Autowired
  private ExerciseRepository exerciseRepository;

  @Test
  void findAllTest() {
    SearchExerciseParam param = new SearchExerciseParam(
      "name", ExerciseBodyType.LEG, 1L);

    Page<ExerciseVO> result = exerciseRepository.findAll(param, PageRequest.of(0, 100));
    assertThat(result).isNotNull();
  }
}
