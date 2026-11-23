import api from "@/lib/axios"

import type {
    CreateTaskRequest,
    Task,
    TaskPageResponse,
    TaskStatus,
    UpdateTaskRequest
} from '../types/task.types'

type Params = {
    page?: number
    size?: number
    status?: TaskStatus
}

export const taskService = {
    async getAll(taskListId: string, params: Params = {}): Promise<TaskPageResponse> {
        const { data } = await api.get<TaskPageResponse>(`/task-lists/${taskListId}/tasks`, { params })
        return data
    },
    async create(taskListId: string, payload: CreateTaskRequest): Promise<Task> {
        const { data } = await api.post<Task>(`/task-lists/${taskListId}/tasks`, payload)
        return data
    },
    async update(taskListId: string, taskId: string, payload: UpdateTaskRequest) {
        const { data } = await api.put<Task>(`/task-lists/${taskListId}/tasks/${taskId}`, payload)
        return data
    },
    async delete(taskListId: string, taskId: string) {
        await api.delete(`/task-lists/${taskListId}/tasks/${taskId}`)
    }
}
