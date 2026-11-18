<script setup lang="ts">

import { ref } from 'vue'
import { Sun, Moon } from '@lucide/vue'

import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Textarea } from '@/components/ui/textarea'
import { Badge } from '@/components/ui/badge'
import { useTheme } from '@/composables/use-theme'


const { mode, toggleTheme } = useTheme()
const inputValue = ref('')
const description = ref('')

</script>

<template>
    <main class="min-h-screen bg-background text-foregraound">
        <div class="mx-auto max-w-6xl space-y-10 p-8">
            <!-- Header -->
            <header class="flex items-center justify-between">
                <div>
                    <h1 class="text-3xl font-bold">Design System</h1>
                    <p class="mt-2 text-muted-foreground">
                        TaskFlow visual components and design tokens
                    </p>
                </div>

                <Button variant="ghost" size="icon" aria-label="Toggle theme" @click="toggleTheme">
                    <Sun v-if="mode === 'dark'" class="h-5 w-5" />
                    <Moon v-else class="h-5 w-5" />
                </Button>
            </header>

            <!-- Typography -->

            <section class="space-y-4">
                <h2 class="text-xl font-semibold">Typography</h2>

                <div class="rounded-xl border bg-card p-6 space-y-4">
                    <h1 class="text-3xl font-bold">Heading 1</h1>
                    <h2 class="text-2xl font-semibold">Heading 2</h2>
                    <h3 class="text-xl font-medium">Heading 3</h3>
                    <p>Body text - The quick brown fox jumps over the lazy dog.</p>
                    <p class="text-sm text-muted-foreground">
                        Secondary text — Additional information and descriptions.
                    </p>
                    <p class="text-xs text-muted-foreground">
                        Small text — Metadata and captions.
                    </p>
                </div>
            </section>

            <!-- Buttons -->
            <section class="space-y-4">
                <h2 class="text-xl font-semibold">Buttons</h2>

                <div class="flex flex-wrap gap-3 rounded-xl border bg-card p-6">
                    <Button>Primary</Button>
                    <Button variant="secondary">Secondary</Button>
                    <Button variant="outline">Outline</Button>
                    <Button variant="ghost">Ghost</Button>
                    <Button variant="destructive">Destructive</Button>
                    <Button disabled>Disabled</Button>
                </div>
            </section>

            <!-- Inputs -->
            <section class="space-y-4">
                <h2 class="text-xl font-semibold">Form elements</h2>

                <div class="max-w-xl space-y-5 rounded-xl border bg-card p-6">
                    <div class="space-y-2">
                        <label class="text-sm font-medium">Task title</label>
                        <Input v-model="inputValue" placeholder="Enter a task title..." />
                    </div>

                    <div class="space-y-2">
                        <label class="text-sm font-medium">Description</label>
                        <Textarea v-model="description" placeholder="Describe your task..." />
                    </div>

                    <div class="space-y-2">
                        <label class="text-sm font-medium">Disabled input</label>
                        <Input disabled placeholder="This field is disabled" />
                    </div>
                </div>
            </section>

            <!-- Badges -->
            <section class="space-y-4">
                <h2 class="text-xl font-semibold">Task priorities</h2>

                <div class="flex flex-wrap items-center gap-4 rounded-xl border bg-card p-6">
                    <Badge class="bg-priority-low text-white hover:bg-priority-low">
                        Low
                    </Badge>

                    <Badge class="bg-priority-medium text-white hover:bg-priority-medium">
                        Medium
                    </Badge>

                    <Badge class="bg-priority-high text-white hover:bg-priority-high">
                        High
                    </Badge>
                </div>
            </section>

            <!-- Surfaces -->
            <section class="space-y-4">
                <h2 class="text-xl font-semibold">Surfaces</h2>

                <div class="grid gap-4 md:grid-cols-3">
                    <div class="rounded-xl border bg-card p-5">
                        <h3 class="font-semibold">Card</h3>
                        <p class="mt-2 text-sm text-muted-foreground">
                            Main content surface.
                        </p>
                    </div>

                    <div class="rounded-xl border bg-muted p-5">
                        <h3 class="font-semibold">Muted</h3>
                        <p class="mt-2 text-sm text-muted-foreground">
                            Secondary content surface.
                        </p>
                    </div>

                    <div class="rounded-xl border bg-accent p-5">
                        <h3 class="font-semibold">Accent</h3>
                        <p class="mt-2 text-sm text-muted-foreground">
                            Highlighted content surface.
                        </p>
                    </div>
                </div>
            </section>

        </div>
    </main>
</template>