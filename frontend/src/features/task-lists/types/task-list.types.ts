import type { Task } from '@/features/task-lists/types/task.types'

export interface TaskList {
    id: string
    title: string
    description: string
    count: number
    progress: number
    tasks: Task[]
}

export interface CreateTaskListRequest {
    title: string
    description: string
}

export type UpdateTaskListRequest = CreateTaskListRequest
