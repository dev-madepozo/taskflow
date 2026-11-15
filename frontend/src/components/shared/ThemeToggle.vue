<script setup lang="ts">
    import { Moon, Sun } from '@lucide/vue'
    import { Button } from '@/components/ui/button'
    import { useTheme } from '@/composables/use-theme'

    const { mode, toggleTheme } = useTheme();
</script>

<template>
    <Button
        variant="ghost"
        size="icon"
        aria-labe="Toggle theme"
        @click="toggleTheme"
    >
        <Sun v-if="mode !== 'dark'" class="size-5" />
        <Moon v-else class="size-5" />
    </Button>
        
</template>