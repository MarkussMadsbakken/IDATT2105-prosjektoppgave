<script setup lang="ts">
import { UserRound } from 'lucide-vue-next';


const props = withDefaults(defineProps<{
    size?: number;
}>(), {
    size: 80
});

</script>

<template>
    <div class="image-wrapper-skeleton" :style="{ width: `${size}px`, height: `${size}px` }">
        <div class="no-image-skeleton">
            <UserRound :size="size * 0.7" stroke-width="1.5" />
        </div>
    </div>
</template>

<style scoped>
.image-wrapper-skeleton {
    width: 60px;
    height: 60px;
    border-radius: 50%;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
}

.no-image-skeleton {
    border: 0.5px solid black;
    border-radius: 50%;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: var(--color-skeleton);
    animation: pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}
</style>
