<script setup lang="ts">
import { ref } from 'vue';
import TextInput from '../TextInput.vue';
import Button from '../Button.vue';
import { CategoryIcons } from '@/util/categoryIcons';
import Select from 'primevue/select';
import { createCategory } from '@/actions/categories';
import LoadingSpinner from '../LoadingSpinner.vue';
import FormGroup from '../FormGroup.vue';
import { useMutation } from '@tanstack/vue-query';

const categoryName = ref("");
const description = ref("");
const icon = ref<keyof typeof CategoryIcons>();

const emit = defineEmits(["categoryCreated"]);

const { mutate: createCategoryMutation, isError, error, isPending } = useMutation({
    mutationFn: createCategory,
    onSuccess: () => {
        emit("categoryCreated")
    }
});

const notFilledInFields = ref<string[]>([]);

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

    createCategoryMutation({
        name: categoryName.value,
        description: description.value,
        icon: icon.value!
    })
}

</script>
<template>
    <div class="outer-wrapper">
        <div class="listing-form">
            <FormGroup name="categoryName" :label="$t('admin.name')"
                :isNotFilledIn="notFilledInFields.includes('categoryName')">
                <TextInput v-model="categoryName" type="text" id="categoryName" name="categoryName"
                    autocomplete="off" />
            </FormGroup>
            <FormGroup name="icon" :label="$t('icon')" :isNotFilledIn="notFilledInFields.includes('icon')">
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
            <Button label="Submit" variant="primary" @click="onSubmit">
                <template v-if="isPending">
                    <LoadingSpinner />
                </template>
                <template v-else>
                    {{ $t('form.create') }}
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