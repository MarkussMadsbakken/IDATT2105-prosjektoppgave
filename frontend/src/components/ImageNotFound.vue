<script setup lang="ts">
import { Image, type LucideProps } from 'lucide-vue-next';

const props = defineProps<{
    size?: number;
    strokewidth?: string | number;
}>();

</script>

<template>
    <div>
        <Image :size="props.size" :stroke-width="props.strokewidth" />
    </div>
</template>

<style scoped>
.div {
    display: flex;
    justify-content: center;
}
</style>