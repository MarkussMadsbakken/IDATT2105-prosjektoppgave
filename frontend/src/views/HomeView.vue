<script setup lang="ts">
import ListingCard from "@/components/ListingCard.vue";
import type { Listing } from "@/types";

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
    <div class="header-title">

    </div>
    <div class="recommended-listings">
      <div v-for="listing in listings">
        <ListingCard :listing="listing" />
      </div>
    </div>
  </div>
</template>

<style scoped>
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

.page-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem;
  gap: 5rem;
}
</style>