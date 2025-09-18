import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/HomeView.vue';
import TaskList from '@/views/TaskList.vue';
import CreateTask from '@/views/TaskForm.vue';
import TaskEdit from '@/views/TaskEdit.vue';

const routes = [
  { path: '/', component: Home },
  { path: '/tasks', component: TaskList },
  { path: '/nova', component: CreateTask },
  { path: '/edit/:id', component: TaskEdit},
];

export default createRouter({
  history: createWebHistory(),
  routes,
});
