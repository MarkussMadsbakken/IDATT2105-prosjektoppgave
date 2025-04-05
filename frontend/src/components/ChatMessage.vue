<script setup lang="ts">

import type { Message } from '@/types'

const { message, ownMessage } = defineProps<{
    message: Message,
    ownMessage: boolean,
}>();
</script>

<template>
    <div class="message-wrapper" :class="{ 'own-message': ownMessage }">
        <div class="message-content-wrapper" :class="{ 'own-message': ownMessage }">
            <div class="message-content" :class="{ 'own-message': ownMessage }">
                {{ message.message }}
            </div>
            <div class="message-time">
                {{ new Date(message.createdAt).toLocaleString("no-NO", {
                    day: "numeric",
                    month: "long",
                    hour: "numeric",
                    minute: "numeric",
                })
                }}
            </div>
        </div>
    </div>
</template>

<style scoped>
.message-content-wrapper {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
}

.message-content-wrapper.own-message {
    align-items: flex-end;
}

.message-wrapper {
    display: flex;
    flex-direction: row;
    align-items: flex-start;
    margin: 1rem;
}

.message-wrapper.own-message {
    flex-direction: row-reverse;
}

.message-content {
    background-color: #f0f0f0;
    padding: 1rem;
    border-radius: 1rem;
}

.message-content.own-message {
    background-color: var(--color-primaryButton);
    color: white;
}

.message-time {
    font-size: small;
    color: grey;
}
</style>