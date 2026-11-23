<script setup lang="ts">

import { computed, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { toast } from 'vue-sonner'
import { Pencil, Trash2, ListTodo, Folder, Plus } from '@lucide/vue'

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

import TaskFormSheet from '@/features/tasks/components/TaskFormSheet.vue'

import { useTasks } from '@/features/tasks/composables/use-tasks'
import { useCreateTask } from '@/features/tasks/composables/use-create-task'
import { useUpdateTask } from '@/features/tasks/composables/use-update-task'
import { useDeleteTask } from '@/features/tasks/composables/use-delete-task'

import type {
    TaskFormValues,
    CreateTaskRequest,
    UpdateTaskRequest,
    Task,
    TaskStatus,
} from '@/features/tasks/types/task.types'
import { formatDate } from '@/lib/date'

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

/*  Composables for task list operations */
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

/* Composables for task operations */

const currentPage = ref(0)
const pageSize = ref(10)
const selectedStatus = ref<TaskStatus | undefined>()

const {
    data: tasksResponse,
    isPending: isTasksPending,
    isError: isTasksError,
    error: tasksError,
    refetch: refetchTasks,
} = useTasks(taskListId, {
    page: currentPage,
    size: pageSize,
    status: selectedStatus
})

const {
    mutateAsync: createTask,
    isPending: isCreatingTask,
    error: createTaskError,
    reset: resetCreateTask,
} = useCreateTask()

const {
    mutateAsync: updateTask,
    isPending: isUpdatingTask,
    error: updateTaskError,
    reset: resetUpdateTask,
} = useUpdateTask()

const {
    mutateAsync: deleteTask,
    isPending: isDeletingTask,
    error: deleteTaskError,
    reset: resetDeleteTask,
} = useDeleteTask()

const selectedTask = ref<Task | null>(null)
const taskToDelete = ref<Task | null>(null)
const isTaskSheetOpen = ref(false)
const isDeleteTaskDialogOpen = ref(false)
const tasks = computed(() => tasksResponse.value?.content ?? [])

// pagination control
const totalTasks = computed(() => tasksResponse.value?.totalElements ?? 0)
const totalPages = computed(() => tasksResponse.value?.totalPages ?? 0)
const completedTasks = computed(() =>
    tasks.value.filter(task => task.status === 'CLOSED').length
)
const progress = computed(() =>
    totalTasks.value ? Math.round((completedTasks.value / totalTasks.value) * 100) : 0
)

const taskFormMode = computed<'create' | 'edit'>(() =>
    selectedTask.value ? 'edit' : 'create',
)

const taskInitialValues = computed<TaskFormValues>(() => {
    const task = selectedTask.value

    if (!task) {
        return {
            title: '',
            description: '',
            dueDate: '',
            priority: 'MEDIUM',
        }
    }

    return {
        title: task.title,
        description: task.description,
        dueDate: task.dueDate?.slice(0, 10) ?? '',
        priority: task.priority,
    }
})

const taskFormServerError = computed(() => {
    const error = selectedTask.value
        ? updateTaskError.value
        : createTaskError.value

    return error instanceof Error ? error.message : null
})

const deleteTaskServerError = computed(() =>
    deleteTaskError.value instanceof Error
        ? deleteTaskError.value.message
        : null,
)

function changeStatus(status?: TaskStatus) {
    selectedStatus.value = status
    currentPage.value = 0
}

function changePage(page: number) {
    if (page < 0 || page >= totalPages.value) return
    currentPage.value = page
}

function handleCreateTask() {
    selectedTask.value = null
    resetCreateTask()
    resetUpdateTask()
    isTaskSheetOpen.value = true
}


function handleEditTask(task: Task) {
    selectedTask.value = task
    resetCreateTask()
    resetUpdateTask()
    isTaskSheetOpen.value = true
}

function handleDeleteTask(task: Task) {
    taskToDelete.value = task
    resetDeleteTask()
    isDeleteTaskDialogOpen.value = true
}

const handleSaveTask = async (values: TaskFormValues) => {
    resetCreateTask()

    const payload: CreateTaskRequest = {
        title: values.title,
        description: values.description,
        dueDate: values.dueDate ? `${values.dueDate}T00:00:00` : null,
        priority: values.priority,
        status: 'OPEN',
    }

    try {
        await createTask({
            taskListId: taskListId.value,
            payload,
        })

        isTaskSheetOpen.value = false
        toast.success('Task created successfully.')
    } catch (e) {
        console.error('There was an error creating the task:', e)
    }
}

async function handleUpdateTask(values: TaskFormValues) {
    if (!selectedTask.value) return

    resetUpdateTask()

    const payload: UpdateTaskRequest = {
        title: values.title,
        description: values.description,
        dueDate: values.dueDate
            ? `${values.dueDate}T00:00:00`
            : null,
        priority: values.priority,
        status: selectedTask.value.status,
    }

    try {
        await updateTask({
            taskListId: taskListId.value,
            taskId: selectedTask.value.id,
            payload,
        })

        isTaskSheetOpen.value = false
        selectedTask.value = null

        toast.success('Task updated successfully.')
    } catch (error) {
        console.error('Failed to update task:', error)
    }
}

async function handleToggleTaskStatus(task: Task) {
    const payload: UpdateTaskRequest = {
        title: task.title,
        description: task.description,
        dueDate: task.dueDate,
        priority: task.priority,
        status: task.status === 'OPEN' ? 'CLOSED' : 'OPEN',
    }

    try {
        await updateTask({
            taskListId: taskListId.value,
            taskId: task.id,
            payload,
        })

        toast.success(
            payload.status === 'CLOSED'
                ? 'Task completed.'
                : 'Task reopened.'
        )
    } catch (error) {
        toast.error('Unable to update task status.')
        console.error('Failed to update task status:', error)
    }
}

async function handleTaskSubmit(values: TaskFormValues) {
    if (selectedTask.value) {
        await handleUpdateTask(values)
    } else {
        await handleSaveTask(values)
    }
}

async function confirmDeleteTask() {
    if (!taskToDelete.value) return

    try {
        await deleteTask({
            taskListId: taskListId.value,
            taskId: taskToDelete.value.id,
        })

        isDeleteTaskDialogOpen.value = false
        toast.success('Task deleted successfully.')
        taskToDelete.value = null
    } catch (e) {
        console.error('Failed to delete task:', e)
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

        <section v-else-if="taskList" class="mx-auto flex w-full max-w-6xl flex-col gap-8 p-6 lg:p-8">
            <!-- Task List Header -->
            <header
                class="flex items-start justify-between gap-4 rounded-2xl border border-border bg-card p-5 shadow-sm">
                <div class="flex min-w-0 items-start gap-4">
                    <div class="flex size-14 shrink-0 items-center justify-center rounded-2xl bg-primary shadow-sm">
                        <Folder class="size-7 text-primary-foreground" />
                    </div>

                    <div class="min-w-0 space-y-1">
                        <h1 class="text-2xl font-bold tracking-tight">
                            {{ taskList.title }}
                        </h1>

                        <p class="text-sm leading-relaxed text-muted-foreground">
                            {{ taskList.description || 'No description provided.' }}
                        </p>
                    </div>
                </div>

                <div class="flex shrink-0 items-center gap-1">
                    <Button variant="ghost" size="icon" aria-label="Edit task list" @click="isEditSheetOpen = true">
                        <Pencil class="size-4" />
                    </Button>

                    <Button variant="ghost" size="icon" aria-label="Delete task list"
                        class="text-destructive hover:bg-destructive/10 hover:text-destructive"
                        @click="isDeleteDialogOpen = true">
                        <Trash2 class="size-4" />
                    </Button>
                </div>
            </header>

            <!-- Tasks Section -->
            <section class="space-y-5">
                <!-- Section Header -->
                <div class="flex flex-wrap items-center justify-between gap-4">
                    <div class="space-y-1">
                        <h2 class="text-lg font-semibold tracking-tight">Tasks</h2>
                        <p class="text-sm text-muted-foreground">
                            {{ totalTasks }} {{ totalTasks === 1 ? 'task' : 'tasks' }}
                        </p>
                    </div>

                    <Button @click="handleCreateTask">
                        <Plus class="size-4" />
                        Add Task
                    </Button>
                </div>

                <div class="space-y-3 rounded-xl border border-border bg-card p-4">
                    <div class="flex items-center justify-between text-sm">
                        <span class="font-medium">Progress</span>
                        <span class="text-muted-foreground">
                            {{ completedTasks }} of {{ totalTasks }} completed
                        </span>
                    </div>

                    <div class="h-2 overflow-hidden rounded-full bg-muted">
                        <div class="h-full rounded-full bg-primary transition-all" :style="{ width: `${progress}%` }" />
                    </div>

                    <p class="text-right text-xs text-muted-foreground">{{ progress }}%</p>
                </div>

                <div class="flex flex-wrap items-center justify-between gap-3">
                    <div class="flex flex-wrap gap-2">
                        <Button size="sm" :variant="selectedStatus === undefined ? 'default' : 'outline'"
                            @click="changeStatus(undefined)">
                            All
                        </Button>

                        <Button size="sm" :variant="selectedStatus === 'OPEN' ? 'default' : 'outline'"
                            @click="changeStatus('OPEN')">
                            Pending
                        </Button>

                        <Button size="sm" :variant="selectedStatus === 'CLOSED' ? 'default' : 'outline'"
                            @click="changeStatus('CLOSED')">
                            Completed
                        </Button>
                    </div>
                </div>

                <!-- Tasks Content -->
                <div v-if="isTasksPending" class="rounded-xl border p-8 text-center">
                    <p class="text-sm text-muted-foreground">Loading tasks...</p>
                </div>

                <div v-else-if="isTasksError"
                    class="flex flex-col items-center gap-3 rounded-xl border p-8 text-center">
                    <p class="text-sm text-destructive">
                        {{ tasksError instanceof Error ? tasksError.message : 'Unable to load tasks.' }}
                    </p>

                    <Button variant="outline" size="sm" @click="refetchTasks()">
                        Try again
                    </Button>
                </div>

                <!-- Empty State -->
                <div v-else-if="!tasks.length"
                    class="flex min-h-72 flex-col items-center justify-center gap-3 rounded-xl border border-dashed bg-card/50 p-8 text-center">
                    <div class="flex size-14 items-center justify-center rounded-2xl bg-primary/10">
                        <ListTodo class="size-7 text-primary" />
                    </div>

                    <h3 class="font-medium">
                        {{ selectedStatus ? 'No matching tasks' : 'No tasks yet' }}
                    </h3>

                    <p class="max-w-sm text-sm text-muted-foreground">
                        {{
                            selectedStatus
                                ? 'There are no tasks matching this filter.'
                                : 'Start organizing your work by adding your first task.'
                        }}
                    </p>
                </div>

                <!-- Tasks Table -->
                <div v-else class="overflow-hidden rounded-xl border border-border bg-card shadow-sm">
                    <table class="w-full text-sm">
                        <thead class="bg-muted/40">
                            <tr class="border-b border-border text-left">
                                <th
                                    class="px-4 py-4 text-center text-xs font-semibold tracking-wide text-muted-foreground">
                                    Completed</th>
                                <th class="px-4 py-4 text-xs font-semibold tracking-wide text-muted-foreground">Title
                                </th>
                                <th
                                    class="px-4 py-4 text-center text-xs font-semibold tracking-wide text-muted-foreground">
                                    Priority</th>
                                <th
                                    class="px-4 py-4 text-center text-xs font-semibold tracking-wide text-muted-foreground">
                                    Due Date</th>
                                <th
                                    class="px-4 py-4 text-center text-xs font-semibold tracking-wide text-muted-foreground">
                                    Actions</th>
                            </tr>
                        </thead>

                        <tbody class="divide-y divide-border">
                            <tr v-for="task in tasks" :key="task.id"
                                class="cursor-default transition-colors duration-150 hover:bg-muted">
                                <td class="px-3 py-3 text-center">
                                    <input type="checkbox" :checked="task.status === 'CLOSED'"
                                        :disabled="isUpdatingTask"
                                        class="size-4 cursor-pointer rounded accent-primary disabled:cursor-not-allowed"
                                        :aria-label="`Mark ${task.title} as ${task.status === 'OPEN' ? 'completed' : 'pending'}`"
                                        @change="handleToggleTaskStatus(task)" />
                                </td>

                                <td class="px-3 py-3 font-medium">
                                    {{ task.title }}
                                </td>

                                <td class="px-3 py-3 text-center">
                                    <span class="rounded-full px-2.5 py-1 text-xs font-medium" :class="{
                                        'bg-priority-low/15 text-priority-low': task.priority === 'LOW',
                                        'bg-priority-medium/15 text-priority-medium': task.priority === 'MEDIUM',
                                        'bg-priority-high/15 text-priority-high': task.priority === 'HIGH',
                                    }">
                                        {{ task.priority.charAt(0) + task.priority.slice(1).toLowerCase() }}
                                    </span>
                                </td>

                                <td class="px-3 py-3 text-muted-foreground text-center">
                                    {{ formatDate(task.dueDate) }}
                                </td>

                                <td class="px-3 py-3 text-muted-foreground space-x-1 text-center">
                                    <Button variant="ghost" size="icon"
                                        class="size-8 text-indigo-600 hover:bg-indigo-50 hover:text-indigo-700 dark:text-indigo-400 dark:hover:bg-indigo-950"
                                        aria-label="Edit task" title="Edit task" @click="handleEditTask(task)">
                                        <Pencil class="size-4" />
                                    </Button>

                                    <Button variant="ghost" size="icon"
                                        class="size-8 text-red-600 hover:bg-red-50 hover:text-red-700 dark:text-red-400 dark:hover:bg-red-950"
                                        aria-label="Delete task" title="Delete task" @click="handleDeleteTask(task)">
                                        <Trash2 class="size-4" />
                                    </Button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div
                        class="flex flex-wrap items-center justify-between gap-4 border-t border-border bg-card px-4 py-4">
                        <div class="flex flex-wrap items-center gap-3">
                            <p class="text-sm text-muted-foreground">
                                Showing
                                <span class="font-medium text-foreground">
                                    {{ totalTasks === 0 ? 0 : currentPage * pageSize + 1 }}–{{ Math.min((currentPage +
                                        1) * pageSize, totalTasks) }}
                                </span>
                                of
                                <span class="font-medium text-foreground">{{ totalTasks }}</span>
                                tasks
                            </p>
                        </div>

                        <div class="flex items-center gap-2">
                            <Button variant="outline" size="sm" :disabled="currentPage === 0"
                                @click="changePage(currentPage - 1)">
                                Previous
                            </Button>

                            <span class="min-w-20 text-center text-sm text-muted-foreground">
                                Page {{ currentPage + 1 }} of {{ Math.max(totalPages, 1) }}
                            </span>

                            <Button variant="outline" size="sm" :disabled="currentPage >= totalPages - 1"
                                @click="changePage(currentPage + 1)">
                                Next
                            </Button>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Edit Task List -->
            <TaskListFormSheet v-model:open="isEditSheetOpen" mode="edit" :initial-values="initialValues"
                :server-error="updateServerError" :loading="isUpdating" @submit="handleUpdate" />

            <!-- Add Task -->
            <TaskFormSheet v-model:open="isTaskSheetOpen" :mode="taskFormMode" :initial-values="taskInitialValues"
                :loading="isCreatingTask || isUpdatingTask" :server-error="taskFormServerError"
                @submit="handleTaskSubmit" />

            <!-- Delete Task List Dialog -->
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

            <!-- Delete Task Dialog -->
            <AlertDialog v-model:open="isDeleteTaskDialogOpen">
                <AlertDialogContent>
                    <AlertDialogHeader>
                        <AlertDialogTitle>
                            Delete Task?
                        </AlertDialogTitle>

                        <AlertDialogDescription>
                            Are you sure you want to delete
                            <strong>{{ taskToDelete?.title }}</strong>?
                            This action cannot be undone.
                        </AlertDialogDescription>
                    </AlertDialogHeader>

                    <p v-if="deleteTaskServerError" class="text-sm text-destructive" role="alert">
                        {{ deleteTaskServerError }}
                    </p>

                    <AlertDialogFooter>
                        <AlertDialogCancel :disabled="isDeletingTask">
                            Cancel
                        </AlertDialogCancel>

                        <Button variant="destructive" :disabled="isDeletingTask" @click="confirmDeleteTask">
                            {{ isDeletingTask ? 'Deleting...' : 'Delete Task' }}
                        </Button>
                    </AlertDialogFooter>
                </AlertDialogContent>
            </AlertDialog>
        </section>
    </FadeTransition>
</template>
