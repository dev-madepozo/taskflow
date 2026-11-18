import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: () => import('@/app/layouts/AppLayout.vue'),
      children: [
        {
          path: '',
          name: 'task-lists',
          component: () => import('@/features/task-lists/views/TaskListsView.vue'),
        },
        {
          path: 'task-lists/:id',
          name: 'task-list-detail',
          component: () => import('@/features/task-lists/views/TaskListDetailView.vue'),
        },
        {
          path: 'design-system',
          name: 'design-system',
          component: () => import('@/features/design-system/views/DesignSystemView.vue'),
        }
      ]
    },
  ],
})

export default router
