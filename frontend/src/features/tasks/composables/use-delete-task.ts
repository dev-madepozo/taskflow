import { useMutation, useQueryClient } from "@tanstack/vue-query";
import { taskService } from "../services/task.service";

interface DeleteTaskVariables {
    taskListId: string
    taskId: string
}

export function useDeleteTask() {
    const queryClient = useQueryClient()

    return useMutation({
        mutationFn: ({ taskListId, taskId }: DeleteTaskVariables) => taskService.delete(taskListId, taskId),
        onSuccess: async (_, { taskListId }) => {
            await Promise.all([
                queryClient.invalidateQueries({
                    queryKey: ['task-lists', taskListId, 'tasks'],
                }),
                queryClient.invalidateQueries({
                    queryKey: ['task-lists', taskListId],
                }),
                queryClient.invalidateQueries({
                    queryKey: ['task-lists'],
                }),
            ])
        }
    })
}
