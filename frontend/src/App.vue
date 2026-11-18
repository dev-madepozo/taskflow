<script setup lang="ts">

import { RouterView } from 'vue-router'
import { useColorMode } from '@vueuse/core'

import { Toaster } from '@/components/ui/sonner'

import 'vue-sonner/style.css'

const mode = useColorMode({
  attribute: 'class',
  storageKey: 'taskflow-theme',
  initialValue: 'auto',
})

</script>

<template>
  <RouterView />
  <Toaster position="bottom-right" :theme="mode === 'dark' ? 'dark' : 'light'" />
</template>
