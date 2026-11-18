import { toValue, type MaybeRefOrGetter } from 'vue'
import { useQuery } from '@tanstack/vue-query'
import { taskListService } from '../services/task-list.service'

export function useTaskLists(enabled: MaybeRefOrGetter<boolean> = true) {
    return useQuery({
        queryKey: ['task-lists'],
        queryFn: taskListService.getAll,
        enabled: () => toValue(enabled)
    })
}
