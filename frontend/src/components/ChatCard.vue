<script setup lang="ts">
import type { Chat } from '@/types';
import UserImage from './UserImage.vue';
import ListingCard from './ListingCard.vue';
import { useRouter } from 'vue-router';
import { useAuth } from '@/stores/auth';
import { computed } from 'vue';
import { useGetUser } from '@/actions/user';
import { useGetListing } from '@/actions/getListing';
import { useLatestMessage } from '@/actions/chat';

const { chat } = defineProps<{
    chat: Chat
}>();

const auth = useAuth();

const ownsListing = computed(() => {
    if (!chat) return false;
    return chat.sellerId === auth.userId;
});

const otherUserId = computed(() => {
    if (!chat) return null;
    return ownsListing.value ? chat.buyerId : chat.sellerId;
});

const router = useRouter();

const onClick = () => {
    router.push(`/chat/${chat.chatId}`);
}

const { data: listing } = useGetListing(chat.listingId);
const { data: otherUser } = useGetUser(otherUserId.value!);
const { data: latestMessage } = useLatestMessage(chat.chatId);

</script>

<template>
    <div class="chat-wrapper" @click="onClick">
        <div class="profile-wrapper">
            <UserImage :user-id="otherUserId!" />
            {{ otherUser?.firstName }} {{ otherUser?.lastName }}
        </div>
        <div class="last-message-wrapper">
            <template v-if="latestMessage">
                {{ latestMessage?.senderId === auth.userId ? $t("you") : otherUser?.username }}: {{
                    latestMessage?.message }}
            </template>
            <template v-else>
                {{ !ownsListing ? $t("youHaveNotSentMessage") : $t("youHaveNotReceivedMessage") }}
            </template>
        </div>
        <div class="listing-wrapper">
            <ListingCard size="small" :listing="listing!" v-if="listing" />
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
    gap: 1rem;
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