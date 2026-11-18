<script setup lang="ts">

import { computed, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { toast } from 'vue-sonner'
import { Pencil, Trash2, ListTodo } from '@lucide/vue'

import {
    AlertDialog,
    AlertDialogAction,
    AlertDialogCancel,
    AlertDialogContent,
    AlertDialogDescription,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogTitle,
} from '@/components/ui/alert-dialog'

import { Button } from '@/components/ui/button'
import AppContentSkeleton from '@/components/skeletons/AppContentSkeleton.vue'
import FadeTransition from '@/components/transitions/FadeTransition.vue'
import TaskListFormSheet from '../components/TaskListFormSheet.vue'

import { useDeleteTaskList } from '../composables/use-delete-task-list'
import { useTaskList } from '../composables/use-task-list'
import { useUpdateTaskList } from '../composables/use-update-task-list'

import type { UpdateTaskListRequest } from '../types/task-list.types'

const route = useRoute()
const router = useRouter()

const taskListId = computed(() => String(route.params.id))

const {
    data: taskList,
    isPending,
    isError,
    error,
    refetch
} = useTaskList(taskListId)

const {
    mutateAsync: updateTaskList,
    isPending: isUpdating,
    error: updateError,
    reset: resetUpdate,
} = useUpdateTaskList()

const {
    mutateAsync: deleteTaskList,
    isPending: isDeleting,
    reset: resetDelete,
} = useDeleteTaskList()

const isEditSheetOpen = ref(false)
const isDeleteDialogOpen = ref(false)

const initialValues = computed(() => ({
    title: taskList.value?.title ?? '',
    description: taskList.value?.description ?? ''
}))

const updateServerError = computed(() => updateError.value instanceof Error ? updateError.value.message : null)

async function handleUpdate(payload: UpdateTaskListRequest) {
    resetUpdate()

    try {
        await updateTaskList({
            id: taskListId.value,
            payload,
        })

        isEditSheetOpen.value = false
        toast.success('Task List updated successfully.')
    } catch (e) {
        console.log('There was an error updating the task list', e)
    }
}

async function handleDelete() {
    resetDelete()

    try {
        await deleteTaskList(taskListId.value)

        isDeleteDialogOpen.value = false
        toast.success('Task List deleted successfully.')

        await router.replace({ name: 'task-lists' })
    } catch (e) {
        toast.error('Unable to delete Task List. Please try again.')
    }
}

</script>

<template>
    <FadeTransition>
        <AppContentSkeleton v-if="isPending" />

        <section v-else-if="isError" class="flex min-h-full flex-col items-center justify-center gap-4 p-6 text-center">
            <p class="text-sm text-muted-foreground">
                {{ error instanceof Error ? error.message : 'Unable to load this task list.' }}
            </p>

            <Button variant="outline" @click="refetch()">
                Try again
            </Button>
        </section>

        <section v-else-if="taskList" class="mx-auto flex w-full max-w-5xl flex-col gap-8 p-6">
            <!-- Task List Header -->
            <header class="flex items-start justify-between gap-4">
                <div class="min-w-0 space-y-2">
                    <h1 class="text-2xl font-semibold tracking-tight">
                        {{ taskList.title }}
                    </h1>

                    <p class="text-sm text-muted-foreground">
                        {{ taskList.description || 'No description provided.' }}
                    </p>
                </div>

                <div class="flex shrink-0 items-center gap-2">
                    <Button variant="outline" size="sm" @click="isEditSheetOpen = true">
                        <Pencil class="mr-2 size-4" />
                        Edit
                    </Button>

                    <Button variant="destructive" size="sm" @click="isDeleteDialogOpen = true">
                        <Trash2 class="mr-2 size-4" />
                        Delete
                    </Button>
                </div>
            </header>

            <!-- Tasks Section -->
            <section class="space-y-4">
                <div class="flex items-center justify-between">
                    <h2 class="text-lg font-semibold">Tasks</h2>

                    <span class="text-sm text-muted-foreground">
                        {{ taskList.count }} tasks
                    </span>
                </div>

                <div
                    class="flex min-h-64 flex-col items-center justify-center gap-3 rounded-xl border border-dashed p-8 text-center">
                    <div class="flex size-12 items-center justify-center rounded-xl bg-primary/10">
                        <ListTodo class="size-6 text-primary" />
                    </div>

                    <h3 class="font-medium">No tasks yet</h3>

                    <p class="max-w-sm text-sm text-muted-foreground">
                        Tasks will appear here once you start adding them to this list.
                    </p>
                </div>
            </section>

            <TaskListFormSheet v-model:open="isEditSheetOpen" mode="edit" :initial-values="initialValues"
                :server-error="updateServerError" :loading="isUpdating" @submit="handleUpdate" />

            <AlertDialog v-model:open="isDeleteDialogOpen">
                <AlertDialogContent>
                    <AlertDialogHeader>
                        <AlertDialogTitle>
                            Delete Task List?
                        </AlertDialogTitle>

                        <AlertDialogDescription>
                            Are you sure you want to delete
                            <strong>{{ taskList.title }}</strong>?
                            This action cannot be undone.
                        </AlertDialogDescription>
                    </AlertDialogHeader>

                    <AlertDialogFooter>
                        <AlertDialogCancel :disabled="isDeleting">
                            Cancel
                        </AlertDialogCancel>

                        <AlertDialogAction :disabled="isDeleting"
                            class="bg-destructive text-white hover:bg-destructive/90" @click="handleDelete">
                            {{ isDeleting ? 'Deleting...' : 'Delete Task List' }}
                        </AlertDialogAction>
                    </AlertDialogFooter>
                </AlertDialogContent>
            </AlertDialog>
        </section>
    </FadeTransition>
</template>