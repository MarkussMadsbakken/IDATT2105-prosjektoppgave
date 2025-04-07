<script setup lang="ts">
import { useRoute, useRouter } from "vue-router";
import SearchOptions from "@/components/SearchOptions.vue";
import { ref, watch } from "vue";
import { useSearchListings } from "@/actions/getListing";


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

const queryString = ref<string>();

const { data: searchResults, isError, error, isPending } = useSearchListings(queryString.value ?? "");

watch(route, () => {
    const params = new URLSearchParams();

    if (route.query.q) {
        params.append("q", route.query.q as string);
    }
    if (route.query.category) {
        params.append("category", route.query.category as string);
    }
    if (route.query.subCategoryId) {
        params.append("subCategoryId", route.query.subCategoryId as string);
    }
    if (route.query.priceRange) {
        const priceRange = Array.isArray(route.query.priceRange) ? route.query.priceRange : [route.query.priceRange];
        params.append("minPrice", priceRange[0] as string);
        params.append("maxPrice", priceRange[1] as string);
    }
    if (route.query.page) {
        params.append("page", route.query.page as string);
    }

});


// Extract pricerange from query
const priceRange: [number, number] = (() => {
    const pr = Array.isArray(route.query.priceRange)
        ? route.query.priceRange
        : (route.query.priceRange ? [route.query.priceRange] : []);
    if (pr.length === 2) {
        return [Number(pr[0]), Number(pr[1])];
    }
    return [0, 100];
})();

console.log(priceRange);

</script>

<template>
    <div class="page-wrapper">
        <SearchOptions :search-value="typeof $route?.query?.q === 'string' ? $route.query.q : undefined"
            :selected-category="typeof $route?.query?.category === 'string' ? $route.query.category : undefined"
            :selected-price-range="priceRange" :selected-sub-categories="Array.isArray($route?.query?.subCategoryId) ?
                $route.query.subCategoryId.filter(item => typeof item === 'string') : (typeof $route?.query?.subCategoryId
                    === 'string' ? [$route.query.subCategoryId] : [])"
            @selectCategory="(category) => router.push({ query: { category: category, q: $route.query.q } })" @search="(searchValue) => router.push({
                query: {
                    category: $route.query.category, q: searchValue,
                    priceRange: $route.query.priceRange, subCategoryId: $route.query.subCategoryId
                }
            })" @toggle-sub-category="(id) => handleToggleSubCategory(id)" @newPriceRange="(priceRange) => router.push({
                query: {
                    category: $route.query.category, q: $route.query.q,
                    priceRange: priceRange, subCategoryId: $route.query.subCategoryId
                }
            })" show-advanced-search />
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