package com.example.healthcare.base.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class Base {

  @CreationTimestamp
  protected LocalDateTime createdDateTime;
  @Setter
  @UpdateTimestamp
  protected LocalDateTime updatedDateTime;
}
