<script setup lang="ts">

import { Skeleton } from '@/components/ui/skeleton'

</script>

<template>
    <main class="h-full min-w-0 flex-1 overflow-y-auto bg-muted/20">
        <div class="mx-auto w-full max-w-[1600px] space-y-8 p-6 lg:p-8">

            <!-- Task List Header -->
            <section class="space-y-4">
                <div class="flex items-start justify-between gap-4">
                    <div class="min-w-0 flex-1 space-y-3">
                        <Skeleton class="h-8 w-56 max-w-full" />
                        <Skeleton class="h-4 w-80 max-w-full" />
                    </div>

                    <div class="flex shrink-0 gap-2">
                        <Skeleton class="size-9 rounded-lg" />
                        <Skeleton class="size-9 rounded-lg" />
                    </div>
                </div>

                <!-- Summary -->
                <div class="flex flex-wrap items-center gap-5">
                    <Skeleton class="h-4 w-24" />
                    <Skeleton class="h-4 w-28" />
                    <Skeleton class="h-2 w-32 rounded-full" />
                </div>
            </section>

            <!-- Tasks Section -->
            <section class="space-y-5">
                <div class="flex items-center justify-between gap-4">
                    <Skeleton class="h-6 w-28" />
                    <Skeleton class="h-9 w-28 rounded-md" />
                </div>

                <!-- Search and Filters -->
                <div class="flex flex-wrap items-center gap-3">
                    <Skeleton class="h-10 min-w-40 flex-1 rounded-md" />
                    <Skeleton class="h-10 w-28 rounded-md" />
                    <Skeleton class="h-10 w-28 rounded-md" />
                </div>

                <!-- Tasks Table -->
                <div class="overflow-hidden rounded-xl border border-border bg-card shadow-sm">

                    <!-- Table Header -->
                    <div
                        class="grid grid-cols-[48px_minmax(0,1fr)_120px_140px_88px] items-center gap-4 border-b border-border bg-muted/40 px-4 py-4">
                        <Skeleton class="h-3 w-8" />
                        <Skeleton class="h-3 w-12" />
                        <Skeleton class="h-3 w-16" />
                        <Skeleton class="h-3 w-16" />
                        <Skeleton class="h-3 w-14" />
                    </div>

                    <!-- Table Rows -->
                    <div v-for="item in 5" :key="item"
                        class="grid grid-cols-[48px_minmax(0,1fr)_120px_140px_88px] items-center gap-4 border-b border-border px-4 py-5 last:border-b-0">
                        <Skeleton class="size-4 rounded" />

                        <Skeleton class="h-4" :class="item === 2 ? 'w-36' : 'w-48'" />

                        <Skeleton class="h-6 w-20 rounded-full" />

                        <Skeleton class="h-4 w-24" />

                        <div class="flex items-center gap-2">
                            <Skeleton class="size-7 rounded-md" />
                            <Skeleton class="size-7 rounded-md" />
                        </div>
                    </div>
                </div>
            </section>

        </div>
    </main>
</template>
