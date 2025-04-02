<script setup lang="ts">
import type { Category } from '@/types';
import { CategoryIcons } from '@/util/categoryIcons';
import { useRouter } from 'vue-router';
const router = useRouter();


const props = withDefaults(defineProps<{
    icon: keyof typeof CategoryIcons;
    selected?: boolean;
}>(), {
    selected: false,
});

defineEmits<(e: "click") => void>();

const Icon = CategoryIcons[props.icon];

</script>

<template>
    <div class="category-card-outer-wrapper" :class="{ selected: props.selected }" @click="$emit('click')">
        <div class="icon-wrapper">
            <Icon :size="30" :stroke-width="1.5" />
        </div>
        <div class="text-wrapper">
            <slot />
        </div>
    </div>
</template>

<style scoped>
.selected {
    background-color: var(--color-primaryButton);
    color: white;
}

.text-wrapper {
    width: 100%;
    height: 100%;
    flex: 1;
    display: flex;
    justify-content: baseline;
    align-items: center;
}

.category-card-outer-wrapper {
    line-height: 0;
    display: flex;
    flex-direction: row;
    border: 1px solid black;
    border-radius: 5px;
    padding: 1rem;
    width: 15rem;
    height: 5rem;
    align-items: center;
    gap: 1rem;
    transition: box-shadow;
    transition-duration: 0.2s;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
}

.category-card-outer-wrapper:hover {
    box-shadow: 0 10px 15px -3px rgb(0 0 0 / 0.1), 0 4px 6px -4px rgb(0 0 0 / 0.1);
    cursor: pointer;
}
</style>
