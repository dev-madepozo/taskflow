import { computed, toValue, type MaybeRefOrGetter } from "vue"
import { useQuery } from "@tanstack/vue-query"

import { taskService } from "../services/task.service"
import type { TaskStatus } from "../types/task.types"

interface UseTasksParams {
    page: MaybeRefOrGetter<number>
    size: MaybeRefOrGetter<number>
    status: MaybeRefOrGetter<TaskStatus | undefined>
}

export function useTasks(taskListId: MaybeRefOrGetter<string>, params: UseTasksParams) {
    return useQuery({
        queryKey: computed(() => [
            'task-lists',
            toValue(taskListId),
            'tasks',
            toValue(params.page),
            toValue(params.size),
            toValue(params.status)
        ]),
        queryFn: () => taskService.getAll(
            toValue(taskListId), {
            page: toValue(params.page),
            size: toValue(params.size),
            status: toValue(params.status)
        }
        ),
        enabled: computed(() => Boolean(toValue(taskListId)))
    })
}
