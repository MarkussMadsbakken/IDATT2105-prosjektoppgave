<script setup lang="ts">
import { createListing } from '@/actions/createListing';
import Button from '@/components/Button.vue';
import CategorySelector from '@/components/CategorySelector.vue';
import FormGroup from '@/components/FormGroup.vue';
import ImageSelector from '@/components/ImageSelector.vue';
import LoadingSpinner from '@/components/LoadingSpinner.vue';
import PhotoGallery from '@/components/PhotoGallery.vue';
import TextInput from '@/components/TextInput.vue';
import type { Listing } from '@/types';
import { useMutation } from '@tanstack/vue-query';
import { computed, ref } from 'vue';
import { useRouter } from 'vue-router';


const title = ref("");
const description = ref("");
const price = ref("");
const postalCode = ref("");
const category = ref<number | null>(null);
const subCategories = ref<number[] | null>(null);
const selectedImages = ref<File[]>([]);
let errors = ref<{
    field: string;
    isError: boolean;
}[]>([]);

const router = useRouter();

const onImagesSelected = (images: File[]) => {
    selectedImages.value = [...images, ...selectedImages.value];
}

const selectedImagesUrls = computed(() => {
    return selectedImages.value.map(image => URL.createObjectURL(image));
})

const { isPending, isError, error, mutate: createListingMutation } = useMutation({
    mutationFn: createListing,
    onSuccess: (data) => {
        router.push(`/listing/${data.uuid}`);
    }
})

const onSubmit = () => {
    errors.value = [];

    if (!title.value) {
        errors.value.push({ field: "title", isError: true });
    }

    if (!price.value) {
        errors.value.push({ field: "price", isError: true });
    }

    if (!postalCode.value) {
        errors.value.push({ field: "postalCode", isError: true });
    }

    if (!category.value) {
        errors.value.push({ field: "category", isError: true });
    }

    if (errors.value.length > 0) {
        return;
    }

    // TODO: when backend stores sub categories with listing, add them here!
    createListingMutation({
        name: title.value,
        description: description.value,
        price: Number(price.value),
        postalCode: Number(postalCode.value),
        category: category.value!,
        subcategory: subCategories?.value?.[0] ?? undefined,
        images: selectedImages.value
    });
}

</script>

<template>
    <div class="outer-create-listing-wrapper">
        <div class="page-title">
            {{ $t("createListing") }}
        </div>
        <div class="listing-form">
            <div class="form-horizontal">
                <label for="image">{{ $t('image') }}</label>
                <ImageSelector @file-select="onImagesSelected" />
            </div>
            <FormGroup :label="$t('image')" name="image" v-if="selectedImagesUrls.length > 0">
                <PhotoGallery :images="selectedImagesUrls" id="uploadedImages" />
            </FormGroup>
            <FormGroup :label="$t('title')" name="title"
                :isNotFilledIn="errors.find(e => e.field === 'title')?.isError">
                <TextInput v-model="title" type="text" id="title" name="title" autocomplete="off" />
            </FormGroup>
            <FormGroup :label="$t('description')" name="description">
                <textarea id="description" name="description" rows="4" v-model="description"></textarea>
            </FormGroup>
            <FormGroup :label="$t('price')" name="price"
                :isNotFilledIn="errors.find(e => e.field === 'price')?.isError">
                <TextInput v-model="price" type="number" id="price" name="price" autocomplete="off" />
            </FormGroup>
            <FormGroup :label="$t('postalCode')" name="postalCode"
                :isNotFilledIn="errors.find(e => e.field === 'postalCode')?.isError">
                <TextInput v-model="postalCode" type="number" id="postalCode" name="postalCode" autocomplete="off" />
            </FormGroup>
            <FormGroup :label="$t('category')" name="category"
                :isNotFilledIn="errors.find(e => e.field === 'category')?.isError">
                <CategorySelector name="category" @category-selected="category = $event"
                    @subcategories-updated="subCategories = $event" />
            </FormGroup>
            <Button label="Submit" variant="primary" @click="onSubmit">
                <template v-if="isPending">
                    <LoadingSpinner />
                </template>
                <template v-else>
                    {{ $t('create') }}
                </template>
            </Button>
            <template v-if="isError">
                <p class="error-message">{{ $t('error') }}: {{ error?.message }}</p>
            </template>
        </div>
    </div>
</template>

<style scoped>
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

.outer-create-listing-wrapper {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 1rem;
}
</style>
