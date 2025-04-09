<script setup lang="ts">
import type { User } from '@/types';
import { LogOut, Settings } from 'lucide-vue-next';
import { computed } from 'vue';
import UserImage from '../UserImage.vue';
import Button from '../Button.vue';


const props = defineProps<{
    user: User;
    isOwnProfile: boolean;
}>();

const emit = defineEmits<{
    (e: 'logout'): void;
    (e: 'editProfile'): void;
}>();


const date = computed(() => new Date(props.user.createdAt!).getFullYear());

</script>

<template>
    <div class="profile-header-wrapper">
        <div class="left-wrapper">
            <div class="profile-header-image-wrapper">
                <UserImage :user-id="user.id" :size="120" stroke-width="1.5" />
            </div>
            <div class="profile-header-info-wrapper">
                <div class="name">{{ user.firstName }} {{ user.lastName }}</div>
                <div class="username">@{{ user.username }}</div>
                <div class="member-since">{{ $t('profile.memberSince', { date }) }}</div>
            </div>
        </div>
        <div class="settings-container" v-if="isOwnProfile">
            <Button class="settings-button" variant="outline" @click="$emit('editProfile')">
                {{ $t("profile.editProfile") }}
                <Settings :stroke-width="1.5">
                </Settings>
            </Button>
            <Button class="logout-button" variant="destructive" @click="$emit('logout')">
                {{ $t("profile.logout") }}
                <LogOut />
            </Button>
        </div>
    </div>
</template>

<style scoped>
.name {
    font-size: 1.8rem;
    font-weight: 600;
}

.logout-button {
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 0.5rem;
}

.settings-button {
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 0.5rem;
    font-weight: 500;
}

.settings-container {
    display: flex;
    align-items: center;
    gap: 1.5rem;
}

.left-wrapper {
    display: flex;
    align-items: center;
    gap: 1rem;
}

@media (max-width: 950px) {
    .settings-container {
        flex-direction: column;
        gap: 1rem;
    }
}

@media (max-width: 700px) {
    .profile-header-wrapper {
        flex-direction: column;
        gap: 0.5rem;
    }

    .left-wrapper {
        align-items: center;
        text-align: center;
        flex-direction: column;
    }
}


.profile-header-wrapper {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 1rem;
    background-color: #ffffff;
    border-radius: 10px;
    width: 100%;
}

.profile-header-info-wrapper {
    display: flex;
    flex-direction: column;
    gap: 0.25rem;
}
</style>