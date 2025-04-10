<script setup lang="ts">
</script>

<template>
    <div class="category-card-outer-wrapper">
        <div class="icon-wrapper-skeleton">
        </div>
        <div class="text-wrapper-skeleton">
        </div>
    </div>
</template>

<style scoped>
.selected {
    background-color: var(--color-primaryButton);
    color: white;
}

.text-wrapper-skeleton {
    height: 2rem;
    width: 100%;
    flex: 1;
    display: flex;
    justify-content: baseline;
    align-items: center;
    background-color: var(--color-skeleton);
    animation: pulse 1.5s infinite;
    border-radius: 5px;
}

.icon-wrapper-skeleton {
    width: 2rem;
    height: 2rem;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: var(--color-skeleton);
    animation: pulse 1.5s infinite;
    border-radius: 25%;
}

.category-card-outer-wrapper {
    line-height: 0;
    display: flex;
    flex-direction: row;
    border: 1px solid black;
    border-radius: 5px;
    padding: 1rem;
    width: 15rem;
    height: 5rem;
    align-items: center;
    gap: 1rem;
    transition: box-shadow;
    transition-duration: 0.2s;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
    background-color: white;
}

.category-card-outer-wrapper:hover {
    box-shadow: 0 10px 15px -3px rgb(0 0 0 / 0.1), 0 4px 6px -4px rgb(0 0 0 / 0.1);
    cursor: pointer;
}
</style>
