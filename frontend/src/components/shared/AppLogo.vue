<script setup lang="ts">

import { RouterLink } from 'vue-router'
import { CheckCheck } from '@lucide/vue'

</script>

<template>
    <RouterLink :to="{ name: 'task-lists' }"
        class="inline-flex items-center gap-3 rounded-md outline-none focus-visible:ring-2 focus-visible:ring-ring">
        <span class="flex size-9 items-center justify-center rounded-xl bg-primary text-primary-foreground">
            <CheckCheck class="size-5" />
        </span>

        <span class="text-lg font-semibold tracking-tight">
            TaskFlow
        </span>
    </RouterLink>
</template>
