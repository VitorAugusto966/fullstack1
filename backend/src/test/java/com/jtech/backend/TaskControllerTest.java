package com.jtech.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtech.backend.controller.TaskController;
import com.jtech.backend.dto.*;
import com.jtech.backend.model.TaskStatus;
import com.jtech.backend.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @SuppressWarnings("removal")
    @MockBean
    TaskService service;

    @Test
    void post_tasks_deveCriarERetornar201() throws Exception {
        TaskCreateRequest req = new TaskCreateRequest("Titulo", "Desc", TaskStatus.PENDING);
        TaskResponse res = new TaskResponse(1L, "Titulo", "Desc", TaskStatus.PENDING, null, null);

        given(service.create(any(TaskCreateRequest.class))).willReturn(res);

        mvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", containsString("/tasks/1")))
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.title", is("Titulo")));
    }

    @Test
    void get_tasks_deveRetornarLista() throws Exception {
        TaskResponse t = new TaskResponse(1L, "A", "B", TaskStatus.PENDING, null, null);
        given(service.findAll()).willReturn(List.of(t));

        mvc.perform(get("/tasks"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(1)))
           .andExpect(jsonPath("$[0].title", is("A")));
    }

    @Test
    void put_tasks_id_deveAtualizar() throws Exception {
        TaskUpdateRequest req = new TaskUpdateRequest("Novo", "Nova desc", TaskStatus.COMPLETED);
        TaskResponse res = new TaskResponse(1L, "Novo", "Nova desc", TaskStatus.COMPLETED, null, null);

        given(service.update(any(Long.class), any(TaskUpdateRequest.class))).willReturn(res);

        mvc.perform(put("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(req)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status", is("COMPLETED")));
    }

    @Test
    void delete_tasks_id_deveRetornar204() throws Exception {
        mvc.perform(delete("/tasks/1"))
           .andExpect(status().isNoContent());
    }
}