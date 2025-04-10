<script setup lang="ts">
import type { Listing } from '@/types';
import ImageNotFound from './ImageNotFound.vue';
import { useRouter } from 'vue-router';
import ListingHeaderImage from './ListingHeaderImage.vue';

const props = withDefaults(defineProps<{
    size?: "small" | "medium";
    listing: Listing;
}>(), {
    size: "medium",
});

const router = useRouter();
const handleClick = (e: any) => {
    e.stopPropagation();
    router.push(`/listing/${props.listing.uuid}`);
}
</script>

<template>
    <div @click="handleClick" class="link">
        <div class="outer-wrapper" :class="props.size">
            <div class="image-wrapper" :class="props.size">
                <ListingHeaderImage :listing-id="props.listing.uuid" class="image" :class="props.size"
                    :size="props.size == 'small' ? 40 : 80" />
                <div class="price" :class="props.size">
                    {{ props.listing.price }} kr
                </div>
            </div>
            <div class="content-wrapper" :class="props.size">
                <div class="title" :class="props.size">
                    {{ props.listing.name }}
                </div>
                <div class="description" :class="props.size">
                    {{ props.listing.description }}
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.price.medium {
    position: absolute;
    bottom: 0;
    left: 0;
    padding-left: 1rem;
    padding-bottom: 1rem;
    background-color: #ffffff;
    padding: 0.5rem 0.5rem;
    border-radius: 0.25rem;
    font-weight: 600;
    display: inline-block;
    color: #333;
    margin-left: 0.5rem;
    margin-bottom: 0.5rem;
    box-shadow: 0 1px 3px rgba(0, 4, 10, 0.15);
    border: 1px solid black;
}

.price.small {
    font-size: small;
    font-weight: 400;
    line-height: 1;
}

.link {
    text-decoration-color: white;
    color: black;
    text-decoration-line: none;
}

.outer-wrapper {
    background-color: #ffffff;
    display: flex;
    line-height: 0;
    overflow: hidden;
    transition-property: box-shadow;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
    transition-duration: 150ms;
    cursor: pointer;
}

.outer-wrapper.small {
    border-radius: 10px;
    padding: 1rem;
    box-shadow: 0 1px 3px 0 rgb(0 0 0 / 0.1), 0 1px 2px -1px rgb(0 0 0 / 0.1);
    width: 18rem;
    flex-direction: row-reverse;
}

.outer-wrapper.medium {
    box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1);
    border-radius: 20px;
    flex-direction: column;
    width: 25rem;
    height: 12rem;
}

.outer-wrapper.small:hover {
    box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1);
    cursor: pointer;
}

.outer-wrapper.medium:hover {
    box-shadow: 0 10px 15px -3px rgb(0 0 0 / 0.1), 0 4px 6px -4px rgb(0 0 0 / 0.1);
    cursor: pointer;
}

.image-wrapper {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
}

.image-wrapper.small {
    flex-direction: column;
    gap: 0.5rem;
}

.image.small {
    width: 5rem;
    height: 5rem;
}

.image-wrapper>img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.image-wrapper.medium {
    height: 10rem;
    background-color: oklch(0.97 0 0);
}

.content-wrapper {
    flex: 1;
    padding: 1rem;
    display: flex;
    gap: 0.5rem;
    text-align: center;
    flex-direction: column;
}

.content-wrapper.small {
    gap: 1rem;
    padding: 0.5rem;
}


.title.small {
    text-align: left;
    line-clamp: 1;
    -webkit-line-clamp: 1;
    -webkit-box-orient: vertical;
    display: -webkit-box;
    overflow: hidden;
    line-height: 1.5;
    font-weight: 400;
    font-size: medium;

}

.title.medium {
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

.description.small {
    padding-right: 0.2rem;
    text-align: left;
    line-height: 1.2;
    font-weight: 400;
    font-size: small;
}
</style>
