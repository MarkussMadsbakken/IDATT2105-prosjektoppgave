<script setup lang="ts">
import Searchbar from './Searchbar.vue';
import CategoryCard from './CategoryCard.vue';
import Collapsible from './Collapsible.vue';
import { useCategories, useSubCategories } from '@/actions/categories';
import AdvancedSearch from './AdvancedSearch.vue';
import { computed } from 'vue';


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

const { data: categories, isError, error, isPending } = useCategories();

const selectedCategoryId = computed(() => {
  return categories.value?.find(c => c.id === props.selectedCategory)?.id ?? -1;
});


</script>

<template>
  <div class="search">
    <Searchbar :placeholder="$t('search')" @input="$emit('newSearchValue', $event)" @search="$emit('search', $event)"
      :value="props.searchValue" />
  </div>
  <Collapsible :openTitle="$t('showCategories')" :closedTitle="$t('hideCategories')" :open="props.open">
    <div class="categories">
      <div v-for="category in categories" :key="category.name">
        <CategoryCard :icon="category.icon" :categoryname="category.name"
          :selected="category.id === props.selectedCategory" @click="$emit('selectCategory', category.id)">
          {{ $t(category.name) }}
        </CategoryCard>
      </div>
    </div>
  </Collapsible>
  <AdvancedSearch v-if="props.showAdvancedSearch" :key="selectedCategoryId" :selectedCategoryId="selectedCategoryId"
    :showLoadingState="isPending"
    :selectedSubcategories="props.selectedSubCategories ? props.selectedSubCategories.map(Number) : undefined"
    @subCategoryChanged="$emit('toggleSubCategory', $event)" @priceRangeChanged="$emit('newPriceRange', $event)"
    :allowedSearchRange="[0, 100]" :selectedPriceRange="props.selectedPriceRange" />
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
}


@media only screen and (min-width: 1200px) {
  .categories {
    grid-template-columns: repeat(4, minmax(0, 1fr));
  }

}
</style>