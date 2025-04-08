<template>
    <div class="chat-not-found">
        <div class="chat-not-found-header">
            <h1>{{ $t("chatNotSelected") }}</h1>
        </div>
        <div class="chat-not-found-content">
            <p>{{ $t("chatNotSelectedDescription") }}</p>
        </div>
    </div>
</template>

<script setup lang="ts">
</script>

<style scoped>
.chat-not-found {
    display: flex;
    flex-direction: column;
    margin-top: 2rem;
    align-items: center;
    width: 100%;
    height: fit-content;
}

.chat-not-found-header {
    font-size: 1.5rem;
    font-weight: bold;
    margin-bottom: 1rem;
}

.chat-not-found-content {
    font-size: 1.2rem;
    text-align: center;
}
</style>