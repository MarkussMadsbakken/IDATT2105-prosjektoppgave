<script setup lang="ts">
import { inject, ref } from 'vue';
import TextInput from '../TextInput.vue';
import Button from '../Button.vue';
import { CategoryIcons } from '@/util/categoryIcons';
import Select from 'primevue/select';
import { createCategory, createSubCategory } from '@/actions/categories';
import LoadingSpinner from '../LoadingSpinner.vue';
import FormGroup from '../FormGroup.vue';
import { useMutation } from '@tanstack/vue-query';

interface DialogRef {
    value: {
        data: {
            categoryId: number;
        },
    },
    close: () => void;
}

const dialogRef = inject<DialogRef>("dialogRef");
const categoryName = ref("");
const description = ref("");

const emit = defineEmits(["subCategoryCreated"]);

const { mutate: createSubCategoryMutation, isError, error, isPending } = useMutation({
    mutationFn: createSubCategory,
    onSuccess: () => {
        emit("subCategoryCreated")
    }
});

const notFilledInFields = ref<string[]>([]);

const onSubmit = () => {
    notFilledInFields.value = [];
    if (!categoryName.value) {
        notFilledInFields.value.push("categoryName");
    }

    if (notFilledInFields.value.length > 0) {
        return;
    }

    createSubCategoryMutation({
        name: categoryName.value,
        description: description.value,
        parentId: dialogRef?.value.data.categoryId!
    })
}

</script>
<template>
    <div class="outer-wrapper">
        <div class="listing-form">
            <FormGroup name="categoryName" :label="$t('name')"
                :isNotFilledIn="notFilledInFields.includes('categoryName')">
                <TextInput v-model="categoryName" type="text" id="categoryName" name="categoryName"
                    autocomplete="off" />
            </FormGroup>
            <FormGroup name="description" :label="$t('description')" :isNotFilledIn="false">
                <TextInput id="description" name="description" v-model="description" type="text" autocomplete="off" />
            </FormGroup>
            <Button label="Submit" variant="primary" @click="onSubmit">
                <template v-if="isPending">
                    <LoadingSpinner />
                </template>
                <template v-else>
                    {{ $t('create') }}
                </template>
            </Button>
        </div>
    </div>
</template>

<style scoped>
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
    width: 30rem;
}

.outer-wrapper {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-top: 1rem;
}
</style>