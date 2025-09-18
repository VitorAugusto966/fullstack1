package com.jtech.backend.dto;

import com.jtech.backend.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskCreateRequest(
  @NotBlank @Size(max = 120) String title,
  @Size(max = 2000) String description,
  TaskStatus status
) {}
