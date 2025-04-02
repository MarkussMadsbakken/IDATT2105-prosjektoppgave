<script setup lang="ts">
import { computed, ref } from 'vue';
import Button from '../Button.vue';
import { deleteCategory, useCategories } from '@/actions/categories';
import CategoryCard from '../CategoryCard.vue';
import { useDialog } from 'primevue/usedialog';
import CreateCategory from './CreateCategory.vue';
import ConfirmDialog from '../ConfirmDialog.vue';
import { useMutation, useQueryClient } from '@tanstack/vue-query';

const { data, isError, error, isPending, } = useCategories();

const categories = computed(() => {
    if (!data.value) { return [] };
    return data.value;
});

const { mutate: deleteCategoryMutation } = useMutation({
    mutationFn: deleteCategory,
    onSuccess: () => {
        queryClient.invalidateQueries({
            queryKey: ['categories']
        });
    },
    onError: (error) => {
        console.error(error);
    }
});


const dialog = useDialog();
const queryClient = useQueryClient();

const openCreateCategoryModal = () => {
    let d = dialog.open(CreateCategory, {
        props: {
            modal: true,
            dismissableMask: true,
            draggable: false,
            header: 'Create a new category',
        },
        emits: {
            onCategoryCreated: () => {
                console.log('Category created');
                queryClient.invalidateQueries({
                    queryKey: ['categories']
                });
                d.close();
            }
        }
    })
}

const handleDeleteCategory = (id: number) => {
    const d = dialog.open(ConfirmDialog, {
        props: {
            header: 'Delete category',
            modal: true,
            draggable: false,
            dismissableMask: true,
        },
        data: {
            message: 'Are you sure you want to delete this category?',
            variant: 'Caution',
        },
        emits: {
            onAccept: () => {
                deleteCategoryMutation(id);
                d.close();
            }
        }
    })
}

</script>

<template>
    <div class="outer-wrapper">
        <div v-if="isPending">Loading...</div>
        <div v-else-if="isError">Error: {{ error?.message }}</div>
        <div class="edit-categories-content" v-else>
            <div class="categories">
                <div v-for="category in categories" v-if="categories.length > 0" class="category-inner-wrapper">
                    <CategoryCard :icon="category.icon">
                        {{ category.name }}
                    </CategoryCard>
                    <Button @click="handleDeleteCategory(category.id)" variant="destructive"
                        class="delete-category-button">
                        {{ $t("delete") }}
                    </Button>
                </div>
                <div v-else class="no-categories">
                    No categories found
                </div>
            </div>
            <Button class="create-category-button" @click="openCreateCategoryModal">
                Create a new category
            </Button>
        </div>
    </div>

</template>

<style scoped>
.delete-category-button {
    height: 4rem;
    width: 4rem;
    font-size: medium;
    font-weight: 500;
}

.category-inner-wrapper {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    gap: 1rem;
}

.categories {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
}

.create-category-button {
    font-size: medium;
    font-weight: 500;
    width: 20rem;
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
    gap: 1rem;
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