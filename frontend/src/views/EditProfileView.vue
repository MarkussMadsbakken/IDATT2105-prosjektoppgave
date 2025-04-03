<script setup lang="ts">
import UserImage from "@/components/UserImage.vue";
import type { User } from '@/types'
const user: User = {id: 1, username:"Jackyboy", firstName: "Jacob", lastName:"Lein", createdAt:new Date('2002-08-12'), isAdmin: true};

</script>

<template>
  <div class="title-wrapper">
      <div class="title">Min profil</div>
  </div>
  <div class="user-box">
    <div class="user-info-box">
      <img v-if="user.imageUrl" :src="user.imageUrl" alt="Profilbilde"/>
      <UserImage v-else :size="120" stroke-width="1.5" />
      <h3 class="username">{{user.username}}</h3>
    </div>
  </div>
</template>

<style scoped>
.user-box{
  margin-top: 4rem;
  display: flex;
  margin-left: 4rem;
  align-items: center;
  border-radius: 5px;
  width: 100%;
  height: 8rem;
  gap: 3rem;
}
.user-info-box{
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
  justify-content: center;
}
.title{
  font-size: 60px;
  font-weight: bold;
  text-align: center;
}
.title-wrapper{
  display: flex;
  align-items: center;
  justify-content: center;
}

</style>
