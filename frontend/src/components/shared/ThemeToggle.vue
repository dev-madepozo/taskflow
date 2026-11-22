<script setup lang="ts">
import { Moon, Sun } from '@lucide/vue'
import { Button } from '@/components/ui/button'
import { useTheme } from '@/composables/use-theme'

const { mode, toggleTheme } = useTheme();
</script>

<template>
    <Button variant="ghost" size="icon" aria-label="Toggle theme" @click="toggleTheme"
        class="size-9 rounded-lg text-muted-foreground hover:bg-accent hover:text-foreground">
        <Sun v-if="mode !== 'dark'" class="size-[18px]" />
        <Moon v-else class="size-[18px]" />
    </Button>
</template>
