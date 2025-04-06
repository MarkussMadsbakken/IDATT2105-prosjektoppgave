<script setup lang="ts">
import { sendMessage, useChat, useChatMessages, useChats } from '@/actions/chat';
import { useWebSocket } from '@/actions/websocket';
import Button from '@/components/Button.vue';
import ChatMessage from '@/components/ChatMessage.vue';
import LoadingSpinner from '@/components/LoadingSpinner.vue';
import SellerInfo from '@/components/SellerInfo.vue';
import TextInput from '@/components/TextInput.vue';
import { useAuth } from '@/stores/auth';
import type { Chat, Message, User } from '@/types';
import { useMutation, useQueryClient } from '@tanstack/vue-query';
import { watchOnce } from '@vueuse/core';
import { computed, nextTick, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const auth = useAuth();
const queryClient = useQueryClient();

const message = ref<string>("");

const chatId = Number(route.params.chatId);
const { data: chat, isPending: chatIsPending, isError: chatIsError, error: chatError } = useChat(Number(chatId));
const { data: messages, isPending: messagesIsPending, isError: messagesIsError, error: messagesError } = useChatMessages(Number(chatId));

const lastMessageRef = ref<HTMLDivElement | null>(null);

onMounted(() => {
    nextTick(() => {
        lastMessageRef.value?.scrollIntoView({ behavior: "smooth", block: "end" });
    });
});

const { mutate: sendMessageMutation, isPending: sendMessageIsPending } = useMutation({
    mutationFn: sendMessage,
    onSuccess: () => {
        queryClient.invalidateQueries({
            queryKey: ["chat", chatId, "messages"]
        });
        message.value = "";
        nextTick(() => {
            lastMessageRef.value?.scrollIntoView({ behavior: "smooth", block: "end" });
        });
    },
});

const isSeller = computed(() => {
    if (!chat.value) return false;
    return chat.value.sellerId === auth.userId;
});


const ws = useWebSocket();

ws.subscribe(
    "/user/queue/messages",
    (message: Message) => {
        if (message.chatId !== chatId) return;
        queryClient.setQueryData(["chat", chatId, "messages"], (oldMessages: Message[] | undefined) => {
            return [...(oldMessages || []), message];
        });
        nextTick(() => {
            lastMessageRef.value?.scrollIntoView({ behavior: "smooth", block: "end" });
        });
    }
);

</script>

<template>
    <div class="outer-wrapper" v-if="!chatIsPending && !chatIsError">
        <div class="seller-info-wrapper">
            <SellerInfo :userId="isSeller ? chat?.buyerId! : chat?.sellerId!" />
        </div>
        <div v-if="messagesIsPending" class="loading">
            <p>{{ $t("loading") }}</p>
        </div>
        <div v-else-if="messagesIsError" class="error">
            <p>{{ $t("error") }}</p>
        </div>
        <div v-else-if="!messages || messages.length === 0" class="no-messages">
            <p>{{ $t("noMessages") }}</p>
        </div>
        <div class="messages-wrapper">
            <div v-for="(message) in messages" :key="message.id">
                <ChatMessage :message="message" :own-message="message.senderId == auth.userId" />
            </div>
        </div>
        <form class="chat-box-wrapper" @submit.prevent="sendMessageMutation({ chatId: chatId, message: message })">
            <TextInput class="input" v-model="message" />
            <Button variant="primary">
                <template v-if="sendMessageIsPending">
                    <LoadingSpinner />
                </template>
                <template v-else>
                    {{ $t("send") }}
                </template>
            </Button>
        </form>
        <div ref="lastMessageRef">
        </div>
    </div>
</template>

<style scoped>
.messages-wrapper {
    width: 60vw;
}

.seller-info-wrapper {
    position: sticky;
}

.input {
    height: 3rem;
    width: 30rem;
}

.chat-box-wrapper {
    line-height: 0;
    padding: 1rem;
    position: sticky;
    bottom: 0;
    display: flex;
    flex-direction: row;
    background-color: white;
    border-radius: 10px;
    gap: 1rem;
    box-shadow: 0 1px 3px 0 rgb(0 0 0 / 0.1), 0 1px 2px -1px rgb(0 0 0 / 0.1);
    height: fit-content;
}

.outer-wrapper {
    padding-top: 1rem;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    gap: 1rem;
}
</style>