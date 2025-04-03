<script setup lang="ts">
import { logout, useAuth } from '@/stores/auth';
import { useRouter } from 'vue-router';
import Profile from '@/components/Profile.vue';

const router = useRouter();
const handleLogout = () => {
    logout();
    router.push('/login');
}

const auth = useAuth();

</script>

<template>
    <Profile :user-id="auth.userId!" :is-own-profile="true" @logout="handleLogout" />
</template>
