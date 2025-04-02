<script setup lang="ts">
import ListingCard from "@/components/ListingCard.vue";
import type { Category, Listing, User } from "@/types";
import { useRouter } from "vue-router";
import SearchOptions from "@/components/SearchOptions.vue";
import Divider from "@/components/Divider.vue";

const Categories: Category[] = [
  {
    icon: "armchair",
    name: "interior",
    id: 1,
  },
  {
    icon: "monitorSmartphone",
    name: "electronics",
    id: 2,
  },
  {
    icon: "washingMachine",
    name: "appliances",
    id: 3,
  },
  {
    icon: "mountainSnow",
    name: "leisure",
    id: 4,
  },
  {
    icon: "volleyball",
    name: "sports",
    id: 5,
  },
  {
    icon: "shirt",
    name: "clothing",
    id: 6,
  },
  {
    icon: "car",
    name: "transport",
    id: 7,
  },
  {
    icon: "shrub",
    name: "garden",
    id: 8,
  }
]

const listings: Listing[] = [
  {
    id: "1",
    title: "kult kjøleskap",
    description: "Veldig kult kjøleskap jeg fant! Bare å komme med et tilbud, jeg hadde satt stor pris på det. I tillegg skal jeg bare si noe langt her slik at teksten overflower!!",
    price: 6000,
    seller: {} as User,
  },
  {
    id: "2",
    title: "sjarmerende sofa",
    description: "En utrolig komfortabel og sjarmerende sofa som passer perfekt i stuen din.",
    price: 3500,
    seller: {} as User,
  },
  {
    id: "3",
    title: "elegant lampe",
    description: "En elegant lampe som sprer et varmt lys og skaper en koselig atmosfære.",
    price: 1500,
    seller: {} as User,
  },
]

const router = useRouter();

const handleSearch = (searchQuery: string) => {
  router.push({
    path: "/search",
    query: { q: searchQuery }
  });
};

const handleCategoryClick = (newCategory: string) => {
  router.push({
    path: "/search",
    query: { category: newCategory }
  })
}

</script>

<template>
  <div class="page-wrapper">
    <SearchOptions :categories="Categories" @search="handleSearch" @select-category="handleCategoryClick"
      :open="true" />
    <Divider />
    <div class="header-title">
      {{ $t('recommended') }}
    </div>
    <div class="recommended-listings">
      <div v-for="listing in listings">
        <ListingCard :listing="listing" />
      </div>
    </div>
  </div>
</template>

<style scoped>
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