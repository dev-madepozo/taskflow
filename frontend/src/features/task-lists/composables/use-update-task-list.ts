import { useMutation, useQueryClient } from "@tanstack/vue-query";

import { taskListService } from "../services/task-list.service";
import type { UpdateTaskListRequest } from "../types/task-list.types";

export function useUpdateTaskList() {
    const queryClient = useQueryClient()

    return useMutation({
        mutationFn: ({ id, payload }: { id: string, payload: UpdateTaskListRequest }) =>
            taskListService.update(id, payload)
        ,
        onSuccess: (updatedTaskList) => {
            queryClient.setQueryData(['task-lists', updatedTaskList.id], updatedTaskList)
            queryClient.invalidateQueries({ queryKey: ['task-lists'] })
        }
    })
}
