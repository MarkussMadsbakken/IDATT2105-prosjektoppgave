<script setup lang="ts">
import ChatCard from '@/components/ChatCard.vue';
import type { User } from '@/types';
import type { Chat } from '@/types/message';



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

const chat1: Chat = {
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
    ],
    yourRole: "seller",
}

const chat2: Chat = {
    id: 2,
    seller: user2,
    buyer: user1,
    listing: {
        id: "2",
        title: "sjarmerende sofa",
        description: "Sofaen er veldig sjarmerende, og jeg har hatt den i mange år. Den er veldig god å sitte i, og jeg har tatt godt vare på den. Håper noen vil ta godt vare på den videre!",
        price: 3000,
        seller: user2
    },
    yourRole: "seller",
    messages: [
        {
            id: 1,
            sender: "seller",
            content: "Hei, jeg er interessert i sofaen!",
            createdAt: new Date(),
        },
        {
            id: 2,
            sender: "buyer",
            content: "Desverre, jeg har allerede solgt den.",
            createdAt: new Date(),
        },
    ],
}

const chats = [chat1, chat2];


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