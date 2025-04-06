<script setup lang="ts">
import { useGetListingImages } from '@/actions/images';
import PhotoGallery from './PhotoGallery.vue';
import { computed } from 'vue';
import ImageNotFound from './ImageNotFound.vue';

const props = defineProps<{
    listingId: string,
    size?: 'small' | 'medium' | 'large';
}>();


const { data: images } = useGetListingImages(props.listingId);

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
    <template v-if="images && images?.length > 0">
        <img :src="imageUrls[0]" alt="listing" />
    </template>
    <template v-else>
        <ImageNotFound :size="props.size === 'medium' ? 80 : 40" strokewidth="0.5px" />
    </template>
</template>