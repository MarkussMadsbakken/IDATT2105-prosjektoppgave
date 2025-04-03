<script setup lang="ts">
import Searchbar from './Searchbar.vue';
import CategoryCard from './CategoryCard.vue';
import Collapsible from './Collapsible.vue';
import { useCategories } from '@/actions/categories';


const props = withDefaults(defineProps<{
  selectedCategory?: string;
  searchValue?: string;
  open?: boolean;
}>(), {
  open: false,
});

defineEmits<{
  (e: "search", value: string): void
  (e: "selectCategory", index: string): void
  (e: "newSearchValue", value: string): void
}>();

const { data: categories, isError, error, isPending } = useCategories();

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
          :selected="category.name === props.selectedCategory" @click="$emit('selectCategory', category.name)">
          {{ $t(category.name) }}
        </CategoryCard>
      </div>
    </div>
  </Collapsible>
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