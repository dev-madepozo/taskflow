<script lang="ts" setup>
import type { ToasterProps } from 'vue-sonner'
import {
  CircleCheckIcon,
  InfoIcon,
  Loader2Icon,
  OctagonXIcon,
  TriangleAlertIcon,
  XIcon,
} from '@lucide/vue'
import { cn } from '@/lib/utils'
import { Toaster as Sonner } from 'vue-sonner'

const props = defineProps<ToasterProps>()
</script>

<template>
  <Sonner :class="cn('toaster group', props.class)" :style="{
    '--normal-bg': 'var(--popover)',
    '--normal-text': 'var(--popover-foreground)',
    '--normal-border': 'var(--border)',
    '--border-radius': 'var(--radius)',
  }" v-bind="props">
    <template #success-icon>
      <CircleCheckIcon class="size-4 text-green-600 dark:text-green-400" />
    </template>

    <template #info-icon>
      <InfoIcon class="size-4 text-blue-600 dark:text-blue-400" />
    </template>

    <template #warning-icon>
      <TriangleAlertIcon class="size-4 text-amber-600 dark:text-amber-400" />
    </template>

    <template #error-icon>
      <OctagonXIcon class="size-4 text-destructive" />
    </template>

    <template #loading-icon>
      <Loader2Icon class="size-4 animate-spin text-muted-foreground" />
    </template>

    <template #close-icon>
      <XIcon class="size-4 text-muted-foreground" />
    </template>
  </Sonner>
</template>
