package com.example.healthcare.application.exercise.domain;

import com.example.healthcare.application.account.domain.User;
import com.example.healthcare.application.common.domain.Base;
import com.example.healthcare.application.exercise.controller.dto.CreateExerciseDTO;
import com.example.healthcare.application.exercise.controller.dto.UpdateExerciseDTO;
import com.example.healthcare.application.exercise.domain.code.ExerciseBodyType;
import com.example.healthcare.application.exercise.domain.code.ExerciseToolType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;

import java.util.Objects;

@Entity
@DynamicUpdate
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
  name = "exercise",
  indexes = {
    @Index(columnList = "exercise_body_type, user_id")
  }
)
@SQLDelete(sql = "UPDATE exercise SET is_deleted = true WHERE exercise_id = ?")
public class Exercise extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "exercise_id")
  private Long id;

  @Column(name = "name", nullable = false, length = 191)
  private String name;

  @Column(name = "description", length = 3000)
  private String description;

  private boolean isDeleted;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "exercise_body_type", nullable = false, length = 191)
  private ExerciseBodyType bodyType;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "exercise_tool_type", nullable = false, length = 191)
  private ExerciseToolType toolType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
  private User createdUser;

  public static Exercise createExercise(CreateExerciseDTO dto, User user) {
    return builder()
      .name(dto.name())
      .description(dto.description())
      .isDeleted(Boolean.FALSE)
      .bodyType(dto.bodyType())
      .toolType(dto.toolType())
      .createdUser(user)
      .build();
  }

  public void updateExercise(UpdateExerciseDTO dto) {
    this.name = dto.name();
    this.description = dto.description();
    this.bodyType = dto.bodyType();
    this.toolType = dto.toolType();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Exercise exercise)) return false;
    return Objects.equals(getId(), exercise.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
      .append("id", id)
      .append("name", name)
      .append("description", description)
      .append("isDeleted", isDeleted)
      .append("bodyType", bodyType)
      .append("toolType", toolType)
      .append("createdUser", createdUser != null ? createdUser.getId() : null)
      .append("createdDateTime", createdDateTime)
      .append("updatedDateTime", updatedDateTime)
      .toString();
  }
}
