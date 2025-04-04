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
    <div class="title">{{ user?.username }} sine artikler</div>
  </div>
  <div v-if="isPending">Laster oppføringer...</div>
  <div v-else-if="isError">Kunne ikke hente oppføringer.</div>
  <div class="listing-grid" v-else>
    <ListingCard
      v-for="listing in listings!"
      :key="listing.uuid!"
      :listing="listing"
      size="medium"
    />
  </div>
</template>

<style scoped>
.title{
  font-size: 60px;
  font-weight: bold;
}
.title-wrapper{
  margin-top: 3rem;
  text-align: center;
}
.listing-grid{
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(20rem, 1fr));
  gap: 2rem;
  max-width: 80rem;
  margin: 2rem auto;
  padding: 0 2rem;
  margin-bottom: 5rem;
}
</style>
