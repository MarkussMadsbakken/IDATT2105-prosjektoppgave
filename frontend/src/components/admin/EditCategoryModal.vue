<script setup lang="ts">
import { inject, ref } from 'vue';
import TextInput from '../TextInput.vue';
import Button from '../Button.vue';
import { CategoryIcons } from '@/util/categoryIcons';
import Select from 'primevue/select';
import { createCategory, deleteCategory, deleteSubCategory, editCategory, useSubCategories } from '@/actions/categories';
import LoadingSpinner from '../LoadingSpinner.vue';
import FormGroup from '../FormGroup.vue';
import { useMutation, useQueryClient } from '@tanstack/vue-query';
import type { Category } from '@/types';
import Divider from '../Divider.vue';
import CreateSubCategoryModal from './CreateSubCategoryModal.vue';
import { useDialog } from 'primevue/usedialog';
import { useI18n } from 'vue-i18n';

interface DialogRef {
    value: {
        data: {
            category: Category;
        },
    },
    close: () => void;
}

const { t } = useI18n();
const dialogRef = inject<DialogRef>("dialogRef");
const categoryName = ref(dialogRef?.value.data.category.name ?? "");
const description = ref(dialogRef?.value.data.category.description ?? "");
const icon = ref<keyof typeof CategoryIcons>(dialogRef?.value.data.category.icon as keyof typeof CategoryIcons);

const emit = defineEmits(["categoryModified"]);

const { mutate: editCategoryMutation, isError, error, isPending } = useMutation({
    mutationFn: editCategory,
    onSuccess: () => {
        emit("categoryModified")
    }
});

const { data: subCategories, isError: subCategoriesError, error: subCategoriesErrorMessage, isPending: subCategoriesIsPending } = useSubCategories(dialogRef?.value.data.category.id!);

const notFilledInFields = ref<string[]>([]);
const queryClient = useQueryClient();
const dialog = useDialog();

const onSubmit = () => {
    notFilledInFields.value = [];

    if (!icon.value) {
        notFilledInFields.value.push("icon");
    }
    if (!categoryName.value) {
        notFilledInFields.value.push("categoryName");
    }

    if (notFilledInFields.value.length > 0) {
        return;
    }

    editCategoryMutation({
        name: categoryName.value,
        description: description.value,
        icon: icon.value!,
        id: dialogRef?.value.data.category.id!
    });
}

const handleCreateSubCategory = () => {
    const d = dialog.open(CreateSubCategoryModal, {
        props: {
            modal: true,
            dismissableMask: true,
            draggable: false,
            header: t("admin.createNewSubCategory"),
        },
        data: {
            categoryId: dialogRef?.value.data.category.id!
        },
        emits: {
            onSubCategoryCreated: () => {
                queryClient.invalidateQueries({
                    queryKey: ['categories']
                });
                d.close();
            }
        }
    })
}

const deletingSubCategoryId = ref<number | null>(null);

const { mutate: deleteSubCategoryMutation, isPending: deleteSubCategoryMutationIsPending } = useMutation({
    mutationFn: deleteSubCategory,
    onSuccess: () => {
        queryClient.invalidateQueries({
            queryKey: ['categories']
        });
    },
    onError: (error) => {
        console.error(error);
    }
})



</script>
<template>
    <div class="outer-wrapper">
        <div class="listing-form">
            <FormGroup name="categoryName" :label="$t('admin.name')"
                :isNotFilledIn="notFilledInFields.includes('categoryName')">
                <TextInput v-model="categoryName" type="text" id="categoryName" name="categoryName"
                    autocomplete="off" />
            </FormGroup>
            <FormGroup name="icon" :label="$t('admin.icon')" :isNotFilledIn="notFilledInFields.includes('icon')">
                <Select v-model="icon" id="icon" name="icon" :options="Object.keys(CategoryIcons)">
                    <template #value="option">
                        <div v-if="option.value" class="icon-option">
                            <component :is="CategoryIcons[option.value as keyof typeof CategoryIcons]" />
                            {{ option.value }}
                        </div>
                        <div v-else>{{ $t('form.choose') }}</div>
                    </template>
                    <template #option="option">
                        <div class="icon-option">
                            <component :is="CategoryIcons[option.option as keyof typeof CategoryIcons]" />
                            {{ option.option }}
                        </div>
                    </template>
                </Select>
            </FormGroup>
            <FormGroup name="description" :label="$t('admin.description')" :isNotFilledIn="false">
                <TextInput id="description" name="description" v-model="description" type="text" />
            </FormGroup>

            <div class="sub-categories">
                <Divider />
                <h3>{{ $t('admin.subCategories') }}</h3>
                <div v-if="subCategoriesIsPending">
                    <LoadingSpinner />
                </div>
                <div v-else-if="subCategoriesError">
                    <p>Error: {{ subCategoriesErrorMessage?.message }}</p>
                </div>
                <div class="sub-category-wrapper" v-for="subCategory in subCategories" :key="subCategory.id"
                    v-else-if="subCategories?.length! > 0">
                    <div class="sub-category-name">{{ subCategory.name }}</div>
                    <Button
                        @click="() => { deleteSubCategoryMutation(subCategory.id); deletingSubCategoryId = subCategory.id }"
                        variant="destructive" class="delete-sub-category-button">
                        <template v-if="deleteSubCategoryMutationIsPending && deletingSubCategoryId === subCategory.id">
                            <LoadingSpinner />
                        </template>
                        <template v-else>
                            {{ $t("delete") }}
                        </template>
                    </Button>
                </div>
                <div v-else>
                    <p class="no-sub-categories-found">{{ $t('admin.noSubCategoriesFound') }}</p>
                </div>
                <Button class="create-sub-category-button" @click="handleCreateSubCategory">
                    {{ $t('admin.createNewSubCategory') }}
                </Button>
            </div>

            <div v-if="isError">
                <p>Error: {{ error?.message }}</p>
            </div>
            <div class="edit-category-actions">
                <Button label="Cancel" variant="outline" @click="dialogRef?.close()">
                    {{ $t('cancel') }}
                </Button>
                <Button label="Submit" variant="primary" @click="onSubmit">
                    <template v-if="isPending">
                        <LoadingSpinner />
                    </template>
                    <template v-else>
                        {{ $t('form.save') }}
                    </template>
                </Button>
            </div>
        </div>
    </div>
</template>

<style scoped>
.sub-category-name {
    width: 8rem;
    font-weight: 400;
    font-size: 1rem;
    text-align: center;
    border: 1px solid var(--text-color);
}

.delete-sub-category-button {
    width: 5rem;
}

.sub-category-wrapper {
    display: flex;
    gap: 1rem;
    align-items: center;
    justify-content: center;
}


.no-sub-categories-found {
    font-weight: 400;
}

.create-sub-category-button {
    width: 20rem;
}

.sub-categories {
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 0.5rem;
    margin-top: 1rem;
}

.edit-category-actions {
    display: flex;
    gap: 1rem;
    justify-content: center;
    margin-top: 1rem;
}

.icon-option {
    display: flex;
    gap: 0.5rem;
    align-items: center;
}


.listing-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 1rem;
    margin-top: 1rem;
    width: 50rem;
}

.outer-wrapper {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-top: 1rem;
}
</style>