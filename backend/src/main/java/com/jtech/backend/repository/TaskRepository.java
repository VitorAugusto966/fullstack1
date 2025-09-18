package com.jtech.backend.repository;

import com.jtech.backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Long> {

}
