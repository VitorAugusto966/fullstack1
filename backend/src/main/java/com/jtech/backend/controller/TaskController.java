package com.jtech.backend.controller;

import com.jtech.backend.dto.*;
import com.jtech.backend.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

  private final TaskService service;

  @PostMapping
  public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskCreateRequest req) {
    TaskResponse created = service.create(req);
    return ResponseEntity.created(URI.create("/tasks/" + created.id())).body(created);
  }

  @GetMapping
  public List<TaskResponse> list() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public TaskResponse get(@PathVariable Long id) {
    return service.findById(id);
  }

  @PutMapping("/{id}")
  public TaskResponse update(@PathVariable Long id, @Valid @RequestBody TaskUpdateRequest req) {
    return service.update(id, req);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
