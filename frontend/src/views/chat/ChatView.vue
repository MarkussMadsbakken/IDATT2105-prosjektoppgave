<script setup lang="ts">
import { sendMessage, useChat, useChatMessages, useChats } from '@/actions/chat';
import { useWebSocket } from '@/actions/websocket';
import Button from '@/components/Button.vue';
import ChatMessage from '@/components/ChatMessage.vue';
import LoadingSpinner from '@/components/LoadingSpinner.vue';
import SellerInfo from '@/components/SellerInfo.vue';
import SellerInfoSkeleton from '@/components/skeleton/SellerInfoSkeleton.vue';
import TextInput from '@/components/TextInput.vue';
import { useAuth } from '@/stores/auth';
import type { Message } from '@/types';
import { useMutation, useQueryClient } from '@tanstack/vue-query';
import { computed, nextTick, onMounted, ref, watch } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const auth = useAuth();
const queryClient = useQueryClient();

const message = ref<string>("");

const chatId = ref(Number(route.params.id));

watch(route, (newRoute) => {
    chatId.value = Number(newRoute.params.id);
});

const { data: chat, isPending: chatIsPending, isError: chatIsError, error: chatError } = useChat(chatId);
const { data: messages, isPending: messagesIsPending, isError: messagesIsError, error: messagesError } = useChatMessages(chatId);

const lastMessageRef = ref<HTMLDivElement | null>(null);

watch(
    messages,
    (newMessages) => {
        if (!newMessages) return;
        nextTick(() => {
            setTimeout(() => {
                lastMessageRef.value?.scrollIntoView({ behavior: "smooth", block: "end" });
            }, 100);
        });
    }
);


const { mutate: sendMessageMutation, isPending: sendMessageIsPending } = useMutation({
    mutationFn: sendMessage,
    onSuccess: () => {
        queryClient.invalidateQueries({
            queryKey: ["chat", chatId, "messages"]
        });
        message.value = "";
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
        if (message.chatId !== chatId.value) return;
        queryClient.setQueryData(["chat", chatId, "messages"], (oldMessages: Message[] | undefined) => {
            return [...(oldMessages || []), message];
        });
    }
);

if (messages.value) {
    nextTick(() => {
        setTimeout(() => {
            lastMessageRef.value?.scrollIntoView({ behavior: "smooth", block: "end" });
        }, 100);
    });
}

</script>

<template>
    <div class="chats-outer-wrapper">
        <template v-if="chatIsPending && messagesIsPending">
            <div class="seller-info-wrapper">
                <SellerInfoSkeleton />

            </div>
            <div class="messages-wrapper">
                <LoadingSpinner />
            </div>
        </template>
        <template v-else-if="!chatIsError">
            <div class="seller-info-wrapper">
                <SellerInfo v-if="chat" :can-contact-seller="false" :key="chat?.chatId"
                    :userId="isSeller ? chat?.buyerId! : chat?.sellerId!" />
            </div>
            <div v-if="messagesIsError" class="error">
                <p>{{ $t("error") }}</p>
            </div>
            <div v-else-if="!messages || messages.length === 0 && !messagesIsPending && !chatIsPending"
                class="no-messages">
                <p>{{ $t("messages.chat.noMessages") }}</p>
            </div>
            <div class="messages-wrapper">

                <ChatMessage v-for="(message) in messages" :key="message.id" :message="message"
                    :own-message="message.senderId == auth.userId" />
            </div>
        </template>
        <form class="chat-box-wrapper" @submit.prevent="sendMessageMutation({ chatId: chatId, message: message })">
            <TextInput class="input" v-model="message" />
            <Button id="send-button" variant="primary">
                <template v-if="sendMessageIsPending">
                    <LoadingSpinner />
                </template>
                <template v-else>
                    {{ $t("messages.chat.send") }}
                </template>
            </Button>
        </form>
        <div ref="lastMessageRef">
        </div>
    </div>
</template>

<style scoped>
.seller-info-wrapper {
    width: 90%;
    display: flex;
    justify-content: center;
    align-items: center;
}

.messages-wrapper {
    width: 100%;
    padding-bottom: 5rem;
}

.input {
    height: 3rem;
    width: 30rem;
}

@media only screen and (max-width: 1200px) {
    .input {
        width: 20rem;
    }
}

@media only screen and (max-width: 600px) {
    .input {
        width: 100%;
    }
}

.chat-box-wrapper {
    line-height: 0;
    padding: 1rem;
    position: absolute;
    bottom: 2rem;
    display: flex;
    flex-direction: row;
    background-color: white;
    border-radius: 10px;
    gap: 1rem;
    box-shadow: 0 1px 3px 0 rgb(0 0 0 / 0.1), 0 1px 2px -1px rgb(0 0 0 / 0.1);
    height: fit-content;
}

.chats-outer-wrapper {
    padding-top: 1rem;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    gap: 1rem;
}
</style>
