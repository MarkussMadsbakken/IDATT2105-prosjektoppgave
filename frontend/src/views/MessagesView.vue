<script setup lang="ts">
import { useChats } from '@/actions/messages';
import ChatCard from '@/components/ChatCard.vue';

const { data: chats } = useChats();

</script>

<template>
    <div class="outer-wrapper">
        <div class="page-title">
            {{ $t("messages") }}
        </div>

        <div v-for="chat in chats">
            <ChatCard :chat="chat" />
        </div>

    </div>
</template>


<style scoped>
.page-title {
    text-align: center;
    font-size: 1.5rem;
    font-weight: 500;
    margin-bottom: 1rem;
}

.outer-wrapper {
    margin-top: 1rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1rem;
    width: 100%;
}
</style>