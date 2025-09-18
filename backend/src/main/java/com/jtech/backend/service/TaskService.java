package com.jtech.backend.service;

import com.jtech.backend.model.Task;
import com.jtech.backend.dto.*;
import com.jtech.backend.exception.NotFoundException;
import com.jtech.backend.repository.TaskRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository repository;

  public TaskResponse create(TaskCreateRequest req) {
    Task task = Task.builder()
        .title(req.title().trim())
        .description(req.description())
        .status(req.status())
        .build();
    return toResponse(repository.save(task));
  }

  public List<TaskResponse> findAll() {
    return repository.findAll().stream().map(this::toResponse).toList();
  }

  public TaskResponse findById(Long id) {
    return repository.findById(id)
        .map(this::toResponse)
        .orElseThrow(() -> new NotFoundException("Tarefa não encontrada"));
  }

  @Transactional
  public TaskResponse update(Long id, TaskUpdateRequest req) {
    Task task = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Tarefa não encontrada"));

    if (req.title() != null && !req.title().isBlank()) {
      task.setTitle(req.title().trim());
    }
    if (req.description() != null) {
      task.setDescription(req.description());
    }
    if (req.status() != null) {
      task.setStatus(req.status());
    }

    Task updated = repository.save(task);
    return toResponse(updated);
  }

  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new NotFoundException("Tarefa não encontrada");
    }
    repository.deleteById(id);
  }

  private TaskResponse toResponse(Task t) {
    return new TaskResponse(
        t.getId(), t.getTitle(), t.getDescription(), t.getStatus(),
        t.getCreatedAt(), t.getUpdatedAt());
  }
}
