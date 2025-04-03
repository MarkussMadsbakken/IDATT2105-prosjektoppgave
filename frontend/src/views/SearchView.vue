<script setup lang="ts">
import { useRoute, useRouter } from "vue-router";
import SearchOptions from "@/components/SearchOptions.vue";
import type { Category } from "@/types";


const router = useRouter();
const route = useRoute();

const handleToggleSubCategory = (id: number) => {
    const subCategoryIds = Array.isArray(route.query.subCategoryId) ? route.query.subCategoryId : (route.query.subCategoryId ? [route.query.subCategoryId] : []);
    const isRemoving = subCategoryIds.includes(id.toString());

    router.push({
        query: {
            q: route.query.q,
            category: route.query.category,
            subCategoryId: isRemoving ? subCategoryIds.filter((subCategoryId) => subCategoryId !== id.toString()) : [...subCategoryIds, id.toString()],
            priceRange: route.query.priceRange
        }
    })
}

</script>

<template>
    <div class="page-wrapper">
        <SearchOptions :search-value="typeof $route?.query?.q === 'string' ? $route.query.q : undefined"
            :selected-category="typeof $route?.query?.category === 'string' ? $route.query.category : undefined"
            :selected-price-range="typeof $route?.query?.priceRange === 'string' ? ($route.query.priceRange.split(',').map(n => parseFloat(n)) as [number, number]) : undefined"
            :selected-sub-categories="Array.isArray($route?.query?.subCategoryId) ? $route.query.subCategoryId.filter(item => typeof item === 'string') : (typeof $route?.query?.subCategoryId === 'string' ? [$route.query.subCategoryId] : [])"
            @selectCategory="(category) => router.push({ query: { category: category, q: $route.query.q } })"
            @search="(searchValue) => router.push({ query: { category: $route.query.category, q: searchValue } })"
            @toggle-sub-category="(id) => handleToggleSubCategory(id)"
            @newPriceRange="(priceRange) => router.push({ query: { category: $route.query.category, q: $route.query.q, priceRange: priceRange, subCategoryId: $route.query.subCategoryId } })"
            show-advanced-search />
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