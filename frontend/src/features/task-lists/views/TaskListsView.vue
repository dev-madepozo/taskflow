<script setup lang="ts">

import { ListTodo } from '@lucide/vue'

import { Button } from '@/components/ui/button'

const emit = defineEmits<{
    create: []
}>()

</script>

<template>
    <section class="flex min-h-full w-full items-center justify-center p-6">
        <div class="flex w-full max-w-md flex-col items-center text-center">
            <div class="mb-6 flex size-16 items-center justify-center rounded-2xl bg-primary/10 text-primary">
                <ListTodo class="size-8" />
            </div>

            <h1 class="text-2xl font-semibold tracking-tight">
                No Task Lists Yet
            </h1>

            <p class="mt-2 text-sm leading-relaxed text-muted-foreground">
                Stay organized and get things done. Create your first task list
                to start managing your tasks.
            </p>

            <Button class="mt-6" @click="emit('create')">
                Create Task List
            </Button>
        </div>
    </section>
</template>
