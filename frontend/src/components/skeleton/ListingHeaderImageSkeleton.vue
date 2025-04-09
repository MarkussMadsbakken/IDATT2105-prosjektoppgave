<script setup lang="ts">

</script>

<template>
    <div class="loading-image-skeleton"></div>
</template>

<style scoped>
.loading-image-skeleton {
    width: 100%;
    height: 100%;
    background-color: var(--color-skeleton);
    animation: pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}
</style>