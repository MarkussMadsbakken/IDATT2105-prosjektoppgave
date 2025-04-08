<script setup lang="ts">
import { DogIcon } from "lucide-vue-next";

</script>

<template>
  <div class="not-found-wrapper">
    <div class="dog-wrapper">
      <DogIcon class="dog" :size="100"></DogIcon>
    </div>
    <div class="not-found-text">{{ $t('notFound.pageNotFound') }}</div>
    <RouterLink class="home-link" to="/">{{ $t('notFound.backToHomePage') }}</RouterLink>
  </div>
</template>

<style scoped>
.dog-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  padding-top: 10rem;
}

.not-found-wrapper {
  display: flex;
  flex-direction: column;
  text-align: center;
}

.not-found-text {
  font-size: 3rem;
}

.home-link {
  font-size: 2rem;
  font-weight: bold;
  color: black;
}
</style>
