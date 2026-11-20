import type { Task } from '@/features/tasks/types/task.types'

export interface TaskList {
    id: string
    title: string
    description: string
    count: number
    progress: number
    tasks: Task[]
}

export type TaskListSummary = Omit<TaskList, 'tasks'>

export interface CreateTaskListRequest {
    title: string
    description: string
}

export type UpdateTaskListRequest = CreateTaskListRequest
