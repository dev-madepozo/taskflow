<script setup lang="ts">

import AppSidebarSkeleton from './AppSidebarSkeleton.vue'
import AppContentSkeleton from './AppContentSkeleton.vue'

</script>

<template>
    <div class="flex h-dvh overflow-hidden bg-background">
        <AppSidebarSkeleton />
        <AppContentSkeleton />
    </div>
</template>
