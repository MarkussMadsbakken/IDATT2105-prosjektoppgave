<script setup lang="ts">
import { ref } from 'vue';
import { ArrowRight, ArrowLeft } from 'lucide-vue-next';
import ImageNotFound from "@/components/ImageNotFound.vue";

const props = defineProps<{
  images: string[];
}>();

const currentIndex = ref(0);

const next = () => {
  currentIndex.value = (currentIndex.value + 1) % props.images.length;
};

const prev = () => {
  currentIndex.value = (currentIndex.value - 1 + props.images.length) % props.images.length;
};

</script>

<template>

  <div class="gallery">
    <ArrowLeft class="arrow" :size="50" @click="prev" v-if="images.length > 1" />
    <div class="imageWrapper">
      <img v-if="props.images.length > 0" class="mainImage" :src="props.images[currentIndex]" alt="image" />
      <ImageNotFound v-else :size="100" />
    </div>
    <ArrowRight class="arrow" :size="50" @click="next" v-if="images.length > 1" />
  </div>

</template>

<style scoped>
.arrow {
  cursor: pointer;
}

.gallery {
  user-select: none;
  display: flex;
  flex-direction: row;
  align-items: center;
}

.imageWrapper {
  aspect-ratio: 16 / 9;
  margin: auto;
  width: 700px;
  max-width: 100%;
  background-color: #eee;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
}

.mainImage {
  width: 100%;
  height: 100%;
  object-fit: contain;
  border-radius: 10px;
}
</style>
