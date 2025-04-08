<script setup lang="ts">

import { useGetUserBookmarks } from "@/actions/user.ts";
import ListingCard from "@/components/ListingCard.vue";
import {useAuth} from "@/stores/auth.ts";


const {data: favoriteListings, isPending: isBookmarkPending, isError: isBookmarkError, error: bookmarkError} = useGetUserBookmarks();

</script>
<template>

  <div class="title-wrapper">
    <div class="title">{{ $t("myFavorites") }}: </div>
  </div>
  <div v-if="isBookmarkPending">Laster oppføringer...</div>
  <div v-else-if="isBookmarkError">{{ $t("couldNotLoadListings") }}</div>
  <div v-if="favoriteListings && favoriteListings.length > 0" class="listing-grid-favorites">
    <ListingCard
      v-for="listing in favoriteListings!"
      :key="listing.uuid!"
      :listing="listing"
      size="medium"
    />
  </div>
  <div v-else class="no-listings">
    {{ $t("emptyListings") }}
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
  padding-top: 2rem;
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;
  justify-content: center;
  max-width: calc(3*25rem + 2*2rem);
  margin: 0 auto;
}
.no-listings{
  font-size: 2rem;
  font-weight: bold;
  text-align: center;
}
</style>
