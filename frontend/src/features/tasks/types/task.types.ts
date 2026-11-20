export type TaskStatus = 'OPEN' | 'CLOSED'

export type TaskPriority = 'LOW' | 'MEDIUM' | 'HIGH'

export interface Task {
    id: string
    title: string
    description: string
    dueDate: string | null
    status: TaskStatus
    priority: TaskPriority
}

export interface TaskFormValues {
    title: string
    description: string
    dueDate: string
    priority: TaskPriority
}

export interface CreateTaskRequest {
    title: string
    description: string
    dueDate: string | null
    status: TaskStatus
    priority: TaskPriority
}

export type UpdateTaskRequest = CreateTaskRequest
