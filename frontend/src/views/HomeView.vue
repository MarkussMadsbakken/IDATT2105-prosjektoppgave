<script setup lang="ts">
import ListingCard from "@/components/ListingCard.vue";
import CategoryCard from "@/components/CategoryCard.vue";
import type { Listing } from "@/types";
import type { CategoryIcons } from "@/util/CategoryIcons";

const Categories: { name: string; icon: keyof typeof CategoryIcons }[] = [
  {
    icon: "armchair",
    name: "interior"
  },
  {
    icon: "monitorSmartphone",
    name: "electronics"
  },
  {
    icon: "washingMachine",
    name: "appliances"
  },
  {
    icon: "mountainSnow",
    name: "leisure"
  },
  {
    icon: "volleyball",
    name: "sports"
  },
  {
    icon: "shirt",
    name: "clothing"
  },
  {
    icon: "car",
    name: "transport"
  },
  {
    icon: "shrub",
    name: "garden"
  }
]

const listings: Listing[] = [
  {
    id: "1",
    title: "kult kjøleskap",
    description: "Veldig kult kjøleskap jeg fant! Bare å komme med et tilbud, jeg hadde satt stor pris på det. I tillegg skal jeg bare si noe langt her slik at teksten overflower!!",
    price: 6000,
    user: {
      userid: "1",
      username: "1",
    }
  },
  {
    id: "2",
    title: "sjarmerende sofa",
    description: "En utrolig komfortabel og sjarmerende sofa som passer perfekt i stuen din.",
    price: 3500,
    user: {
      userid: "2",
      username: "bruker2",
    }
  },
  {
    id: "3",
    title: "elegant lampe",
    description: "En elegant lampe som sprer et varmt lys og skaper en koselig atmosfære.",
    price: 1500,
    user: {
      userid: "3",
      username: "bruker3",
    }
  },
]

</script>

<template>
  <div class="page-wrapper">
    <div class="categories">
      <div v-for="category in Categories">
        <CategoryCard :icon="category.icon" :categoryname="category.name">
          {{ $t(category.name) }}
        </CategoryCard>
      </div>
    </div>
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