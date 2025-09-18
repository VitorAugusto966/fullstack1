<template>
  <Header/>
  <div class="create-task-page">
    <form class="create-task-form" @submit.prevent="submit">
      <h1>➕ Nova Tarefa</h1>

      <div class="create-task-group">
        <input v-model="title" placeholder="Título da tarefa" />
        <span v-if="v$.title.$error" class="create-task-error">
          {{ v$.title.$errors[0].$message || 'Título inválido' }}
        </span>
      </div>

      <div class="create-task-group">
        <select v-model="status">
          <option value="PENDING">⏳ Pendente</option>
          <option value="COMPLETED">✅ Concluída</option>
        </select>
      </div>

      <div class="create-task-group create-task-full">
        <textarea v-model="description" placeholder="Descrição detalhada (opcional)"></textarea>
      </div>

      <div class="create-task-actions">
        <button type="submit" class="create-task-btn">Criar Tarefa</button>
        <router-link to="/" class="create-task-cancel">Cancelar</router-link>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';
import { required, minLength } from '@vuelidate/validators';
import { createTask } from '../service/taskService';
import Header from '@/components/Header.vue';
import '../styles/taskForm.css';

const title = ref('');
const description = ref('');
const status = ref('PENDING');
const router = useRouter();

const rules = {
  title: { required, minLength: minLength(3) },
  description: {},
  status: { required }
};

const v$ = useVuelidate(rules, { title, description, status });

const submit = async () => {
  v$.value.$touch();
  if (!v$.value.$invalid) {
    await createTask({
      title: title.value,
      description: description.value,
      status: status.value
    });
    router.push('/tasks');
  }
};
</script>
