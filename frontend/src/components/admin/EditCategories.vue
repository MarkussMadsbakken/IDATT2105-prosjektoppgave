<script setup lang="ts">
import { computed, ref } from 'vue';
import Button from '../Button.vue';
import { deleteCategory, useCategories } from '@/actions/categories';
import CategoryCard from '../CategoryCard.vue';
import { useDialog } from 'primevue/usedialog';
import CreateCategory from './CreateCategory.vue';
import ConfirmDialog from '../ConfirmDialog.vue';
import { useMutation, useQueryClient } from '@tanstack/vue-query';
import type { Category } from '@/types';
import EditCategoryModal from './EditCategoryModal.vue';
import { useI18n } from 'vue-i18n';

const { data, isError, error, isPending, } = useCategories();

const { t } = useI18n();

const categories = computed(() => {
    if (!data.value) { return [] };
    console.log(data.value);
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
            header: t('listings.category.createNewCategory'),
        },
        emits: {
            onCategoryCreated: () => {
                queryClient.invalidateQueries({
                    queryKey: ['categories']
                });
                d.close();
            }
        }
    })
}

const openEditCategoryModal = (category: Category) => {
    let d = dialog.open(EditCategoryModal, {
        props: {
            modal: true,
            dismissableMask: true,
            draggable: false,
            header: `${t('listings.create.edit')} '${category.name}'`,

},
        data: {
            category: category,
        },
        emits: {
            onCategoryModified: () => {
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
            header: t('listings.category.deleteCategory'),
            modal: true,
            draggable: false,
            dismissableMask: true,
        },
        data: {
            message: t("listings.category.areYouSureYouWantToDelete"),
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
    <div class="edit-categories-outer-wrapper">
        <div v-if="isPending">Loading...</div>
        <div v-else-if="isError">Error: {{ error?.message }}</div>
        <div class="edit-categories-content" v-else>
            <div class="categories">
                <div v-for="category in categories" :key="category.id + category.icon" v-if="categories.length > 0"
                    class="category-inner-wrapper">
                    <CategoryCard :icon="category.icon" @click="openEditCategoryModal(category)">
                        {{ category.name }}
                    </CategoryCard>
                    <Button @click="handleDeleteCategory(category.id)" variant="destructive"
                        class="delete-category-button">
                        {{ $t("listings.delete") }}
                    </Button>
                </div>
                <div v-else class="no-categories">
                  {{ $t('listings.create.noCategoriesFound') }}
                </div>
            </div>
            <Button class="create-category-button" @click="openCreateCategoryModal">
              {{ $t("listings.category.createNewCategory") }}
            </Button>
        </div>
    </div>

</template>

<style scoped>
.delete-category-button {
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

.edit-categories-outer-wrapper {
    position: relative;
    width: 100%;
    padding: 1rem;
    display: flex;
    flex-direction: column;
    justify-content: center;
}
</style>
