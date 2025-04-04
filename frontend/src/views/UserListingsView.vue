<script setup lang="ts">
import UserListing from '@/components/UserListing.vue'
import {useAuth} from "@/stores/auth.ts";
import {useRoute} from "vue-router";

  const route = useRoute();
  const userId = Number(route.params.userid as string);


</script>

<template>
  <UserListing :userId="userId"></UserListing>
</template>

<style scoped>

</style>
