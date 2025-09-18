<template>
  <div class="tasklist-page">
    <Header />
    <div class="tasklist-container">
      <h1 class="tasklist-title">Minhas Tarefas</h1>

      <div v-if="tasks.length" class="tasklist-table-wrapper">
        <table class="tasklist-table">
          <thead>
            <tr>
              <th>Título</th>
              <th>Status</th>
              <th class="tasklist-actions-header">Ações</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="task in tasks" :key="task.id">
              <td class="tasklist-title-cell">{{ task.title }}</td>
              <td>
                <span class="tasklist-status" :class="statusClass(task.status)">
                  {{ traduzStatus(task.status) }}
                </span>
              </td>
              <td class="tasklist-actions">
                <button class="tasklist-edit-btn" @click="edit(task.id)" :aria-label="`Editar tarefa ${task.title}`"
                  title="Editar">
                  ✏️
                </button>
                <button class="tasklist-delete-btn" @click="remove(task.id)"
                  :aria-label="`Excluir tarefa ${task.title}`" title="Excluir">
                  🗑️
                </button>
                <button @click="toggleStatus(task)"
                  :class="task.status === 'COMPLETED' ? 'tasklist-reopen-btn' : 'tasklist-complete-btn'"
                  :aria-label="task.status === 'COMPLETED' ? `Reabrir tarefa ${task.title}` : `Concluir tarefa ${task.title}`"
                  :title="task.status === 'COMPLETED' ? 'Reabrir' : 'Concluir'">
                  {{ task.status === 'COMPLETED' ? '⏳' : '✅' }}
                </button>


              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <p v-else class="tasklist-empty">Nenhuma tarefa encontrada.</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getTasks, deleteTask, updateTask } from '../service/taskService';
import { useRouter } from 'vue-router';
import Header from '@/components/Header.vue';
import '../styles/taskList.css';

const tasks = ref([]);
const router = useRouter();

const traduzStatus = (status) => {
  switch (status) {
    case 'PENDING':
      return 'Pendente';
    case 'COMPLETED':
      return 'Concluída';
    default:
      return status;
  }
};

const statusClass = (status) => {
  return status === 'COMPLETED'
    ? 'tasklist-status-completed'
    : 'tasklist-status-pending';
};

const loadTasks = async () => {
  const res = await getTasks();
  tasks.value = res.data;
};

const remove = async (id) => {
  const tarefa = tasks.value.find(t => t.id === id);
  const confirmado = window.confirm(`Tem certeza que deseja excluir a tarefa "${tarefa?.title}"?`);
  if (!confirmado) return;

  await deleteTask(id);
  await loadTasks();
};

const getNextStatus = (status) => 
  status === 'COMPLETED' ? 'PENDING' : 'COMPLETED';

const toggleStatus = async (task) => {
  await updateTask(task.id, { ...task, status: getNextStatus(task.status) });
  await loadTasks();
};

const edit = (id) => {
  router.push(`/edit/${id}`);
};

onMounted(loadTasks);
</script>
