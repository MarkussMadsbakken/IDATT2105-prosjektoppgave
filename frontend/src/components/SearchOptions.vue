<script setup lang="ts">
import Searchbar from './Searchbar.vue';
import CategoryCard from './CategoryCard.vue';
import Collapsible from './Collapsible.vue';
import { useCategories } from '@/actions/categories';
import AdvancedSearch from './AdvancedSearch.vue';
import { computed } from 'vue';
import { Map } from 'lucide-vue-next';
import { useRouter } from 'vue-router';
import CategoryCardSkeleton from './skeleton/CategoryCardSkeleton.vue';

const props = withDefaults(defineProps<{
  selectedCategory?: number;
  searchValue?: string;
  open?: boolean;
  selectedSubCategories?: string[];
  showAdvancedSearch?: boolean;
  selectedPriceRange?: [number, number];
}>(), {
  open: false,
  showAdvancedSearch: false,
});


defineEmits<{
  (e: "search", value: string): void
  (e: "selectCategory", index: number): void
  (e: "newSearchValue", value: string): void
  (e: "toggleSubCategory", value: number): void
  (e: "newPriceRange", value: [number, number]): void
}>();

const router = useRouter();
const { data: categories, isError, error, isPending } = useCategories();

const selectedCategoryId = computed(() => {
  return categories.value?.find(c => c.id === props.selectedCategory)?.id ?? -1;
});


</script>

<template>
  <div class="search-outer-wrapper">
    <div class="search">
      <Searchbar :placeholder="$t('search.search')" @input="$emit('newSearchValue', $event)"
        @search="$emit('search', $event)" :value="props.searchValue" />
    </div>
    <div class="map-icon-wrapper" @click="router.push('/search/map')">
      <Map class="search-icon" :size="24"></Map>
    </div>
  </div>
  <Collapsible :openTitle="$t('search.showCategories')" :closedTitle="$t('search.hideCategories')" :open="props.open">
    <div class="categories">
      <CategoryCardSkeleton v-for="i in 8" :key="i" v-if="isPending" />
      <div v-for="category in categories" :key="category.name" v-else-if="categories">
        <CategoryCard :icon="category.icon" :categoryname="category.name"
          :selected="category.id === props.selectedCategory" @click="$emit('selectCategory', category.id)">
          {{ category.name }}
        </CategoryCard>
      </div>
    </div>
  </Collapsible>
  <AdvancedSearch v-if="props.showAdvancedSearch" :key="selectedCategoryId" :selectedCategoryId="selectedCategoryId"
    :showLoadingState="isPending"
    :selectedSubcategories="props.selectedSubCategories ? props.selectedSubCategories.map(Number) : undefined"
    @subCategoryChanged="$emit('toggleSubCategory', $event)" @priceRangeChanged="$emit('newPriceRange', $event)"
    :allowedSearchRange="[0, 10000]" :selectedPriceRange="props.selectedPriceRange" />
</template>


<style scoped>
.search-outer-wrapper {
  position: relative;
}

.map-icon-wrapper {
  position: absolute;
  top: 0;
  right: -3rem;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 3rem;
  height: 3rem;
  cursor: pointer;
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
}

@media only screen and (max-width: 580px) {
  .search {
    width: 100%;
    flex: 1;
  }

  .search-outer-wrapper {
    width: 100%;
    position: static;
  }

  .map-icon-wrapper {
    position: static;
    right: 0;
    top: 0;
    margin-left: auto;
    margin-right: auto;
  }

  .categories {
    grid-template-columns: repeat(1, minmax(0, 1fr));
    gap: 1rem;
    justify-content: center;
    justify-items: center;
  }
}


@media only screen and (min-width: 1200px) {
  .categories {
    grid-template-columns: repeat(4, minmax(0, 1fr));
  }

}
</style>
