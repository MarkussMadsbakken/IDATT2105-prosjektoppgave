<script setup lang="ts">
import { useCategories } from '@/actions/categories';
import LoadingSpinner from './LoadingSpinner.vue';
import Select from 'primevue/select';
import { CategoryIcons } from '@/util/categoryIcons';
import { ref, watch } from 'vue';
import SubCategorySelector from './SubCategorySelector.vue';
import type { Category } from '@/types';
import { watchOnce } from '@vueuse/core';


const props = defineProps<{
    isError?: boolean
    name?: string
    initialCategory?: number
    initialSubCategories?: number[]
}>();

console.log(props.initialSubCategories);

const emit = defineEmits<{
    (e: 'categorySelected', category: number): void
    (e: 'subcategoriesUpdated', subcategories: number[]): void
}>();

const { data: categories, isPending, isError, error } = useCategories();
const selectedCategory = ref<Category | null>(props.initialCategory ? (categories.value?.find(c => c.id === props.initialCategory) ?? null) : null);
const selectedSubCategories = ref<number[]>([]);

watchOnce(categories, (categories) => {
    if (!props.initialCategory) return;
    const category = categories?.find(c => c.id === props.initialCategory);
    if (!category) return;
    selectedCategory.value = category;
    selectedSubCategories.value = props.initialSubCategories ?? [];
});

watch(selectedCategory, (newCategory) => {
    if (!newCategory) return;
    emit('categorySelected', newCategory.id);
});

const handleCategoryChange = () => {
    if (!selectedCategory.value) return;
    selectedSubCategories.value = [];
    emit('subcategoriesUpdated', selectedSubCategories.value);
}

const handleSubCategoryToggle = (id: number) => {
    const index = selectedSubCategories.value.indexOf(id);
    if (index === -1) {
        selectedSubCategories.value.push(id);
    } else {
        selectedSubCategories.value.splice(index, 1);
    }
    emit('subcategoriesUpdated', selectedSubCategories.value);
}

</script>

<template>
    <template v-if="isPending">
        <LoadingSpinner />
    </template>
    <template v-else-if="isError">
        <p>Feil: {{ error?.message }}</p>
    </template>
    <template v-else>
        <Select :options="categories" :inputId="props.name" v-model="selectedCategory"
            :placeholder="$t('selectCategory')" @value-change="handleCategoryChange">
            <template #option="{ option }">
                <div class="category-option">
                    <component :is="CategoryIcons[option.icon as keyof typeof CategoryIcons]" />
                    <span>{{ option.name }}</span>
                </div>
            </template>
            <template #value="option">
                <div class="category-option" v-if="option.value">
                    <component :is="CategoryIcons[option.value.icon as keyof typeof CategoryIcons]" />
                    <span>{{ option.value.name }}</span>
                </div>
            </template>
        </Select>
        <template v-if="selectedCategory">
            <p> {{ $t('subCategories') }}</p>
            <SubCategorySelector :selected-category-id="selectedCategory.id" :key="selectedCategory.id"
                @subCategoryChanged="handleSubCategoryToggle" />
        </template>
    </template>
</template>

<style scoped>
.category-option {
    display: flex;
    align-items: center;
    gap: 0.5rem;
}

.category-option>span {
    font-size: 1rem;
    font-weight: 400;
}
</style>
