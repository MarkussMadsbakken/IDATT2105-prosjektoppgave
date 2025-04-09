<script setup lang="ts">
import { LogOut, Settings } from 'lucide-vue-next';
import Button from '../Button.vue';
import UserImageSkeleton from './UserImageSkeleton.vue';


const props = defineProps<{
    isOwnProfile: boolean;
}>();

const emit = defineEmits<{
    (e: 'logout'): void;
    (e: 'editProfile'): void;
}>();


</script>

<template>
    <div class="profile-header-wrapper">
        <div class="left-wrapper">
            <div class="profile-header-image-wrapper">
                <UserImageSkeleton :size="120" stroke-width="1.5" />
            </div>
            <div class="profile-header-info-wrapper">
                <div class="name-skeleton"></div>
                <div class="username-skeleton"></div>
                <div class="member-since-skeleton"></div>
            </div>
        </div>
        <div class="settings-container" v-if="isOwnProfile">
            <Button class="settings-button" variant="outline">
                {{ $t("profile.editProfile") }} <Settings :stroke-width="1.5">
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
.name-skeleton {
    width: 15rem;
    height: 2rem;
    border-radius: 0.25rem;
    background-color: var(--color-skeleton);
    animation: pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}

.username-skeleton {
    width: 5rem;
    height: 1rem;
    border-radius: 0.25rem;
    background-color: var(--color-skeleton);
    animation: pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}

.member-since-skeleton {
    width: 10rem;
    height: 1rem;
    border-radius: 0.25rem;
    background-color: var(--color-skeleton);
    animation: pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
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
    box-shadow: 0 1px 3px 0 rgb(0 0 0 / 0.1), 0 1px 2px -1px rgb(0 0 0 / 0.1);
}

.profile-header-info-wrapper {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
}
</style>