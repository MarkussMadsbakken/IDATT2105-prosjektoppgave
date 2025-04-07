<script setup lang="ts">
import { useGetListingImages } from '@/actions/images';
import { computed } from 'vue';
import ImageNotFound from './ImageNotFound.vue';

const props = withDefaults(defineProps<{
    listingId: string,
    size?: number;

}>(), {
    size: 80
});


const { data: images, isPending } = useGetListingImages(props.listingId);

const imageUrls = computed(() => {
    if (!images.value) {
        return [];
    }
    return images.value.map((image) => {
        return `data:${image.fileType};base64,${image.base64Image}`;
    });
});

</script>

<template>
    <template v-if="isPending">
        <div class="loading-image-skeleton"></div>
    </template>
    <template v-else-if="images && images?.length > 0">
        <img :src="imageUrls[0]" alt="listing" />
    </template>
    <template v-else>
        <ImageNotFound :size="props.size" strokewidth="0.5px" />
    </template>
</template>


<style scoped>
.loading-image-skeleton {
    width: 100%;
    height: 100%;
    background-color: var(--color-skeleton);
    animation: pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}
</style>