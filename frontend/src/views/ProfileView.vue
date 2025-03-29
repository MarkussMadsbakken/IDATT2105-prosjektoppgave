<script setup lang="ts">

import Button from '@/components/Button.vue';
import { logout } from '@/stores/auth';
import { useRouter } from 'vue-router';

const router = useRouter();
const handleLogout = () => {
    logout();
    router.push('/login');
}

</script>

<template>
    <Button variant="primary" @click="handleLogout">{{ $t("logout") }}</Button>
</template>