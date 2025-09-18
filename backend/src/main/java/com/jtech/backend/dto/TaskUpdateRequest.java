package com.jtech.backend.dto;

import com.jtech.backend.model.TaskStatus;
import jakarta.validation.constraints.Size;


public record TaskUpdateRequest(
  @Size(max = 120) String title,
  @Size(max = 2000) String description,
  TaskStatus status
) {}
