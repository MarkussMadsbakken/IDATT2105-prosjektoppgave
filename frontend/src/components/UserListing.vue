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
    <div class="title">{{ user?.username }} sine artikler: </div>
  </div>
  <div v-if="isPending">Laster oppføringer...</div>
  <div v-else-if="isError">Kunne ikke hente oppføringer.</div>
  <div v-if="listings && listings.length > 0" class="listing-grid" >
    <ListingCard
      v-for="listing in listings!"
      :key="listing.uuid!"
      :listing="listing"
      size="medium"
    />
  </div>
  <div v-else class="no-listings">
    Her er det tomt.
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
