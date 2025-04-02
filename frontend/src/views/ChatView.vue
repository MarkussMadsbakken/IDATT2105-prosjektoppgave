<script setup lang="ts">
import Button from '@/components/Button.vue';
import ChatMessage from '@/components/ChatMessage.vue';
import SellerInfo from '@/components/SellerInfo.vue';
import TextInput from '@/components/TextInput.vue';
import { WS_BASE_URL, type Chat, type User } from '@/types';
import { computed, nextTick, onMounted, ref } from 'vue';

const user1: User = {
    id: 1,
    username: "user1",
    isAdmin: false,
    firstName: "User",
    lastName: "One",
    createdAt: new Date(),
    imageUrl: "https://img.freepik.com/free-photo/view-3d-cool-modern-bird_23-2150946449.jpg?t=st=1743520776~exp=1743524376~hmac=9ab3de77ebede1d2d2b2a1386b20b6e8de2a01346565e73528927450aa6bd821&w=2000"
}

const user2: User = {
    id: 2,
    username: "user2",
    isAdmin: false,
    firstName: "User",
    lastName: "Two",
    createdAt: new Date(),
}

const chat: Chat = {
    id: 1,
    seller: user1,
    buyer: user2,
    listing: {
        id: "1",
        title: "kult kjøleskap",
        description: "Veldig kult kjøleskap jeg fant! Bare å komme med et tilbud, jeg hadde satt stor pris på det. I tillegg skal jeg bare si noe langt her slik at teksten overflower!!",
        price: 6000,
        seller: user1
    },
    messages: [
        {
            id: 1,
            sender: "buyer",
            content: "Hei, jeg er interessert i kjøleskapet!",
            createdAt: new Date(),
        },
        {
            id: 2,
            sender: "seller",
            content: "Hei, det er bare å komme å hente det!",
            createdAt: new Date(),
        },
        {
            id: 3,
            sender: "buyer",
            content: "Takk for tilbudet, jeg kommer å henter det i morgen!",
            createdAt: new Date(),
        },
        {
            id: 4,
            sender: "seller",
            content: "Perfekt, vi sees i morgen!",
            createdAt: new Date(),
        },
        {
            id: 5,
            sender: "buyer",
            content: "Hei, jeg er interessert i kjøleskapet!",
            createdAt: new Date(),
        },
        {
            id: 6,
            sender: "seller",
            content: "Hei, det er bare å komme å hente det!",
            createdAt: new Date(),
        },
        {
            id: 7,
            sender: "buyer",
            content: "Takk for tilbudet, jeg kommer å henter det i morgen!",
            createdAt: new Date(),
        },
        {
            id: 8,
            sender: "seller",
            content: "Perfekt, vi sees i morgen!",
            createdAt: new Date(),
        }
    ],
    yourRole: "seller",
}

const lastMessageRef = ref<HTMLDivElement | null>(null);

onMounted(() => {
    nextTick(() => {
        lastMessageRef.value?.scrollIntoView({ behavior: "smooth", block: "end" });
    });
});

const ws = new WebSocket(`${WS_BASE_URL}/ws/chat/${chat.id}/`);

ws.onopen = () => {
    console.log("connected");
    ws.send("Hello");
};

</script>

<template>
    <div class="outer-wrapper">
        <div class="seller-info-wrapper">
            <SellerInfo :userEntity="chat.seller" />
        </div>
        <div class="messages-wrapper">
            <div v-for="(message) in chat.messages" :key="message.id">
                <ChatMessage :message="message" :own-message="message.sender === chat.yourRole" />
            </div>
        </div>
        <div class="chat-box-wrapper">
            <TextInput class="input" />
            <Button variant="primary">{{ $t("send") }}</Button>
        </div>
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
    margin-top: 1rem;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    gap: 1rem;
}
</style>