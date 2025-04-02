<script setup lang="ts">
import { ref } from 'vue';
import TextInput from '../TextInput.vue';
import Button from '../Button.vue';
import { CategoryIcons } from '@/util/categoryIcons';
import Select from 'primevue/select';
import { useCreateCategory } from '@/actions/categories';

const categoryName = ref("");
const description = ref("");
const icon = ref<keyof typeof CategoryIcons>();

const emit = defineEmits<(e: 'onCategoryCreated') => void>();

const { mutate: createCategory, isError, error, isPending } = useCreateCategory({
    onSuccess: () => {
        emit("onCategoryCreated")
    }
});

const onSubmit = () => {
    if (!icon.value) {
        return;
    }
    if (!categoryName.value) {
        return;
    }

    createCategory({
        name: categoryName.value,
        description: description.value,
        icon: icon.value
    })
}

</script>
<template>
    <div class="outer-wrapper">
        <div class="listing-form">
            <div class="form-group">
                <label for="categoryName">{{ $t('name') }}</label>
                <TextInput v-model="categoryName" type="text" id="categoryName" name="categoryName"
                    autocomplete="off" />
            </div>
            <div class="form-group">
                <label for="icon">{{ $t('icon') }}</label>
                <Select v-model="icon" id="icon" name="icon" :options="Object.keys(CategoryIcons)">
                    <template #value="option">
                        <div v-if="option.value" class="icon-option">
                            <component :is="CategoryIcons[option.value as keyof typeof CategoryIcons]" />
                            {{ option.value }}
                        </div>
                        <div v-else>{{ $t('choose') }}</div>
                    </template>
                    <template #option="option">
                        <div class="icon-option">
                            <component :is="CategoryIcons[option.option as keyof typeof CategoryIcons]" />
                            {{ option.option }}
                        </div>
                    </template>
                </Select>
            </div>
            <div class="form-group">
                <label for="description">{{ $t('description') }}</label>
                <TextInput id="description" name="description" v-model="description" type="text" />
            </div>
            <Button label="Submit" variant="primary" @click="onSubmit">
                {{ $t('create') }}
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

.form-group {
    display: flex;
    flex-direction: column;
    gap: 0.25rem;
    width: 100%;
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
