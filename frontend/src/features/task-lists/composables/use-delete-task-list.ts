import { useMutation, useQueryClient } from "@tanstack/vue-query";
import { taskListService } from "../services/task-list.service";

export function useDeleteTaskList() {
    const queryClient = useQueryClient()

    return useMutation({
        mutationFn: (id: string) => taskListService.delete(id),
        onSuccess: async (_, id) => {
            queryClient.removeQueries({ queryKey: ['task-lists', id] })

            await queryClient.invalidateQueries({
                queryKey: ['task-lists']
            })
        }
    })
}
