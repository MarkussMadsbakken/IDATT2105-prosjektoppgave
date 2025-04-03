<script setup lang="ts">
import { useSubCategories } from '@/actions/categories';
import type { SubCategory } from '@/types';
import Checkbox from 'primevue/checkbox';
import { ref, watch } from 'vue';
import LoadingSpinner from './LoadingSpinner.vue';

const emit = defineEmits<(e: "subCategoryChanged", value: number) => void>();

const props = defineProps<{
    selectedCategoryId: number;
    selectedSubcategories?: number[];
}>();

const { data: subCategories, isError, error, isPending } = useSubCategories(props.selectedCategoryId);


const handleSubCategoryChanged = (id: number) => {
    emit("subCategoryChanged", id);
}

const selectedCategories = ref<SubCategory[]>([]);

// When we get new data, check which subcategories are selected
watch(subCategories, (newData) => {
    if (!newData) return;
    selectedCategories.value = newData?.filter((subcategory) => {
        return props.selectedSubcategories?.includes(subcategory.id);
    }) ?? [];
}, { immediate: true });

</script>

<template>
    <template v-if="isPending">
        <LoadingSpinner />
    </template>
    <template v-else-if="isError">
        <p>Feil: {{ error?.message }}</p>
    </template>
    <template v-else-if="!subCategories?.length">
        <p class="no-sub-categories-text">{{ $t('noSubCategories') }}</p>
    </template>
    <div class="sub-category-selector" v-else>
        <div v-for="subcategory in subCategories" :key="subcategory.name" class="sub-category">
            <Checkbox @value-change="() => handleSubCategoryChanged(subcategory.id)" v-model="selectedCategories"
                :value="subcategory" :name="subcategory.name" :input-id="subcategory.name" />
            <label :for="subcategory.name"> {{ $t(subcategory.name) }} </label>
        </div>
    </div>
</template>

<style scoped>
.no-sub-categories-text {
    text-align: center;
    font-size: 1rem;
    font-weight: 400;
}

.sub-category {
    user-select: none;
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 0.5rem;
}

.sub-category>label {
    cursor: pointer;
}

.sub-category-selector {
    display: grid;
    grid-template-columns: repeat(2, minmax(0, 1fr));
    gap: 1rem;
    justify-content: center;
    justify-items: center;
}


@media only screen and (min-width: 1200px) {
    .sub-category-selector {
        grid-template-columns: repeat(4, minmax(0, 1fr));
    }

}
</style>