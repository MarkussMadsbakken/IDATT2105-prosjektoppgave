<script setup lang="ts">
import { computed } from 'vue';
import Button from '../Button.vue';
import { useCategories } from '@/actions/categories';
import CategoryCard from '../CategoryCard.vue';
import { useDialog } from 'primevue/usedialog';
import CreateCategory from '../admin/CreateCategory.vue';

const { data, isError, error, isPending, } = useCategories();

const categories = computed(() => {
    if (!data.value) { return [] };
    return data.value;
});

const dialog = useDialog();

</script>

<template>
    <div class="outer-wrapper">
        <div v-if="isPending">Loading...</div>
        <div v-else-if="isError">Error: {{ error?.message }}</div>
        <div class="edit-categories-content" v-else>
            <div class="categories">
                <div v-for="category in categories" v-if="categories.length > 0">
                    <CategoryCard :icon="category.icon" :categoryname="category.name" />
                </div>
                <div v-else class="no-categories">
                    No categories found
                </div>
            </div>
            <Button class="createCategoryButton" @click="dialog.open(CreateCategory, {
                props: {
                    modal: true,
                    dismissableMask: true,
                    draggable: false,
                    header: 'Create a new category',
                }
            })"> Create a new category
            </Button>
        </div>
    </div>

</template>

<style scoped>
.createCategoryButton {
    font-size: medium;
    font-weight: 500;
    width: 15rem;
}

.no-categories {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    height: 100%;
    padding: 1rem;
    gap: 0.5rem;
}

.edit-categories-content {
    display: flex;
    position: relative;
    flex-direction: column;
    align-items: center;
}

.outer-wrapper {
    position: relative;
    width: 100%;
    height: 100%;
    height: fit-content;
    padding: 1rem;
    display: flex;
    flex-direction: column;
    justify-content: center;
}
</style>