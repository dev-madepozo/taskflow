import { useMutation, useQueryClient } from "@tanstack/vue-query";

import type { CreateTaskRequest } from "../types/task.types";
import { taskService } from "../services/task.service";

interface CreateTaskVariables {
    taskListId: string,
    payload: CreateTaskRequest
}

export function useCreateTask() {
    const queryClient = useQueryClient()

    return useMutation({
        mutationFn: ({ taskListId, payload }: CreateTaskVariables) => taskService.create(taskListId, payload),
        onSuccess: async (_, { taskListId }) => {
            await Promise.all([
                queryClient.invalidateQueries({
                    queryKey: ['task-lists', taskListId, 'tasks'],
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
