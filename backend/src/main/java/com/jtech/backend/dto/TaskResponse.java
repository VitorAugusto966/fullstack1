package com.jtech.backend.dto;

import com.jtech.backend.model.TaskStatus;
import java.time.Instant;

public record TaskResponse(
  Long id,
  String title,
  String description,
  TaskStatus status,
  Instant createdAt,
  Instant updatedAt
) {}
