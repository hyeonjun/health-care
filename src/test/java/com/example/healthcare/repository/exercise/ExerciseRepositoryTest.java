package com.example.healthcare.repository.exercise;

import com.example.healthcare.application.account.domain.code.AuthorityType;
import com.example.healthcare.application.exercise.controller.dto.SearchExerciseDTO;
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
    SearchExerciseDTO dto = new SearchExerciseDTO();
    dto.setExerciseBodyType(ExerciseBodyType.LEG);
    dto.setName("name");

    SearchExerciseParam param = SearchExerciseParam.valueOf(dto, null, AuthorityType.GUEST);

    Page<ExerciseVO> result = exerciseRepository.findAll(param, PageRequest.of(0, 100));
    assertThat(result).isNotNull();
  }
}
