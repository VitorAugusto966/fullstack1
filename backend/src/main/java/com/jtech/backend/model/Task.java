package com.jtech.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "tasks")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Task {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "text")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TaskStatus status;

  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  private Instant updatedAt;

  @PrePersist
  void onCreate() {
    this.createdAt = Instant.now();
    this.status = this.status == null ? TaskStatus.PENDING : this.status;
  }

  @PreUpdate
  void onUpdate() {
    this.updatedAt = Instant.now();
  }
}
