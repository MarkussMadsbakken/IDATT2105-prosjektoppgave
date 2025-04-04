<script setup lang="ts">
import { UserRound } from 'lucide-vue-next';

const props = defineProps<{
    src?: string;
    size? : number;
}>();

const size = props.size ?? 60;

</script>

<template>
    <div class="image-wrapper" v-if="src" :style="{ width: `${size}px`, height: `${size}px` }">
        <img :src="src" alt="user" />
    </div>
  <div
      class="image-wrapper" v-else :style="{ width: `${size}px`, height: `${size}px` }">
        <div class="no-image">
            <UserRound :size="size * 0.7" stroke-width="1.5" />
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
    border-radius: 50%;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;

}

img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 50%;
}
</style>
