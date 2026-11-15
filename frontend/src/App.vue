<script setup lang="ts">
import ThemeToggle from '@/components/shared/ThemeToggle.vue'
</script>

<template>
  <main class="min-h-screen bg-background text-foreground p-8">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-semibold">TaskFlow</h1>
        <p class="text-muted-foreground">
          Manage your tasks efficiently.
        </p>
      </div>

      <ThemeToggle />
    </div>

    <div class="mt-10 rounded-lg border bg-card p-6">
      <h2 class="text-lg font-medium">Theme preview</h2>
      <p class="mt-2 text-muted-foreground">
        Click the button to switch between light and dark mode.
      </p>
    </div>
  </main>
</template>