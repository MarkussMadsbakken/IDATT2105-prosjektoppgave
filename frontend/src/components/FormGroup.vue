<script setup lang="ts">
const props = defineProps<{
    name: string
    label: string
    isNotFilledIn: boolean
}>();
</script>

<template>
    <div class="form-group">
        <div>
            <label :for="props.name">{{ props.label }}</label>
            <template v-if="props.isNotFilledIn">
                <span class="error">{{ $t("thisFieldIsRequired") }}</span>
            </template>
        </div>
        <slot />
    </div>
</template>

<style scoped>
.error {
    color: oklch(0.637 0.237 25.331);
    margin-left: 1rem;
}

.form-group {
    display: flex;
    flex-direction: column;
    gap: 0.25rem;
    width: 100%;
}
</style>