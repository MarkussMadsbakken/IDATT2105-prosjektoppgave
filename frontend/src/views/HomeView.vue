<script setup lang="ts">
import ListingCard from "@/components/ListingCard.vue";
import { API_BASE_URL, WS_BASE_URL, type Category, type GetListingsResponse, type Listing, type Page, type User } from "@/types";
import { useRouter } from "vue-router";
import SearchOptions from "@/components/SearchOptions.vue";
import Divider from "@/components/Divider.vue";
import { useGetListings, useGetRecommendedListings } from "@/actions/getListing";
import { computed, ref, watch } from "vue";
import { useInfiniteScroll } from "@vueuse/core";
import LoadingSpinner from "@/components/LoadingSpinner.vue";
import Button from "@/components/Button.vue";
import { Client } from "@stomp/stompjs"
import { useAuth } from "@/stores/auth";
import ListingCardSkeleton from "@/components/skeleton/ListingCardSkeleton.vue";

const auth = useAuth();

const {
  data: listings,
  isError,
  error,
  isPending,
  hasNextPage,
  isFetching,
  fetchNextPage,
} = auth.isLoggedIn() ? useGetRecommendedListings() : useGetListings();

const router = useRouter();

const handleSearch = (searchQuery: string) => {
  router.push({
    path: "/search",
    query: { q: searchQuery }
  });
};

const handleCategoryClick = (newCategory: number) => {
  router.push({
    path: "/search",
    query: { category: newCategory }
  })
}

const scrollWrapper = ref<HTMLElement | null>(null);
useInfiniteScroll(
  scrollWrapper,
  () => {
    fetchNextPage();
  },
  {
    canLoadMore: () => { return hasNextPage.value && !isFetching.value },
    distance: 10,
  }
);

</script>

<template>
  <div class="page-wrapper">
    <SearchOptions @search="handleSearch" @select-category="handleCategoryClick" :open="true" />
    <Divider />
    <div class="header-title">
      <template v-if="auth.isLoggedIn()">
        {{ $t('home.recommended') }}
      </template>
      <template v-else>
        {{ $t('home.listings') }}
      </template>
    </div>
    <div class="recommended-listings">
      <template v-if="isPending">
        <ListingCardSkeleton :size="'medium'" v-for="i in 6" :key="i" />
      </template>
      <div v-else-if="!listings || listings.pages.length === 0" class="no-listings">
        {{ $t('home.noListings') }}
      </div>
      <div v-else-if="isError" class="error-message">
        {{ $t('home.errorLoadingListings') }}
      </div>
      <template v-else v-for="(page, index) in listings.pages">
        <div v-for="listing in page.content" :key="index + listing.uuid">
          <ListingCard :listing="listing" />
        </div>
      </template>
    </div>
    <div ref="scrollWrapper">
      <div v-if="isPending" class="loading-spinner">
        <LoadingSpinner />
      </div>
      <div v-else-if="!hasNextPage && !isError && listings?.pages[0].totalElements !== 0" class="no-more-listings">
        {{ $t('home.noMoreListings') }}
      </div>
    </div>
  </div>
</template>

<style scoped>
.no-listings {
  grid-column: 1 / -1;
  text-align: center;
}

.no-more-listings {
  font-size: 1rem;
  text-align: center;
  margin-top: 1rem;
  margin-bottom: 1rem;
  font-weight: 400;
}

.search {
  width: 63rem;
  display: flex;
  justify-content: center;
}

@media only screen and (max-width: 1200px) {
  .search {
    width: 31rem;
  }
}

.categories {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 1rem;
  justify-content: center;
  justify-items: center;
  margin-bottom: 5rem;
}

.header-title {
  font-size: x-large;
  font-weight: 600;
}

.recommended-listings {
  display: grid;
  grid-template-columns: repeat(1, minmax(0, 1fr));
  grid-template-columns: 3;
  width: 100%;
  justify-content: center;
  justify-items: center;
  gap: 2rem;
}

@media only screen and (min-width: 1000px) {
  .recommended-listings {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media only screen and (min-width: 1400px) {
  .recommended-listings {
    grid-template-columns: repeat(3, minmax(0, 1fr));
  }
}

@media only screen and (min-width: 1200px) {
  .categories {
    grid-template-columns: repeat(4, minmax(0, 1fr));
  }

}

.page-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem;
  gap: 1rem;
}
</style>
