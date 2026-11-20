import { computed, toValue, type MaybeRefOrGetter } from "vue";
import { useQuery } from "@tanstack/vue-query";

import { taskService } from "../services/task.service";

export function useTasks(taskListId: MaybeRefOrGetter<string>) {
    return useQuery({
        queryKey: computed(() => [
            'task-lists',
            toValue(taskListId),
            'tasks'
        ]),
        queryFn: () => taskService.getAll(toValue(taskListId)),
        enabled: computed(() => Boolean(toValue(taskListId)))
    })
}
