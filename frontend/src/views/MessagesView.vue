<script setup lang="ts">
import { useChats } from '@/actions/chat';
import ChatCard from '@/components/ChatCard.vue';
import ChatCardSkeleon from '@/components/skeleton/ChatCardSkeleon.vue';

const { data: chats, isPending, isError, error } = useChats();

</script>

<template>
    <div class="outer-wrapper">
        <div class="chats-wrapper">
            <div class="page-title">
                {{ $t("messages.messages") }}
            </div>
            <ChatCardSkeleon :key="i" v-if="isPending" v-for="i in 5" />
            <div v-else-if="isError">
                <p>{{ $t("messages.error") }} {{ error }}</p>
            </div>
            <div v-else-if="!chats || chats.length === 0">
                <p>{{ $t("messages.noMessages") }}</p>
            </div>
            <ChatCard v-for="chat in chats" :chat="chat" :is-selected="Number($route.params.id) === chat.chatId" />
        </div>
        <div class="chat-view-wrapper">
            <RouterView />
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
    width: 100vw;
    height: 88vh;
    display: flex;
    flex-direction: row;
    justify-content: center;
    position: relative;
}

.chat-view-wrapper {
    width: 60vw;
    overflow-x: hidden;
}

.chats-wrapper {
    padding: 1rem;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    gap: 1rem;
    width: 40vw;
    height: 100%;
    overflow-y: scroll;
    border-right: 1px solid oklch(92.2% 0 0);
    background-color: white;
    box-shadow: 0 1px 3px 0 rgb(0 0 0 / 0.1), 0 1px 2px -1px rgb(0 0 0 / 0.1);
}


@media (max-width: 1000px) {
    .page-title {
        display: none;
    }

    .outer-wrapper {
        width: 100vw;
        height: 100%;
        flex-direction: column;
    }

    .chats-wrapper {
        width: 100vw;
        height: 20vh;
        border-right: none;
        border-bottom: 1px solid oklch(92.2% 0 0);
        flex-shrink: 0;
        overflow-x: scroll;
        flex-direction: row;
    }

    .chat-view-wrapper {
        flex-shrink: 0;
        width: 100vw;
        height: 68vh;
        overflow-x: hidden;
        border-bottom: none;
    }
}
</style>