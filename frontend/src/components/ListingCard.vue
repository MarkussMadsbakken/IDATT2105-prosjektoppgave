<script setup lang="ts">
import type { Listing } from '@/types';
import ImageNotFound from './ImageNotFound.vue';

const { listing } = defineProps<{
    listing: Listing;
}>();

const headerImage = Array.isArray(listing.image) ? listing.image[0] : listing.image;

</script>

<template>
    <RouterLink :to="`/listing/${listing.id}`" class="link">
        <div class="outer-wrapper">
            <div class="image-wrapper">
                <div v-if="listing.image">
                    <img :src="headerImage">
                </div>
                <div v-else>
                    <ImageNotFound :size="80" strokewidth="0.5px" />
                </div>
                <div class="price">
                    {{ listing.price }} kr
                </div>
            </div>
            <div class="content-wrapper">
                <div class="title">
                    {{ listing.title }}
                </div>
                <div class="description">
                    {{ listing.description }}
                </div>
            </div>
        </div>
    </RouterLink>
</template>

<style scoped>
.price {
    position: absolute;
    bottom: 0;
    left: 0;
    padding-left: 1rem;
    padding-bottom: 1rem;

}

.link {
    text-decoration-color: white;
    color: black;
    text-decoration-line: none;
}

.outer-wrapper {
    display: flex;
    flex-direction: column;
    border-radius: 20px;
    line-height: 0;
    width: 25rem;
    height: 12rem;
    overflow: hidden;
    box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1);
    transition-property: box-shadow;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
    transition-duration: 150ms;
}

.outer-wrapper:hover {
    box-shadow: 0 10px 15px -3px rgb(0 0 0 / 0.1), 0 4px 6px -4px rgb(0 0 0 / 0.1);
    cursor: pointer;
}

.image-wrapper {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 10rem;
    background-color: oklch(0.97 0 0);
}

.content-wrapper {
    padding: 1rem;
    grid-template-rows: repeat(1, minmax(0, 1fr));
    flex-direction: column;
    display: flex;
    gap: 0.5rem;
    text-align: center;
}

.title {
    line-height: 1;
    font-size: large;
    font-weight: 600;
}

.description {
    line-height: 1.6;
    font-weight: 400;
    font-size: smaller;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
}
</style>