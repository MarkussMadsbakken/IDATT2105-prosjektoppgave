<script setup lang="ts">
import type { Chat } from '@/types';
import { useRouter } from 'vue-router';
import { useAuth } from '@/stores/auth';
import { computed } from 'vue';
import { useGetListing } from '@/actions/getListing';
import { useLatestMessage } from '@/actions/chat';
import ListingHeaderImage from './ListingHeaderImage.vue';

const { chat, isSelected } = defineProps<{
    chat: Chat
    isSelected?: boolean
}>();

const router = useRouter();

const onClick = () => {
    router.push(`/chat/${chat.chatId}`);
}

const { data: listing, isPending: listingIsPending } = useGetListing(chat.listingId);
const { data: latestMessage, isPending: latestMessageIsPending } = useLatestMessage(chat.chatId);

</script>

<template>
    <div class="chat-card-wrapper" :class="{ selected: isSelected }" @click="onClick">
        <div class="image-container">
            <ListingHeaderImage :listing-id="chat.listingId" />
        </div>
        <div class="chat-card-content" v-if="!listingIsPending && !latestMessageIsPending">
            <div class="chat-card-header">
                <div class="chat-card-title">
                    {{ listing?.name }}
                </div>
                <div class="chat-card-price">
                    {{ listing?.price }} kr
                </div>
            </div>
            <template v-if="latestMessage">
                <div class="chat-card-message">
                    {{ latestMessage?.message }}
                </div>
                <div class="chat-card-time">
                    {{ new Date(latestMessage?.createdAt!).toLocaleDateString() }}
                    {{ new Date(latestMessage?.createdAt!).toLocaleTimeString([], {
                        hour: '2-digit', minute: '2-digit'
                    }) }}
                </div>
            </template>
        </div>
        <div class="chat-card-content" v-else>
            <div class="chat-card-header">
                <div class="chat-card-title-skeleton">
                </div>
                <div class="chat-card-price-skeleton">
                </div>
            </div>
            <div class="chat-card-message-skeleton">
            </div>
            <div class="chat-card-time-skeleton">
            </div>
        </div>

    </div>
</template>

<style scoped>
.chat-card-message-skeleton {
    margin-top: 0.5rem;
    width: 10rem;
    height: 1rem;
    background-color: var(--color-skeleton);
    border-radius: 10px;
    animation: pulse 1.5s infinite;
}

.chat-card-time-skeleton {
    position: absolute;
    bottom: 1rem;
    width: 9rem;
    height: 1rem;
    background-color: var(--color-skeleton);
    border-radius: 10px;
    animation: pulse 1.5s infinite;
}

.chat-card-price-skeleton {
    width: 3rem;
    height: 1rem;
    background-color: var(--color-skeleton);
    border-radius: 10px;
    animation: pulse 1.5s infinite;
}

.chat-card-title-skeleton {
    width: 10rem;
    height: 1.5rem;
    background-color: var(--color-skeleton);
    border-radius: 10px;
    animation: pulse 1.5s infinite;
}

.chat-card-message {
    font-size: 0.8rem;
    text-align: center;
    color: var(--color-text-secondary);
    line-clamp: 2;
    -webkit-line-clamp: 2;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    overflow: hidden;
}

.chat-card-time {
    position: absolute;
    bottom: 1rem;
    color: var(--color-text-secondary);
}

.chat-card-price {
    font-size: 1rem;
    font-weight: 400;
}

.chat-card-title {
    font-size: 1.2rem;
    font-weight: 500;
}

.chat-card-header {
    display: flex;
    flex-direction: row;
    text-align: justify;
    align-items: baseline;
    gap: 0.5rem;
}

.chat-card-content {
    margin-top: 1rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    position: relative;
    width: 100%;
    height: 100%;
}

.image-container {
    padding: 0.5rem;
    width: 8rem;
    height: 8rem;
    border-radius: 10px;
    overflow: hidden;
    flex-shrink: 0;
}

.image-container>img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    object-position: center;
}

.chat-card-wrapper {
    flex-shrink: 0;
    display: flex;
    flex-direction: row;
    align-items: center;
    width: 100%;
    height: 8rem;
    border-radius: 10px;
    cursor: pointer;
    background-color: white;
    border: 1px solid oklch(92.2% 0 0);
    box-shadow: 0 10px 15px -3px rgb(0 0 0 / 0.1), 0 4px 6px -4px rgb(0 0 0 / 0.1);
}


.chat-card-wrapper.selected {
    background-color: #1E6676;
    color: white;
}
</style>