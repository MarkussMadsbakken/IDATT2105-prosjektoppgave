<script setup lang="ts">
import { Check, CircleAlert, TriangleAlert } from 'lucide-vue-next';

const props = defineProps<{
    variant: "Caution" | "Warning" | "Info" | "Success";
}>();

const icons = {
    Caution: TriangleAlert,
    Warning: TriangleAlert,
    Info: CircleAlert,
    Success: Check,
};

</script>

<template>
    <div class="alert" :class="props.variant">
        <component :is="icons[props.variant]" class="icon" />
        <slot />
    </div>
</template>

<style scoped>
.alert {
    padding: 1rem;
    border-radius: 0.5rem;
    margin: 1rem 0;
    height: 100%;
    width: 100%;
    display: flex;
    gap: 1rem;
    align-items: center;
}

.Caution {
    background-color: oklch(0.637 0.237 25.331 / 0.5);
}

.Warning {
    background-color: oklch(0.879 0.169 91.605);
}

.Info {
    background-color: oklch(0.809 0.105 251.813);
}

.Success {
    background-color: oklch(0.0 0.5 0.0 / 0.5);
}
</style>