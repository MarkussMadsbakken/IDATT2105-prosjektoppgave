<script setup lang="ts">

import { useGetUserBookmarks } from "@/actions/user.ts";
import ListingCard from "@/components/ListingCard.vue";
import ListingCardSkeleton from "@/components/skeleton/ListingCardSkeleton.vue";
import Breadcrumb from "primevue/breadcrumb";

const { data: favoriteListings, isPending: isBookmarkPending, isError: isBookmarkError, error: bookmarkError } = useGetUserBookmarks();

</script>
<template>
  <div class="favorites-header">
    <div class="title-wrapper">
      <div class="title">{{ $t("profile.myFavorites") }}: </div>
    </div>
    <div class="breadcrumb">
      <Breadcrumb
        :model="[{ label: $t('breadcrumb.profile'), url: '/profile' }, { label: $t('breadcrumb.myFavorites') }]" />
    </div>
  </div>
  <div v-if="isBookmarkPending" class="listing-grid-favorites">
    <ListingCardSkeleton :size="'medium'" v-for="i in 3" :key="i" />
  </div>
  <div v-else-if="isBookmarkError">{{ $t("couldNotLoadListings") }}</div>
  <div v-else-if="favoriteListings && favoriteListings.length > 0" class="listing-grid-favorites">
    <ListingCard v-for="listing in favoriteListings!" :key="listing.uuid!" :listing="listing" size="medium" />
  </div>
  <div v-else class="no-listings">
    {{ $t("emptyListings") }}
  </div>
</template>

<style scoped>
.favorites-header {
  position: relative;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  padding: 2rem;
  padding-bottom: 0.1rem;
  width: 100%;
}

.breadcrumb {
  position: absolute;
  left: 2rem;
}

@media (max-width: 700px) {
  .favorites-header {
    flex-direction: column-reverse;
    gap: 0.5rem;
  }

  .breadcrumb {
    position: static;
    width: fit-content;
  }

}

.breadcrumb>* {
  background-color: var(--color-background);
}

.title {
  font-size: 2rem;
  font-weight: bold;
}

.title-wrapper {
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

.no-listings {
  font-size: 2rem;
  font-weight: bold;
  text-align: center;
}
</style>
