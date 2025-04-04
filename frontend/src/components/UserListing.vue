<script setup lang="ts">
  import {useAuth} from "@/stores/auth.ts";

  const props = defineProps<{
    userId: number;
  }>();

</script>

<template>
<div>{{props.userId}}</div>

</template>

<style scoped>


</style>
