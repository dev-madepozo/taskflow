<script setup lang="ts">

import { computed, ref, watch } from 'vue'
import { toast } from 'vue-sonner'
import { useRoute, useRouter, RouterView } from 'vue-router'

import TaskListFormSheet from '@/features/task-lists/components/TaskListFormSheet.vue'
import { Button } from '@/components/ui/button'


import AppSidebar from '@/components/shared/AppSidebar.vue'
import AppLayoutSkeleton from '@/components/skeletons/AppLayoutSkeleton.vue'
import AppContentSkeleton from '@/components/skeletons/AppContentSkeleton.vue'

import { useCreateTaskList } from '@/features/task-lists/composables/use-create-task-list'
import { useTaskLists } from '@/features/task-lists/composables/use-task-lists'
import type { CreateTaskListRequest } from '@/features/task-lists/types/task-list.types'


const route = useRoute()
const router = useRouter()

const isDesignSystem = computed(() => route.name === 'design-system')
const isCreateSheetOpen = ref(false)

const {
    data: taskLists,
    isPending,
    isError,
    refetch
} = useTaskLists(computed(() => !isDesignSystem.value))

const {
    mutateAsync,
    isPending: isCreating,
    error,
    reset,
} = useCreateTaskList()

const hasTaskLists = computed(() => (taskLists.value?.length ?? 0) > 0)
const shouldRedirectHome = computed(() =>
    !isDesignSystem.value &&
    !isPending.value &&
    !isError.value &&
    route.name === 'task-lists' &&
    hasTaskLists.value
)

watch(shouldRedirectHome, (shouldRedirect) => {
    if (!shouldRedirect || !taskLists.value?.length) return

    router.replace({
        name: 'task-list-detail',
        params: { id: taskLists.value[0]?.id },
    })
}, { immediate: true })

async function handleCreateTaskList(payload: CreateTaskListRequest) {
    reset()

    try {
        const createdTaskList = await mutateAsync(payload)
        toast.success('Task List created successfully!')
        isCreateSheetOpen.value = false

        await router.push({
            name: 'task-list-detail',
            params: { id: createdTaskList.id },
        })
    } catch {
        console.error('Error creating task list.')
    }
}

</script>

<template>
    <!-- Initial loading -->
    <AppLayoutSkeleton v-if="!isDesignSystem && isPending" />

    <!-- Application -->
    <div v-else class="flex h-dvh overflow-hidden bg-muted/30">
        <AppSidebar :task-lists="taskLists ?? []" @create="isCreateSheetOpen = true" />

        <main class="h-full min-w-0 flex-1 overflow-y-auto bg-background">
            <!-- Redirecting to first Task List -->
            <AppContentSkeleton v-if="shouldRedirectHome" />

            <!-- Error loading Task Lists -->
            <section v-else-if="!isDesignSystem && isError"
                class="flex min-h-full flex-col items-center justify-center gap-4 p-6 text-center">
                <p class="text-sm text-muted-foreground">
                    Unable to load your task lists.
                </p>

                <Button variant="outline" @click="refetch()">
                    Try again
                </Button>
            </section>

            <!-- Child routes -->
            <RouterView v-else v-slot="{ Component }">
                <component :is="Component" @create="isCreateSheetOpen = true" />
            </RouterView>
        </main>

        <!-- Global Task List Form -->
        <TaskListFormSheet v-model:open="isCreateSheetOpen" :loading="isCreating" :mode="'create'"
            :server-error="error instanceof Error ? error.message : null" @submit="handleCreateTaskList" />
    </div>
</template>
