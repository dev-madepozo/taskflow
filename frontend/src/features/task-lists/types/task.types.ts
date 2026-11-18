export type TaskStatus = 'OPEN' | 'CLOSED'

export type TaskPriority = 'LOW' | 'MEDIUM' | 'HIGH'

export interface Task {
    id: string,
    title: string
    description: string
    dueDate: string | null
    status: TaskStatus
    priority: TaskPriority
}
