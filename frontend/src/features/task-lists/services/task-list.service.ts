import api from "@/lib/axios";
import type { CreateTaskListRequest, UpdateTaskListRequest, TaskList } from "../types/task-list.types"

export const taskListService = {
    async getAll(): Promise<TaskList[]> {
        const { data } = await api.get<TaskList[]>('/task-lists')
        return data
    },
    async getById(id: string): Promise<TaskList> {
        const { data } = await api.get<TaskList>(`/task-lists/${id}`)
        return data
    },
    async create(payload: CreateTaskListRequest): Promise<TaskList> {
        const { data } = await api.post<TaskList>('/task-lists', payload)
        return data
    },
    async update(id: string, payload: UpdateTaskListRequest): Promise<TaskList> {
        const { data } = await api.put<TaskList>(`/task-lists/${id}`, payload)
        return data
    },
    async delete(id: string): Promise<void> {
        await api.delete(`/task-lists/${id}`)
    }
}
