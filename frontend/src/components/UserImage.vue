<script setup lang="ts">
import { UserRound } from 'lucide-vue-next';

const { src } = defineProps<{
    src?: string;
}>();

console.log(src);
</script>

<template>
    <div class="image-wrapper" v-if="src">
        <img :src="src" alt="user" />
    </div>
    <div class="image-wrapper" v-else>
        <div class="no-image">
            <UserRound :size="40" strokeWidth="0.5px" />
        </div>
    </div>
</template>

<style scoped>
.image-wrapper {
    width: 60px;
    height: 60px;
    border-radius: 50%;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
}

.no-image {
    border: 0.5px solid black;
    height: min-content;
    line-height: 0;
    padding: 0.5rem;
    border-radius: 999px;
}

img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 50%;
}
</style>
