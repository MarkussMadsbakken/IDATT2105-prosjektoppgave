<script setup lang="ts">
import UserListing from '@/components/UserListing.vue'
import {useAuth} from "@/stores/auth.ts";
import {useRoute} from "vue-router";
import {computed} from "vue";

  const route = useRoute();
  const userId = computed(() => Number(route.params.userid));


</script>

<template>
  <UserListing :userId="userId"></UserListing>
</template>

<style scoped>

</style>
