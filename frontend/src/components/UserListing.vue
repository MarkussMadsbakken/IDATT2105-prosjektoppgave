<script setup lang="ts">
  import {useAuth} from "@/stores/auth.ts";

  const props = defineProps<{
    userId: number;
  }>();

  const userID =useAuth().userId;

</script>

<template>


</template>

<style scoped>


</style>
