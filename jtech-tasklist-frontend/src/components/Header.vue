<template>
  <nav class="tasklist-navbar">
    <div class="tasklist-nav-content">
      <h2 class="tasklist-logo" @click="goToHome">📋 Task Manager</h2>
      <div class="tasklist-nav-links">
        <router-link to="/" class="tasklist-nav-btn">🏠 Home</router-link>
        <router-link to="/nova" class="tasklist-nav-btn">➕ Nova Tarefa</router-link>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const goToHome = () => {
  router.push('/');
};
</script>

<style scoped>
.tasklist-navbar {
  background-color: #2563eb;
  padding: 0.8rem 2rem;
  color: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.tasklist-nav-content {
  max-width: 1400px;
  width: 100%;
  margin: auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
}

.tasklist-logo {
  font-size: 1.4rem;
  font-weight: 700;
  cursor: pointer; 
}

.tasklist-nav-links {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.tasklist-nav-btn {
  background-color: rgba(255, 255, 255, 0.15);
  padding: 0.5rem 1rem;
  border-radius: 6px;
  color: white;
  text-decoration: none;
  font-weight: 500;
  transition: background-color 0.2s ease;
}

.tasklist-nav-btn:hover {
  background-color: rgba(255, 255, 255, 0.3);
}

@media (max-width: 768px) {
  .tasklist-nav-content {
    flex-direction: column;
    align-items: flex-start;
    gap: 0.8rem;
  }

  .tasklist-nav-links {
    flex-direction: column;
    width: 100%;
  }

  .tasklist-nav-btn {
    width: 100%;
    text-align: left;
  }
}
</style>
