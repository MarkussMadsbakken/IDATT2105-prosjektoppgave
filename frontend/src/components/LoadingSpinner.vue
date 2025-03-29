<template>
    <div class="loading-spinner">
        <div class="spinner"></div>
    </div>
</template>

<style scoped>
.loading-spinner {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
}

.spinner {
    width: 25px;
    height: 25px;
    border: 2px solid #f3f3f3;
    border-top: 2px solid var(--color-primaryButton);
    border-radius: 50%;
    animation: spin 1s linear infinite;
}

@keyframes spin {
    0% {
        transform: rotate(0deg);
    }

    100% {
        transform: rotate(360deg);
    }
}
</style>