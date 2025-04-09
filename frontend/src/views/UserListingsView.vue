<script setup lang="ts">
import { useGetUserListings } from "@/actions/user.ts";
import ListingCard from "@/components/ListingCard.vue";
import { useGetUser } from "@/actions/user.ts";
import ListingCardSkeleton from "@/components/skeleton/ListingCardSkeleton.vue";
import { useRoute } from "vue-router";
import Breadcrumb from "primevue/breadcrumb";

const route = useRoute();

const { data: user } = useGetUser(Number(route.params?.userid));

</script>

<template>
  <div class="user-listings-page">
    <div class="listings-header">
      <div class="title-wrapper">
        <div class="title">{{ $t('profile.listingsByUser', { name: user?.username }) }} </div>
      </div>
      <div class="breadcrumb">
        <Breadcrumb
          :model="[{ label: $t('breadcrumb.profile'), url: '/profile' }, { label: $t('breadcrumb.myListings') }]" />
      </div>
    </div>
    <div class="tab-buttons">
      <RouterLink replace :to="`/profile/${route.params.userid}/listings`" class="tab-link"
        :class="{ active: $route.path === `/profile/${route.params.userid}/listings` }">
        {{ $t("listings.archive.activeListings") }}
      </RouterLink>
      <span class="divider">|</span>
      <RouterLink replace :to="`/profile/${route.params.userid}/listings/archived`" class="tab-link"
        :class="{ active: $route.path === `/profile/${route.params.userid}/listings/archived` }">
        {{ $t("profile.listings.archivedListings") }}
      </RouterLink>
    </div>
    <RouterView />
  </div>
</template>

<style scoped>
.listings-header {
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


.listing-grid {
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

.tab-buttons {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1rem;
  font-size: 1.8rem;
  margin-bottom: 2rem;
  padding-top: 2rem;
}

.tab-link {
  text-decoration: none;
  color: black;
  font-weight: 500;
  font-size: 1.5rem;
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
