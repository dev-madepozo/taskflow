<script setup lang="ts">
import { computed, watch } from 'vue'
import { toTypedSchema } from '@vee-validate/zod'
import { useForm } from 'vee-validate'
import { z } from 'zod'

import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Textarea } from '@/components/ui/textarea'
import {
    Sheet,
    SheetContent,
    SheetDescription,
    SheetHeader,
    SheetTitle,
} from '@/components/ui/sheet'

import type { CreateTaskListRequest } from '../types/task-list.types'
import SheetFooter from '@/components/ui/sheet/SheetFooter.vue'

interface Props {
    open: boolean
    mode?: 'create' | 'edit'
    initialValues?: CreateTaskListRequest
    loading?: boolean
    serverError?: string | null
}

const props = withDefaults(defineProps<Props>(), {
    mode: 'create',
    loading: false,
    serverError: null,
    initialValues: () => ({
        title: '',
        description: '',
    }),
})

const emit = defineEmits<{
    'update:open': [value: boolean]
    submit: [values: CreateTaskListRequest]
}>()

const schema = toTypedSchema(
    z.object({
        title: z.string().trim().min(1, 'Title is required').max(150, 'Title cannot exceed 150 characters.'),
        description: z.string().trim().max(1000, 'Description cannot exceed 1000 characters.'),
    }),
)

const { defineField, errors, handleSubmit, resetForm } = useForm({
    validationSchema: schema,
    initialValues: {
        title: '',
        description: '',
    },
})

const [title, titleAttrs] = defineField('title', {
    validateOnModelUpdate: false,
    validateOnBlur: false,
})

const [description, descriptionAttrs] = defineField('description', {
    validateOnModelUpdate: false,
    validateOnBlur: false,
})

watch(
    () => [props.open, props.mode, props.initialValues] as const,
    ([open]) => {
        if (!open) return

        resetForm({
            values: {
                title: props.initialValues.title,
                description: props.initialValues.description,
            },
        })
    },
    { immediate: true },
)

const onSubmit = handleSubmit((values) => {
    emit('submit', values)
})

const isFormValid = computed(() => {
    const normalizedTitle = title.value?.trim() ?? ''
    const normalizedDescription = description.value?.trim() ?? ''

    return (
        normalizedTitle.length >= 1 &&
        normalizedTitle.length <= 80 &&
        normalizedDescription.length <= 500
    )
})

const isEditMode = computed(() => props.mode === 'edit')

const formTitle = computed(() =>
    isEditMode.value ? 'Edit Task List' : 'Create Task List',
)

const formDescription = computed(() =>
    isEditMode.value
        ? 'Update your task list information.'
        : 'Organize your tasks by creating a new list.',
)

const submitLabel = computed(() => {
    if (props.loading) {
        return isEditMode.value ? 'Saving...' : 'Creating...'
    }

    return isEditMode.value ? 'Save Changes' : 'Create Task List'
})
</script>

<template>
    <Sheet :open="open" @update:open="emit('update:open', $event)">
        <SheetContent side="right" class="flex w-full flex-col gap-0 border-l border-border bg-card p-0 sm:max-w-md">
            <SheetHeader class="border-b border-border px-6 py-6">
                <SheetTitle class="text-xl font-bold tracking-tight">{{ formTitle }}</SheetTitle>
                <SheetDescription>
                    {{ formDescription }}
                </SheetDescription>
            </SheetHeader>

            <form class="flex min-h-0 flex-1 flex-col" @submit="onSubmit">
                <div class="flex-1 space-y-6 overflow-y-auto px-6 py-6">
                    <div class="flex flex-col gap-2">
                        <label for="title" class="text-sm font-semibold text-foreground">
                            Title <span class="text-destructive">*</span>
                        </label>

                        <Input id="title" v-model="title" v-bind="titleAttrs" maxlength="80"
                            placeholder="e.g. Work projects" />

                        <p v-if="errors.title" class="text-sm text-destructive">
                            {{ errors.title }}
                        </p>
                    </div>

                    <div class="flex flex-col gap-2">
                        <label for="description" class="text-sm font-semibold text-foreground">
                            Description
                        </label>

                        <Textarea id="description" v-model="description" v-bind="descriptionAttrs" maxlength="500"
                            placeholder="What is this list about?" class="min-h-28" />

                        <p v-if="errors.description" class="text-sm text-destructive">
                            {{ errors.description }}
                        </p>
                    </div>
                </div>

                <SheetFooter class="border-t border-border bg-muted/20 px-6 py-5 sm:flex-row sm:justify-end">
                    <Button type="button" variant="outline" :disabled="props.loading"
                        @click="emit('update:open', false)">
                        Cancel
                    </Button>

                    <Button type="submit" :disabled="!isFormValid || props.loading"
                        class="rounded-lg font-semibold shadow-sm">
                        {{ submitLabel }}
                    </Button>

                    <div v-if="props.serverError" role="alert"
                        class="rounded-lg border border-destructive/30 bg-destructive/10 p-3 text-sm text-destructive">
                        {{ props.serverError }}
                    </div>
                </SheetFooter>
            </form>
        </SheetContent>
    </Sheet>
</template>
