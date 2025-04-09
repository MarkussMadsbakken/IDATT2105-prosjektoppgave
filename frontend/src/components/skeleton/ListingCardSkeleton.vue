<script setup lang="ts">
import { useRouter } from 'vue-router';
import ListingHeaderImageSkeleton from './ListingHeaderImageSkeleton.vue';

const props = withDefaults(defineProps<{
    size?: "small" | "medium";
}>(), {
    size: "medium",
});

</script>

<template>
    <div class="outer-wrapper" :class="props.size">
        <div class="image-wrapper" :class="props.size">
            <ListingHeaderImageSkeleton />
            <div class="price-skeleton" :class="props.size">
            </div>
        </div>
        <div class="content-wrapper" :class="props.size">
            <div class="title-skeleton" :class="props.size">
            </div>
            <div class="description-skeleton" :class="props.size">
                <div></div>
                <div></div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.price-skeleton.medium {
    position: absolute;
    bottom: 0;
    left: 0;
    margin-left: 0.5rem;
    margin-bottom: 0.5rem;
    width: 5rem;
    height: 1rem;
    border-radius: 0.25rem;
    background-color: #e4e4e4;
    border: 1px solid black;
    animation: pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}

.price-skeleton.small {
    width: 5rem;
    height: 1rem;
    border-radius: 0.25rem;
    background-color: var(--color-skeleton);
    animation: pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}

.outer-wrapper {
    display: flex;
    line-height: 0;
    overflow: hidden;
    background-color: #ffffff;
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
    min-width: 25rem;
    height: 12rem;
    transition-property: box-shadow;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
    transition-duration: 150ms;
    flex-shrink: 0;
}


.outer-wrapper.medium:hover {
    box-shadow: 0 10px 15px -3px rgb(0 0 0 / 0.1), 0 4px 6px -4px rgb(0 0 0 / 0.1);
}

.image-wrapper {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    overflow: hidden;
}

.image-wrapper>img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.image-wrapper.medium {
    height: 10rem;
}

.image-wrapper.small {
    flex-shrink: 0;
    width: 5rem;
    gap: 0.5rem;
    height: 5rem;
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
    padding: 0.5rem;
    gap: 1rem;
}


.title-skeleton.small {
    text-align: center;
    font-size: large;
    background-color: var(--color-skeleton);
    animation: pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
    width: 8rem;
    height: 1.5rem;
    border-radius: 0.25rem;
}

.title-skeleton.medium {
    line-height: 1;
    width: 10rem;
    height: 1.2rem;
    background-color: var(--color-skeleton);
    margin-left: auto;
    margin-right: auto;
    border-radius: 0.25rem;
    animation: pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}

.description-skeleton.medium {
    line-height: 1.6;
    height: 1rem;
    width: 15rem;
    background-color: var(--color-skeleton);
    margin-left: auto;
    margin-right: auto;
    border-radius: 0.25rem;
    animation: pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}

.description-skeleton.small {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
}

.description-skeleton.small>div {
    width: 10rem;
    height: 1rem;
    border-radius: 0.25rem;
    background-color: var(--color-skeleton);
    animation: pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}

.price.medium {
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
</style>
