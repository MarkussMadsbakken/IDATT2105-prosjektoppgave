<script setup lang="ts">
import type { Chat } from '@/types';
import UserImage from './UserImage.vue';
import ListingCard from './ListingCard.vue';
import { useRouter } from 'vue-router';

const { chat } = defineProps<{
    chat: Chat
}>();

const imageUrl = chat.yourRole === 'buyer' ? chat.seller.imageUrl : chat.buyer.imageUrl;
const router = useRouter();

const onClick = () => {
    router.push(`/chat/${chat.id}`);
}

</script>

<template>
    <div class="chat-wrapper" @click="onClick">
        <div class="profile-wrapper">
            <UserImage :src="imageUrl" />
            {{ chat.yourRole === 'buyer' ? chat.seller.firstName : chat.buyer.firstName }}
        </div>
        <div class="last-message-wrapper">
            {{ chat.messages[chat.messages.length - 1].content }}
        </div>
        <div class="listing-wrapper">
            <ListingCard size="small" :listing="chat.listing" />
        </div>
    </div>
</template>

<style scoped>
.listing-wrapper {
    margin-right: 1rem;
}

.last-message-wrapper {
    margin-left: 1rem;
    font-size: medium;
    font-weight: 400;
    text-align: left;
    flex: 1;
}

.profile-wrapper {
    margin-left: 1rem;
    display: flex;
    flex-direction: column;
    text-align: center;
    justify-self: flex-start;
}

.chat-wrapper {
    display: flex;
    flex-direction: row;
    align-items: center;
    width: 60rem;
    height: 8rem;
    border: 1px solid black;
    border-radius: 10px;
    cursor: pointer;
}

@media only screen and (max-width: 1000px) {
    .chat-wrapper {
        width: 30rem;
    }
}

@media only screen and (max-width: 500px) {
    .chat-wrapper {
        width: 15rem;
    }
}
</style>