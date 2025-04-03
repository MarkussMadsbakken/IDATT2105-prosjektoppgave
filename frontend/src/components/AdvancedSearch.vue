<script setup lang="ts">
import { useSubCategories } from '@/actions/categories';
import LoadingSpinner from './LoadingSpinner.vue';
import Slider from 'primevue/slider';
import { computed, ref, watch } from 'vue';
import TextInput from './TextInput.vue';
import NumberInput from './NumberInput.vue';
import FormGroup from './FormGroup.vue';
import Checkbox from 'primevue/checkbox';
import { useDebounceFn } from '@vueuse/core';
import type { Category } from '@/types';
import SubCategorySelector from './SubCategorySelector.vue';

const props = withDefaults(defineProps<{
    selectedCategoryId: number
    selectedSubcategories?: number[]
    selectedPriceRange?: [number, number]
    allowedSearchRange?: [number, number]
    showLoadingState?: boolean
}>(), {
    selectedPriceRange: () => [0, 100],
    allowedSearchRange: () => [0, 100],
    showLoadingState: false,
});

const emit = defineEmits<{
    (e: "priceRangeChanged", value: [number, number]): void
    (e: "subCategoryChanged", value: number): void
}>();

const { data: subCategories, isError, error, isPending } = useSubCategories(props.selectedCategoryId);


const selectedRange = ref(props.selectedPriceRange);

// When a new price range is selected, send it after a 200ms debounce to reduce rerenders
const emitRangeDebounced = useDebounceFn(() => {
    emit("priceRangeChanged", selectedRange.value);
}, 200);

watch(selectedRange, () => {
    emitRangeDebounced();
}, { deep: true });

</script>

<template>
    <div class="advanced-search-outer-wrapper">
        <div v-if="props.showLoadingState" class="loading-spinner">
            <LoadingSpinner />
        </div>
        <div v-else class="advanced-search-inner-wrapper">
            <div class="advanced-search-title">
                {{ $t('advancedSearch') }}
            </div>
            <div class="price-selector-wrapper">
                <NumberInput v-model="selectedRange[0]" class="price-input" />
                <Slider class="price-selector" range v-model="selectedRange" :min="props.allowedSearchRange[0]"
                    :max="props.allowedSearchRange[1]" />
                <NumberInput v-model="selectedRange[1]" class="price-input" />
            </div>
            <SubCategorySelector :selected-category-id="props.selectedCategoryId"
                :selected-subcategories="props.selectedSubcategories"
                @subCategoryChanged="$emit('subCategoryChanged', $event)" />
        </div>
    </div>
</template>

<style scoped>
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

.price-input {
    width: 8rem;
}

.price-selector {
    width: 100%;
}

.price-selector-wrapper {
    width: 60%;
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 1rem;
}

.advanced-search-inner-wrapper {
    align-items: center;
    padding: 1rem;
    display: flex;
    flex-direction: column;
    gap: 1rem;
}

.loading-spinner {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
}

.advanced-search-outer-wrapper {
    width: 63rem;
    min-height: 10rem;
    border: 1px solid black;
    border-radius: 5px;
    display: flex;
    flex-direction: column;
    gap: 1rem;
}

@media only screen and (max-width: 1200px) {
    .advanced-search-outer-wrapper {
        width: 31rem;
    }

    .price-selector-wrapper {
        flex-direction: column;
    }


}
</style>