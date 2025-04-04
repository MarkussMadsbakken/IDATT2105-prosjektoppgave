<script setup lang="ts">
import { useGetListingImages } from '@/actions/images';
import PhotoGallery from './PhotoGallery.vue';
import { computed } from 'vue';

const props = defineProps<{
    listingId: string,
}>();


const { data: images } = useGetListingImages(props.listingId);

const imageUrls = computed(() => {
    if (!images.value) {
        return [];
    }
    return images.value.map((image) => {
        return `data:image/${image.fileType};base64,${image.base64Image}`;
    });
});

</script>

<template>
    <PhotoGallery :images="imageUrls" />
</template>