<script setup lang="ts">
import { useChats } from '@/actions/chat';
import ChatCard from '@/components/ChatCard.vue';

const { data: chats, isPending, isError, error } = useChats();

</script>

<template>
    <div class="outer-wrapper">
        <div class="page-title">
            {{ $t("messages") }}
        </div>
        <div v-if="isPending">
            <p>{{ $t("loading") }}</p>
        </div>
        <div v-else-if="isError">
            <p>{{ $t("error") }}</p>
        </div>
        <div v-else-if="!chats || chats.length === 0">
            <p>{{ $t("noMessages") }}</p>
        </div>
        <div v-for="chat in chats" v-else>
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
    padding-top: 1rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1rem;
    width: 100%;
}
</style>