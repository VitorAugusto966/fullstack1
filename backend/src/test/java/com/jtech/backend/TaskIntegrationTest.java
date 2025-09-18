package com.jtech.backend;

import com.jtech.backend.dto.TaskCreateRequest;
import com.jtech.backend.dto.TaskResponse;
import com.jtech.backend.dto.TaskUpdateRequest;
import com.jtech.backend.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate rest;

    private String baseUrl() {
        return "http://localhost:" + port + "/tasks";
    }

    @Test
    void fluxoCompleto_criar_listar_buscar_atualizar_deletar() {
        // 1. Criar tarefa
        TaskCreateRequest createReq = new TaskCreateRequest("Tarefa Integração", "Descrição teste", TaskStatus.PENDING);
        ResponseEntity<TaskResponse> createRes = rest.postForEntity(baseUrl(), createReq, TaskResponse.class);

        assertThat(createRes.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        TaskResponse criada = createRes.getBody();
        assertThat(criada).isNotNull();
        assertThat(criada.id()).isNotNull();

        Long id = criada.id();

        // 2. Listar tarefas
        ResponseEntity<TaskResponse[]> listRes = rest.getForEntity(baseUrl(), TaskResponse[].class);
        assertThat(listRes.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<TaskResponse> lista = Arrays.asList(listRes.getBody());
        assertThat(lista).extracting(TaskResponse::id).contains(id);

        // 3. Buscar por ID
        ResponseEntity<TaskResponse> getRes = rest.getForEntity(baseUrl() + "/" + id, TaskResponse.class);
        assertThat(getRes.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getRes.getBody().title()).isEqualTo("Tarefa Integração");

        // 4. Atualizar tarefa
        TaskUpdateRequest updateReq = new TaskUpdateRequest("Tarefa Atualizada", "Nova descrição", TaskStatus.COMPLETED);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TaskUpdateRequest> updateEntity = new HttpEntity<>(updateReq, headers);

        ResponseEntity<TaskResponse> updateRes = rest.exchange(
                baseUrl() + "/" + id,
                HttpMethod.PUT,
                updateEntity,
                TaskResponse.class
        );

        assertThat(updateRes.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateRes.getBody().status()).isEqualTo(TaskStatus.COMPLETED);

        // 5. Deletar tarefa
        rest.delete(baseUrl() + "/" + id);

        // 6. Verificar que não existe mais
        ResponseEntity<String> notFoundRes = rest.getForEntity(baseUrl() + "/" + id, String.class);
        assertThat(notFoundRes.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
