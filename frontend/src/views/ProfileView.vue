<script setup lang="ts">

import Button from '@/components/Button.vue';
import {logout, useAuth} from '@/stores/auth';
import { useRouter } from 'vue-router';
import ListingCard from "@/components/ListingCard.vue";

const router = useRouter();
const handleLogout = () => {
    logout();
    router.push('/login');
}

import type { Listing, User } from '@/types'
import {Settings} from "lucide-vue-next";
import UserImage from "@/components/UserImage.vue";

const users: User[] = [
  {id: 1, username:"Jackyboy", firstName: "Jacob", lastName:"Lein", createdAt:new Date('2002-08-12'), isAdmin: true},
  {id: 1, username:"MattiBattiboy", firstName: "Markus", lastName:"Madsbakken", createdAt:new Date('2004-01-13'), isAdmin: false}
]
const favoriteListings: Listing[] = [
  { id: "7", title: "minimalistisk bokhylle", seller: users[1], description: "Stilren bokhylle med fem hyller.", price: 1200 },
  { id: "8", title: "komfortabel lenestol", seller: users[1], description: "Perfekt for avslapning.", price: 2300 },
  { id: "9", title: "retro kaffebord", seller: users[1], description: "Unikt design fra 70-tallet.", price: 1600 },
  { id: "10", title: "industrilampe", seller: users[1], description: "Lampe i metall med røff stil.", price: 1100 }
]
const ownListings: Listing[] = [
  { id: "2", title: "sjarmerende sofa", seller:users[0], description: "Komfortabel sofa.", price: 3500 },
  { id: "3", title: "elegant lampe", seller: users[0], description: "Elegant lampe.", price: 1500 },
  { id: "4", title: "rustikk spisebord", seller: users[0], description: "Solid spisebord i heltre.", price: 4200 },
  { id: "5", title: "moderne kontorstol", seller: users[0], description: "Ergonomisk kontorstol med justerbar høyde.", price: 1900 },
  { id: "6", title: "klassisk kommode", seller: users[0], description: "Romslig kommode i klassisk design.", price: 2800 },
]

const createdAtText = "Medlem siden: "+ new Date(users[0].createdAt).getFullYear();
const auth = useAuth();

</script>

<template>
  <div class="page">
  <div class="user-box">
    <div class="user-info-box">
        <img v-if="users[0].imageUrl" :src="users[0].imageUrl" alt="Profilbilde"/>
        <UserImage v-else :size="120" stroke-width="1.5" />
        <h3 class="username">{{users[0].username}}</h3>
    </div>
    <div class="text-field">
        <div class="admin-field" v-if=users[0].isAdmin>Admin</div>
        <div class="member-since">{{createdAtText}}</div>
    </div>
    <div class="settings-container">
        <Settings class="settings-button" :size="35" @click="router.push('/profile/edit')"  :stroke-width="2.2"></Settings>
        <Button class="logout-button" variant="primary" @click="handleLogout">{{ $t("logout") }}</Button>
    </div>

  </div>
    <div class="custom-line"></div>
    <div class="ownListingContainer">
        <div class="title-wrapper">
          <div class="title"> Mine artikler </div>
          <RouterLink class="router-link" :to="`/profile/${auth.userId}/listings`">Vis alle</RouterLink>
        </div>
        <div class="ownListingGrid">
          <ListingCard v-for="listing in ownListings" :key="listing.id" :listing="listing" size="medium"/>
        </div>
    </div>
    <div class="custom-line"></div>
    <div class="title-wrapper">
      <div class="title"> Mine favoritter </div>
      <RouterLink class="router-link" to="/favorites">Vis alle</RouterLink>
    </div>
    <div class="favoriteListingsGrid">
      <ListingCard v-for="listing in favoriteListings" :key="listing.id" :listing="listing" size="medium"/>
    </div>

  </div>

</template>


<style scoped>
.user-box{
  margin-top: 4rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-radius: 5px;
  width: 40rem;
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
.page{
  display: flex;
  flex-direction: column;
  gap: 3rem;
  align-items: center;
  justify-content: center;
}
.username{
  font-size: 20px;
  font-weight: bold;
}
.text-field{
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 3rem;
}
.settings-button{
  cursor: pointer;
  display: block;

}
.settings-container{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 1rem;
}

.custom-line {
  width: 80%;
  height: 1px;
  background-color: #000;
}


.ownListingContainer{
  display:flex;
  flex-direction: column;
  align-items: center;

}
.favoriteListingsGrid{
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;
  justify-content: center;
  max-width: calc(3*25rem + 2*2rem);
  margin: 0 auto;
}

.favoriteListingsGrid > * {
  margin-top: 1rem;
  flex: 1 1 18rem;
  max-width: 25rem;
  box-sizing: border-box;
}

.ownListingGrid {
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;
  justify-content: center;
  max-width: calc(3*25rem + 2*2rem);
  margin: 0 auto;
}
.ownListingGrid > * {
  margin-top: 1rem;
  flex: 1 1 18rem;
  max-width: 25rem;
  box-sizing: border-box;
}
.logout-button{
  width: 7rem;
  height: 2rem;
  font-size: 14px;
  padding: 0.25rem 0.25rem;
}
.router-link{
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
  font-size: 25px;
  font-weight: bold;
  color: var(--color-primaryButton);
  text-decoration: none;
  cursor: pointer;
  margin-right: 3rem;
}
.title-wrapper{
  position: relative;
  width: 100%;
  max-width: calc(3*25rem + 2*2rem);
  text-align: center;
}
.title-wrapper .title{
  font-size: 50px;
  font-weight: bold;
}
</style>
