<script setup lang="ts">
import { logout } from '@/stores/auth';
import { useRoute, useRouter } from 'vue-router';
import Profile from '@/components/Profile.vue';

const route = useRoute();
const userId = Number(route.params.userid as string);

</script>

<template>
  <Profile :user-id="userId" :is-own-profile="false" />
</template>
