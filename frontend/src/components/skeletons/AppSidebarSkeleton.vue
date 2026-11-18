<script setup lang="ts">

import { Skeleton } from '@/components/ui/skeleton'

</script>

<template>
    <aside class="flex h-full w-64 shrink-0 flex-col overflow-hidden border-r border-sidebar-border bg-sidebar">
        <!-- Header -->
        <header class="flex h-16 shrink-0 items-center gap-3 border-b border-sidebar-border px-6">
            <Skeleton class="size-8 rounded-md" />
            <Skeleton class="h-5 w-24" />
        </header>

        <!-- Navigation -->
        <section class="min-h-0 flex-1 space-y-4 overflow-hidden px-4 py-6">
            <Skeleton class="mb-5 ml-2 h-3 w-20" />

            <div v-for="item in 4" :key="item" class="flex h-9 items-center gap-3 rounded-md px-2">
                <Skeleton class="size-4 shrink-0 rounded" />
                <Skeleton class="h-3" :class="item === 2 ? 'w-24' : 'w-32'" />
            </div>
        </section>

        <!-- Footer -->
        <footer class="shrink-0 space-y-4 border-t border-sidebar-border px-4 py-4">
            <div class="flex justify-center pb-2">
                <Skeleton class="size-9 rounded-full" />
            </div>

            <Skeleton class="mx-auto h-3 w-40" />
        </footer>
    </aside>
</template>
