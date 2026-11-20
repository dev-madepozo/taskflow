import { useMutation, useQueryClient } from "@tanstack/vue-query";
import type { UpdateTaskRequest } from "../types/task.types";
import { taskService } from "../services/task.service";

interface UpdateTaskVariables {
    taskListId: string
    taskId: string
    payload: UpdateTaskRequest
}

export function useUpdateTask() {
    const queryClient = useQueryClient()

    return useMutation({
        mutationFn: ({ taskListId, taskId, payload }: UpdateTaskVariables) =>
            taskService.update(taskListId, taskId, payload),
        onSuccess: async (_, { taskListId }) => {
            await Promise.all([
                queryClient.invalidateQueries({
                    queryKey: ['task-lists', taskListId, 'tasks']
                }),
                queryClient.invalidateQueries({
                    queryKey: ['task-lists', taskListId]
                }),
                queryClient.invalidateQueries({
                    queryKey: ['task-lists']
                }),
            ])
        }
    })
}
