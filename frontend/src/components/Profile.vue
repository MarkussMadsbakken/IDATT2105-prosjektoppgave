<script setup lang="ts">

import Button from '@/components/Button.vue';
import { logout, useAuth } from '@/stores/auth';
import { useRoute, useRouter } from 'vue-router';
import ListingCard from "@/components/ListingCard.vue";
import { Settings } from "lucide-vue-next";
import UserImage from "@/components/UserImage.vue";
import { useGetUser } from '@/actions/user';
import type { Listing } from '@/types';
import { computed } from 'vue';
import Divider from '@/components/Divider.vue';
import { useGetUserListings } from '@/actions/user';



const emit = defineEmits<{
    (e: 'logout'): void;
}>();

const props = defineProps<{
    userId: number;
    isOwnProfile?: boolean;
}>();


const router = useRouter();
const handleLogout = () => {
    emit('logout');
}

const { data: user } = useGetUser(props.userId);
const { data: listings, isPending, isError, error } = useGetUserListings(props.userId);

const createdAtText = computed(() => "Medlem siden: " + new Date(user?.value?.createdAt!).getFullYear());

const favoriteListings = [] as Listing[];

</script>

<template>
    <div class="page">
        <div class="user-box">
            <div class="user-info-box">
                <UserImage :user-id="props.userId" :size="120" stroke-width="1.5" />
                <h3 class="username">{{ user?.username }}</h3>
            </div>
            <div class="text-field">
                <div class="admin-field" v-if=user?.isAdmin>Admin</div>
                <div class="member-since">{{ createdAtText }}</div>
            </div>
            <div class="settings-container" v-if="isOwnProfile">
                <Settings class="settings-button" :size="35" @click="router.push('/profile/edit')" :stroke-width="2.2">
                </Settings>
                <Button class="logout-button" variant="primary" @click="handleLogout">{{ $t("logout") }}</Button>
            </div>

        </div>
        <Divider />
        <div class="title-wrapper">
            <div class="title"> {{ isOwnProfile ? $t("ownListings") : $t("listingsByUser", {
                name: user?.firstName ?? user?.username
            }) }} </div>
            <RouterLink class="router-link" :to="`/profile/${props.userId}/listings`">Vis alle</RouterLink>
        </div>
        <div v-if="isPending">Laster oppføringer...</div>
        <div v-else-if="isError">Kunne ikke hente oppføringer.</div>
        <div class="listing-grid" v-else>
            <ListingCard v-for="listing in listings!.slice(0, 3)" :key="listing.uuid!" :listing="listing"
                size="medium" />
        </div>
        <template v-if="isOwnProfile">
            <Divider />
            <div class="title-wrapper">
                <div class="title"> Mine favoritter </div>
                <RouterLink class="router-link" to="/favorites">Vis alle</RouterLink>
            </div>
            <div class="listing-grid">
                <ListingCard v-for="listing in favoriteListings" :key="listing.uuid" :listing="listing" size="medium" />
            </div>
        </template>

    </div>

</template>


<style scoped>
.user-box {
    margin-top: 4rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-radius: 5px;
    width: 40rem;
    height: 8rem;
    gap: 3rem;
}

.user-info-box {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    align-items: center;
    justify-content: center;
}

.page {
    display: flex;
    flex-direction: column;
    gap: 3rem;
    align-items: center;
    justify-content: center;
    padding-bottom: 3rem;
}

.username {
    font-size: 20px;
    font-weight: bold;
}

.text-field {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 3rem;
}

.settings-button {
    cursor: pointer;
    display: block;

}

.settings-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 1rem;
}

.listing-grid {
    display: flex;
    flex-wrap: wrap;
    gap: 2rem;
    justify-content: center;
    max-width: calc(3*25rem + 2*2rem);
    margin: 0 auto;
}

.listing-grid>* {
    margin-top: 1rem;
    flex: 1 1 18rem;
    max-width: 25rem;
    box-sizing: border-box;
}

.logout-button {
    width: 7rem;
    height: 2rem;
    font-size: 14px;
    padding: 0.25rem 0.25rem;
}

.router-link {
    position: absolute;
    right: 0;
    top: 50%;
    transform: translateY(-50%);
    font-size: 25px;
    font-weight: bold;
    color: var(--color-primaryButton);
    text-decoration: none;
    cursor: pointer;
    margin-right: 3rem;
}

.title-wrapper {
    position: relative;
    width: 100%;
    max-width: calc(3*25rem + 2*2rem);
    text-align: center;
}

.title-wrapper .title {
    font-size: 50px;
    font-weight: bold;
}
</style>
