<script setup lang="ts">
import type { Notification } from "@/types/notification.ts";

defineProps<{
    notification: Notification;
}>();

defineEmits<{
    (e: "notificationClicked", notification: Notification): void;
}>();

</script>

<template>
    <div class="notification-outer-wrapper" @click="$emit('notificationClicked', notification)">
        <div class=" notification-inner-wrapper">
            <p>{{ $t(notification.message) }}</p>
        </div>
        <div class="dot" v-if="!notification.read"></div>
    </div>
</template>

<style scoped>
.notification-outer-wrapper {
    display: flex;
    line-height: 1.5;
    flex-direction: row;
    border-radius: 5px;
    padding: 1rem;
    width: 15rem;
    height: 4rem;
    align-items: center;
    gap: 1rem;
    color: black;
    border: 1px solid oklch(0.439 0 0);
    box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1);
    transition: box-shadow 0.2s ease-in-out;
    cursor: pointer;
}

.notification-outer-wrapper:hover {
    box-shadow: 0 10px 15px -3px rgb(0 0 0 / 0.1), 0 4px 6px -2px rgb(0 0 0 / 0.1);
}

.dot {
    display: inline-block;
    margin-left: 8px;
    width: 8px;
    height: 8px;
    background-color: red;
    border-radius: 50%;
}
</style>