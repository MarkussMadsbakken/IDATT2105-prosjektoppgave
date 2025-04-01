<script setup lang="ts">
import SellerInfo from '@/components/SellerInfo.vue';
import type { Chat, User } from '@/types';

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
    ],
    yourRole: "seller",
}
</script>

<template>
    <SellerInfo :userEntity="chat.seller" />
</template>

<style scoped></style>