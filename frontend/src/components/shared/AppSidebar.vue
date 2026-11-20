<script setup lang="ts">

import AppLogo from './AppLogo.vue'
import ThemeToggle from './ThemeToggle.vue'
import { Button } from '@/components/ui/button'

import { ListTodo, Plus } from '@lucide/vue'
import { RouterLink } from 'vue-router'

import type { TaskListSummary } from '@/features/task-lists/types/task-list.types'

defineProps<{
    taskLists: TaskListSummary[]
}>()

const emit = defineEmits<{
    create: []
}>()

</script>

<template>
    <aside
        class="flex h-full w-64 shrink-0 flex-col overflow-hidden border-r border-sidebar-border bg-sidebar text-sidebar-foreground">
        <!-- Header -->
        <div class="flex h-16 shrink-0 items-center border-b border-sidebar-border px-6">
            <AppLogo />
        </div>

        <!-- Navigation -->
        <div class="min-h-0 flex-1 overflow-y-auto px-4 py-6">
            <div class="mb-4 flex items-center justify-between px-2">
                <h2 class="text-xs font-semibold tracking-wider text-muted-foreground uppercase">
                    Task Lists
                </h2>

                <Button variant="ghost" size="sm" class="h-7 gap-1.5 px-2 text-xs" aria-label="Create Task List"
                    @click="emit('create')">
                    <Plus class="size-4" />
                </Button>
            </div>

            <template v-if="taskLists.length">
                <nav class="flex flex-col gap-1">
                    <RouterLink v-for="taskList in taskLists" :key="taskList.id"
                        :to="{ name: 'task-list-detail', params: { id: taskList.id } }" v-slot="{ isActive }">
                        <a class="flex items-center gap-3 rounded-md px-3 py-2 text-sm transition-colors" :class="isActive
                            ? 'bg-sidebar-accent text-sidebar-accent-foreground font-medium'
                            : 'text-muted-foreground hover:bg-sidebar-accent/50 hover:text-sidebar-accent-foreground'">
                            <ListTodo class="size-4 shrink-0" />
                            <span class="truncate">{{ taskList.title }} ({{ taskList.count }})</span>
                        </a>
                    </RouterLink>
                </nav>
            </template>

            <div v-else
                class="flex flex-col items-center justify-center gap-2 rounded-lg border border-dashed border-sidebar-border px-4 py-8 text-center">
                <p class="text-sm font-medium text-muted-foreground">
                    No lists yet
                </p>
                <p class="text-xs text-muted-foreground">
                    Your task lists will appear here.
                </p>
            </div>
        </div>

        <!-- Footer -->
        <div class="shrink-0 border-t border-sidebar-border px-4 py-4">
            <div class="flex justify-center pb-3">
                <ThemeToggle />
            </div>

            <p class="text-center text-xs text-muted-foreground">
                © {{ new Date().getFullYear() }} TaskFlow. All rights reserved.
            </p>
        </div>
    </aside>
</template>
