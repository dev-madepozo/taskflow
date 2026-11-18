import { useMutation, useQueryClient } from '@tanstack/vue-query'
import { taskListService } from '../services/task-list.service'

export function useCreateTaskList() {
    const queryClient = useQueryClient()

    return useMutation({
        mutationFn: taskListService.create,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['task-lists'] })
        }
    })
}