import { useQuery } from "@tanstack/vue-query";
import { computed, toValue, type MaybeRefOrGetter } from "vue";
import { taskListService } from "../services/task-list.service";

export function useTaskList(id: MaybeRefOrGetter<string>) {
    return useQuery({
        queryKey: computed(() => ['task-lists', toValue(id)]),
        queryFn: () => taskListService.getById(toValue(id)),
        enabled: computed(() => Boolean(toValue(id))),
    })
}
