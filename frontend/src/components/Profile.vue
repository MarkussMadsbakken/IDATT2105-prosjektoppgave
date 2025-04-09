<script setup lang="ts">

import { useRouter } from 'vue-router';
import ListingCard from "@/components/ListingCard.vue";
import { useGetUser, useGetUserBookmarks, useGetUserListings } from '@/actions/user';
import Divider from '@/components/Divider.vue';
import ListingCardSkeleton from './skeleton/ListingCardSkeleton.vue';
import ProfileHeader from './profile/ProfileHeader.vue';
import ProfileHeaderSkeleton from './skeleton/ProfileHeaderSkeleton.vue';

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

const { data: user, isPending: userIsPending } = useGetUser(props.userId);
const { data: listings, isPending, isError, error } = useGetUserListings(props.userId);
const { data: favoriteListings, isPending: isBookmarkPending, isError: isBookmarkError, error: bookmarkError } = useGetUserBookmarks();

</script>

<template>
    <div class="page">
        <div class="profile-header">
            <ProfileHeaderSkeleton :is-own-profile="props.isOwnProfile" v-if="userIsPending" />
            <ProfileHeader v-else :user="user!" :is-own-profile="props.isOwnProfile"
                @edit-profile="router.push('/profile/edit')" @logout="handleLogout" />
        </div>
        <Divider />
        <div class="title-wrapper">
            <div class="title"> {{ isOwnProfile ? $t("profile.ownListings") : $t("profile.listingsByUser", {
                name: user?.firstName ?? user?.username
            }) }} </div>
            <RouterLink class="router-link" :to="(`/profile/${props.userId}/listings`)">{{ $t("profile.showAll") }}
            </RouterLink>
        </div>
        <div v-if="isPending" class="listing-grid">
            <ListingCardSkeleton :size="'medium'" v-for="i in 3" :key="i" />
        </div>
        <div v-else-if="isError">{{ $t("profile.couldNotLoadListings") }}</div>
        <template v-else>
            <div v-if="listings && listings.length > 0" class="listing-grid">
                <ListingCard v-for="listing in listings.slice(0, 3)" :key="listing.uuid" :listing="listing"
                    size="medium" />
            </div>
            <div v-else class="no-listings">
                {{ $t('profile.emptyListings') }}
            </div>
        </template>
        <template v-if="isOwnProfile">
            <Divider />
            <div class="title-wrapper">
                <div class="title"> {{ $t("profile.myFavorites") }} </div>
                <RouterLink class="router-link" to="/favorites">{{ $t("profile.showAll") }}</RouterLink>
            </div>

            <div v-if="isBookmarkPending" class="listing-grid-favorites">
                <ListingCardSkeleton :size="'medium'" v-for="i in 3" :key="i" />
            </div>
            <div v-else-if="isBookmarkError">{{ $t("profile.couldNotLoadListings") }}</div>
            <div v-else>
                <div v-if="favoriteListings && favoriteListings.length > 0" class="listing-grid-favorites">
                    <ListingCard v-for="listing in favoriteListings.slice(0, 3)" :key="listing.uuid" :listing="listing"
                        size="medium" />
                </div>
                <div v-else class="no-listings">
                    {{ $t("profile.noFavorites") }}
                </div>
            </div>
        </template>

    </div>

</template>


<style scoped>
.profile-header {
    margin-top: 1rem;
    width: 80%;
    min-width: 20rem;
}

.page {
    display: flex;
    flex-direction: column;
    gap: 3rem;
    align-items: center;
    justify-content: center;
    padding-bottom: 3rem;
}

.text-field {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 3rem;
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

.listing-grid-favorites {
    display: flex;
    flex-wrap: wrap;
    gap: 2rem;
    justify-content: center;
    max-width: calc(3*25rem + 2*2rem);
    margin-bottom: 3rem;
}

.listing-grid>* {
    margin-top: 1rem;
    flex: 1 1 18rem;
    max-width: 25rem;
    box-sizing: border-box;
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

.no-listings {
    font-size: 1rem;
    padding-bottom: 1rem;
    font-weight: bold;
}
</style>
