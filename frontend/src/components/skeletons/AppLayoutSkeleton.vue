<script setup lang="ts">

import AppSidebarSkeleton from './AppSidebarSkeleton.vue'
import AppContentSkeleton from './AppContentSkeleton.vue'

</script>

<template>
    <div class="flex h-dvh overflow-hidden bg-muted/30">
        <AppSidebarSkeleton />
        <AppContentSkeleton />
    </div>
</template>
