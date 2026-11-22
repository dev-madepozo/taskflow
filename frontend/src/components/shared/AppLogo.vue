<script setup lang="ts">
import { RouterLink } from 'vue-router'
import { CheckCheck } from '@lucide/vue'
</script>

<template>
    <RouterLink :to="{ name: 'task-lists' }"
        class="group inline-flex items-center gap-3 rounded-lg outline-none transition-opacity hover:opacity-90 focus-visible:ring-2 focus-visible:ring-ring">
        <span
            class="flex size-10 shrink-0 items-center justify-center rounded-xl bg-primary text-primary-foreground shadow-sm">
            <CheckCheck class="size-6" :stroke-width="2.2" />
        </span>

        <span class="text-xl font-bold tracking-tight text-foreground">
            TaskFlow
        </span>
    </RouterLink>
</template>
