<template>
  <main class="home-container">
    <section class="hero">
      <h1>📋 Sistema de Tarefas</h1>
      <p class="subtitle">
        Organize suas atividades de forma simples, moderna e eficiente.
      </p>
    </section>

    <div class="card-grid">
      <router-link to="/tasks" class="card">
        <div class="icon">📂</div>
        <h2>Ver Tarefas</h2>
        <p>Acesse a lista completa de tarefas cadastradas.</p>
      </router-link>

      <router-link to="/nova" class="card">
        <div class="icon">➕</div>
        <h2>Criar Nova Tarefa</h2>
        <p>Adicione uma nova tarefa à sua lista.</p>
      </router-link>
    </div>
  </main>
</template>

<script setup lang="ts">
</script>

<style scoped>
.home-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 3rem 2rem;
  font-family: "Inter", "Segoe UI", sans-serif;
  background: linear-gradient(135deg, #f9fafb 0%, #eef2ff 100%);
}

.hero {
  text-align: center;
  max-width: 700px;
  margin-bottom: 3rem;
}

h1 {
  font-size: 3rem;
  font-weight: 800;
  color: #1f2937;
  margin-bottom: 1rem;
  letter-spacing: -1px;
}

.subtitle {
  font-size: 1.2rem;
  color: #6b7280;
  line-height: 1.6;
}

.card-grid {
  display: flex;
  gap: 2rem;
  justify-content: center;
  flex-wrap: wrap;
  width: 100%;
  max-width: 900px;
}

.card {
  background: #ffffff;
  padding: 2rem 1.5rem;
  border-radius: 18px;
  text-decoration: none;
  color: #374151;
  width: 270px;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.06);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  border: 1px solid #f3f4f6;
}

.card:hover {
  transform: translateY(-8px) scale(1.03);
  box-shadow: 0 12px 28px rgba(0, 0, 0, 0.15);
}

.icon {
  font-size: 2rem;
  margin-bottom: 1rem;
}

.card h2 {
  margin-bottom: 0.6rem;
  background: linear-gradient(90deg, #2563eb, #7c3aed);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  font-size: 1.4rem;
  font-weight: 700;
}

.card p {
  font-size: 1rem;
  color: #6b7280;
  text-align: center;
  line-height: 1.5;
}

@media (max-width: 600px) {
  h1 {
    font-size: 2.2rem;
  }

  .card {
    width: 100%;
  }
}
</style>
