<script setup lang="ts">

import { useGetUserBookmarks } from "@/actions/user.ts";
import ListingCard from "@/components/ListingCard.vue";
import {useAuth} from "@/stores/auth.ts";


const {data: favoriteListings, isPending: isBookmarkPending, isError: isBookmarkError, error: bookmarkError} = useGetUserBookmarks();

</script>
<template>

  <div class="title-wrapper">
    <div class="title">Mine favoritter: </div>
  </div>
  <div v-if="isBookmarkPending">Laster oppføringer...</div>
  <div v-else-if="isBookmarkError">Kunne ikke hente oppføringer.</div>
  <div v-if="favoriteListings && favoriteListings.length > 0" class="listing-grid-favorites">
    <ListingCard
      v-for="listing in favoriteListings!"
      :key="listing.uuid!"
      :listing="listing"
      size="medium"
    />
  </div>
  <div v-else class="no-listings">
    Her er det tomt...
  </div>
</template>

<style scoped>

.title{
  font-size: 4.5rem;
  font-weight: bold;
}
.title-wrapper{
  text-align: center;
}
.listing-grid-favorites {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 2rem;
  max-width: 80rem;
  margin: 2rem auto 5rem auto;
  padding: 0 2rem;
}
.no-listings{
  font-size: 2rem;
  font-weight: bold;
  text-align: center;
}
</style>
