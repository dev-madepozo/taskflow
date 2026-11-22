<script setup lang="ts">
import { computed, watch } from 'vue'
import { toTypedSchema } from '@vee-validate/zod'
import { useForm } from 'vee-validate'
import { z } from 'zod'
import { CalendarDays } from '@lucide/vue'

import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Textarea } from '@/components/ui/textarea'
import {
    Sheet,
    SheetContent,
    SheetDescription,
    SheetFooter,
    SheetHeader,
    SheetTitle,
} from '@/components/ui/sheet'

import type { TaskFormValues } from '../types/task.types'

interface Props {
    open: boolean
    mode?: 'create' | 'edit'
    initialValues: TaskFormValues
    loading?: boolean
    serverError?: string | null
}

const props = withDefaults(defineProps<Props>(), {
    mode: 'create',
    loading: false,
    serverError: null,
})

const emit = defineEmits<{
    'update:open': [value: boolean]
    submit: [values: TaskFormValues]
}>()

const schema = toTypedSchema(
    z.object({
        title: z
            .string()
            .trim()
            .min(1, 'Title is required.')
            .max(150, 'Title cannot exceed 150 characters.'),
        description: z
            .string()
            .trim()
            .max(1000, 'Description cannot exceed 1000 characters.'),
        dueDate: z.string(),
        priority: z.enum(['LOW', 'MEDIUM', 'HIGH']),
    }),
)

const {
    handleSubmit,
    defineField,
    resetForm,
    meta,
    errors,
    isSubmitting,
} = useForm({
    validationSchema: schema,
    initialValues: props.initialValues,
})

const [title, titleAttrs] = defineField('title', {
    validateOnModelUpdate: false,
    validateOnBlur: false,
})
const [description, descriptionAttrs] = defineField('description')
const [dueDate, dueDateAttrs] = defineField('dueDate')
const [priority] = defineField('priority')

watch(
    () => props.open,
    (open) => {
        if (!open) return

        resetForm({
            values: {
                title: props.initialValues.title,
                description: props.initialValues.description,
                dueDate: props.initialValues.dueDate,
                priority: props.initialValues.priority,
            }
        })
    },
)

const isFormValid = computed(() => meta.value.valid)

const onSubmit = handleSubmit((values) => {
    emit('submit', values)
})

</script>

<template>
    <Sheet :open="open" @update:open="emit('update:open', $event)">
        <SheetContent class="flex w-full flex-col gap-0 border-l border-border bg-card p-0 sm:max-w-md">
            <SheetHeader class="border-b border-border px-6 py-6">
                <SheetTitle class="text-xl font-bold tracking-tight">
                    {{ props.mode === 'create' ? 'Create Task' : 'Edit Task' }}
                </SheetTitle>

                <SheetDescription>
                    {{
                        props.mode === 'create'
                            ? 'Add a new task to your task list.'
                            : 'Update the details of this task.'
                    }}
                </SheetDescription>
            </SheetHeader>

            <form class="flex min-h-0 flex-1 flex-col" @submit="onSubmit">
                <div class="flex-1 space-y-6 overflow-y-auto px-6 py-6">
                    <!-- Title -->
                    <div class="flex flex-col gap-2">
                        <label for="task-title" class="text-sm font-semibold text-foreground">
                            Title <span class="text-destructive">*</span>
                        </label>

                        <Input id="task-title" v-model="title" v-bind="titleAttrs"
                            placeholder="e.g. Prepare project proposal" :disabled="props.loading" />

                        <p v-if="errors.title" class="text-sm text-destructive">
                            {{ errors.title }}
                        </p>
                    </div>

                    <!-- Description -->
                    <div class="flex flex-col gap-2">
                        <label for="task-description" class="text-sm font-semibold text-foreground">
                            Description
                        </label>

                        <Textarea id="task-description" v-model="description" v-bind="descriptionAttrs"
                            placeholder="Add more details about this task..." class="min-h-24 resize-none"
                            :disabled="props.loading" />

                        <p v-if="errors.description" class="text-sm text-destructive">
                            {{ errors.description }}
                        </p>
                    </div>

                    <!-- Due Date -->
                    <div class="flex flex-col gap-2">
                        <label for="task-due-date" class="text-sm font-semibold text-foreground">
                            Due Date
                        </label>

                        <div class="relative">
                            <CalendarDays
                                class="absolute left-3 top-1/2 size-4 -translate-y-1/2 text-muted-foreground" />

                            <Input id="task-due-date" v-model="dueDate" v-bind="dueDateAttrs" type="date" class="pl-10"
                                :disabled="props.loading" />
                        </div>

                        <p v-if="errors.dueDate" class="text-sm text-destructive">
                            {{ errors.dueDate }}
                        </p>
                    </div>

                    <!-- Priority -->
                    <div class="flex flex-col gap-3">
                        <span class="text-sm font-semibold text-foreground">
                            Priority <span class="text-destructive">*</span>
                        </span>

                        <div class="grid grid-cols-3 gap-3">
                            <Button type="button" variant="outline"
                                class="border-green-600/30 bg-green-600/10 text-green-700 hover:border-green-600 hover:bg-green-600/20 hover:text-green-700 dark:text-green-400 dark:hover:text-green-400"
                                :class="{
                                    'border-2 border-green-600 bg-green-600/20 dark:border-green-400 dark:bg-green-400/20':
                                        priority === 'LOW',
                                }" :disabled="props.loading" :aria-pressed="priority === 'LOW'"
                                @click="priority = 'LOW'">
                                Low
                            </Button>

                            <Button type="button" variant="outline"
                                class="border-amber-500/30 bg-amber-500/10 text-amber-700 hover:border-amber-500 hover:bg-amber-500/20 hover:text-amber-700 dark:text-amber-400 dark:hover:text-amber-400"
                                :class="{
                                    'border-2 border-amber-500 bg-amber-500/20 dark:border-amber-400 dark:bg-amber-400/20':
                                        priority === 'MEDIUM',
                                }" :disabled="props.loading" :aria-pressed="priority === 'MEDIUM'"
                                @click="priority = 'MEDIUM'">
                                Medium
                            </Button>

                            <Button type="button" variant="outline"
                                class="border-red-600/30 bg-red-600/10 text-red-700 hover:border-red-600 hover:bg-red-600/20 hover:text-red-700 dark:text-red-400 dark:hover:text-red-400"
                                :class="{
                                    'border-2 border-red-600 bg-red-600/20 dark:border-red-400 dark:bg-red-400/20':
                                        priority === 'HIGH',
                                }" :disabled="props.loading" :aria-pressed="priority === 'HIGH'"
                                @click="priority = 'HIGH'">
                                High
                            </Button>
                        </div>

                        <p v-if="errors.priority" class="text-sm text-destructive">
                            {{ errors.priority }}
                        </p>
                    </div>

                    <!-- Server Error -->
                    <p v-if="serverError" class="text-sm text-destructive">
                        {{ serverError }}
                    </p>
                </div>

                <!-- Footer -->
                <SheetFooter class="border-t border-border bg-muted/20 px-6 py-5 sm:flex-row sm:justify-end">
                    <Button type="button" variant="outline" :disabled="props.loading"
                        @click="emit('update:open', false)">
                        Cancel
                    </Button>

                    <Button type="submit" :disabled="props.loading || isSubmitting || !isFormValid"
                        class="rounded-lg font-semibold shadow-sm">
                        {{
                            props.loading
                                ? 'Saving...'
                                : props.mode === 'create'
                                    ? 'Create Task'
                                    : 'Save Changes'
                        }}
                    </Button>
                </SheetFooter>
            </form>
        </SheetContent>
    </Sheet>
</template>
