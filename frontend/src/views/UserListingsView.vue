<script setup lang="ts">
import UserListing from '@/components/UserListing.vue'
import {useAuth} from "@/stores/auth.ts";

  const userId= useAuth().userId;
</script>

<template>
  <UserListing :userId="userId"></UserListing>
</template>

<style scoped>

</style>
