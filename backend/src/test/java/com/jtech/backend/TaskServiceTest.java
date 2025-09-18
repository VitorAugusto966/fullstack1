package com.jtech.backend;

import com.jtech.backend.dto.*;
import com.jtech.backend.exception.NotFoundException;
import com.jtech.backend.model.Task;
import com.jtech.backend.model.TaskStatus;
import com.jtech.backend.repository.TaskRepository;
import com.jtech.backend.service.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository repository;

    @InjectMocks
    TaskService service; 

    Task task;

    @BeforeEach
    void setup() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Estudar Spring");
        task.setDescription("Focar em testes");
        task.setStatus(TaskStatus.PENDING);
    }

    @Test
    void create_deveCriarComSucesso() {
        TaskCreateRequest req = new TaskCreateRequest("Nova tarefa", "Desc", TaskStatus.PENDING);
        when(repository.save(any(Task.class))).thenAnswer(inv -> {
            Task t = inv.getArgument(0);
            t.setId(10L);
            return t;
        });

        TaskResponse res = service.create(req);

        assertThat(res.id()).isEqualTo(10L);
        assertThat(res.title()).isEqualTo("Nova tarefa");
        verify(repository, times(1)).save(any(Task.class));
    }

    @Test
    void findAll_deveRetornarLista() {
        when(repository.findAll()).thenReturn(List.of(task));
        List<TaskResponse> list = service.findAll();
        assertThat(list).hasSize(1);
        assertThat(list.get(0).title()).isEqualTo("Estudar Spring");
    }

    @Test
    void findById_quandoExiste_retornaTask() {
        when(repository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponse res = service.findById(1L);

        assertThat(res.id()).isEqualTo(1L);
        assertThat(res.status()).isEqualTo(TaskStatus.PENDING);
    }

    @Test
    void findById_quandoNaoExiste_lancaNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(999L))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining("Tarefa não encontrada");
    }

    @Test
    void update_deveAtualizarCamposParciaisESalvar() {
        when(repository.findById(1L)).thenReturn(Optional.of(task));
        when(repository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        TaskUpdateRequest req = new TaskUpdateRequest("Título novo", "Desc nova", TaskStatus.COMPLETED);

        TaskResponse res = service.update(1L, req);

        assertThat(res.title()).isEqualTo("Título novo");
        assertThat(res.status()).isEqualTo(TaskStatus.COMPLETED);
        verify(repository).save(any(Task.class));
    }

    @Test
    void update_quandoNaoExiste_lancaNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        TaskUpdateRequest req = new TaskUpdateRequest("x", "y", TaskStatus.PENDING);
        assertThatThrownBy(() -> service.update(1L, req))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    void delete_quandoExiste_deveRemover() {
        when(repository.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void delete_quandoNaoExiste_lancaNotFound() {
        when(repository.existsById(999L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(999L))
            .isInstanceOf(NotFoundException.class);
    }
}
