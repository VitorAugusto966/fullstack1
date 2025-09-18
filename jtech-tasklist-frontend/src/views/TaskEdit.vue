<template>
  <Header />
  <div class="edit-task-page">
    <form @submit.prevent="submit" class="edit-task-form">
      <h1>✏️ Editar Tarefa</h1>

      <!-- Título -->
      <div class="edit-task-group">
        <label for="title">Título</label>
        <input
          id="title"
          v-model="title"
          placeholder="Digite o título da tarefa"
        />
        <span v-if="v$.title.$error" class="edit-task-error">
          {{ v$.title.$errors[0].$message || 'Título inválido' }}
        </span>
      </div>

      <!-- Status -->
      <div class="edit-task-group">
        <label for="status">Status</label>
        <select id="status" v-model="status">
          <option value="PENDING">⏳ Pendente</option>
          <option value="COMPLETED">✅ Concluída</option>
        </select>
      </div>

      <!-- Descrição -->
      <div class="edit-task-group edit-task-full">
        <label for="description">Descrição</label>
        <textarea
          id="description"
          v-model="description"
          placeholder="Adicione uma descrição detalhada"
        ></textarea>
      </div>

      <!-- Ações -->
      <div class="edit-task-actions">
        <button type="submit" class="edit-task-save">
          💾 Salvar Alterações
        </button>
        <router-link to="/tasks" class="edit-task-cancel">
          ❌ Cancelar
        </router-link>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';
import { required, minLength, helpers } from '@vuelidate/validators';
import { getTaskById, updateTask } from '../service/taskService';
import Header from '@/components/Header.vue';
import '../styles/taskEdit.css';

const route = useRoute();
const router = useRouter();

const title = ref('');
const description = ref('');
const status = ref('PENDING');

const rules = {
  title: {
    required: helpers.withMessage('O título é obrigatório', required),
    minLength: helpers.withMessage(
      'O título deve conter pelo menos 3 caracteres',
      minLength(3)
    )
  },
  description: {},
  status: {
    required: helpers.withMessage('O status é obrigatório', required)
  }
};

const v$ = useVuelidate(rules, { title, description, status });

onMounted(async () => {
  const { data } = await getTaskById(route.params.id);
  title.value = data.title;
  description.value = data.description;
  status.value = data.status;
});

const submit = async () => {
  v$.value.$touch();
  if (!v$.value.$invalid) {
    await updateTask(route.params.id, {
      title: title.value,
      description: description.value,
      status: status.value
    });
    router.push('/tasks');
  }
};
</script>
