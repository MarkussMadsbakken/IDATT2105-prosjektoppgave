<script setup lang="ts">
  import {useAuth} from "@/stores/auth.ts";
  import { useGetUserListings } from "@/actions/user.ts";
  import ListingCard from "@/components/ListingCard.vue";
  import { useGetUser } from "@/actions/user.ts";


  const props = defineProps<{
    userId: number;
  }>();
  const {data: listings, isPending, isError, error} = useGetUserListings(props.userId);
  const { data: user } = useGetUser(props.userId);


</script>

<template>
  <div class="title-wrapper">
    <div class="title">{{ $t('profile.listingsByUser', {name: user?.username}) }} </div>
    <div class="tab-buttons">
      <router-link
        :to="`/profile/${userId}/listings`"
        class="tab-link"
        :class="{ active: $route.path === `/profile/${userId}/listings` }"
      >
        {{ $t("listings.archive.activeListings") }}
      </router-link>
      <span class="divider">|</span>
      <router-link
        :to="`/profile/${userId}/listings/archived`"
        class="tab-link"
        :class="{ active: $route.path === `/profile/${userId}/listings/archived` }"
      >
        {{ $t("listings.archive.activeListings") }}
      </router-link>
    </div>

  </div>
  <div v-if="isPending">Laster oppføringer...</div>
  <div v-else-if="isError">{{ $t("couldNotLoadListings") }}</div>
  <div v-if="listings && listings.length > 0" class="listing-grid" >
    <ListingCard
      v-for="listing in listings!"
      :key="listing.uuid!"
      :listing="listing"
      size="medium"
    />
  </div>
  <div v-else class="no-listings">
    {{ $t("profile.emptyListings") }}
  </div>
</template>

<style scoped>
.title{
  font-size: 4.5rem;
  font-weight: bold;
}
.title-wrapper{
  padding-top: 3rem;
  text-align: center;
}
.listing-grid {
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
.tab-buttons {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1rem;
  font-size: 1.8rem;
  margin-bottom: 2rem;
  padding-top: 1rem;
}

.tab-link {
  text-decoration: none;
  color: black;
  font-weight: 500;
  padding-bottom: 4px;
  transition: border-color 0.2s;
}

.tab-link.active {
  border-bottom: 2px solid black;
  font-weight: bold;
}

.divider {
  color: #666;
  font-size: 1.8rem;
}

</style>
