<script setup lang="ts">
import { useRouter } from "vue-router";
import SearchOptions from "@/components/SearchOptions.vue";
import type { Category } from "@/types";

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

const router = useRouter();

</script>

<template>
    <div class="page-wrapper">
        <SearchOptions :categories="Categories"
            :search-value="typeof $route?.query?.q === 'string' ? $route.query.q : undefined"
            :selected-category="typeof $route?.query?.category === 'string' ? $route.query.category : undefined"
            @selectCategory="(category) => router.push({ query: { category: category, q: $route.query.q } })"
            @search="(searchValue) => router.push({ query: { category: $route.query.category, q: searchValue } })" />
    </div>
</template>


<style scoped>
.page-wrapper {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 2rem;
    gap: 1rem;
}
</style>