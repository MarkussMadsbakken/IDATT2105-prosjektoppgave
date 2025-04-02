<script setup lang="ts">
import useCreateListing from '@/actions/createListing';
import Button from '@/components/Button.vue';
import ImageSelector from '@/components/ImageSelector.vue';
import PhotoGallery from '@/components/PhotoGallery.vue';
import TextInput from '@/components/TextInput.vue';
import { computed, ref } from 'vue';


const title = ref("");
const description = ref("");
const price = ref("");
const postalCode = ref("");
const category = ref("");
const selectedImages = ref<File[]>([]);

const onImagesSelected = (images: File[]) => {
    selectedImages.value = [...images, ...selectedImages.value];
}

const selectedImagesUrls = computed(() => {
    return selectedImages.value.map(image => URL.createObjectURL(image));
})

const { isPending, isError, error, mutate: createListing } = useCreateListing();

const onSubmit = () => {
    createListing({
        name: title.value,
        description: description.value,
        price: Number(price.value),
        postalCode: Number(postalCode.value),
        category: category.value
    });
}

</script>

<template>
    <div class="outer-wrapper">
        <div class="page-title">
            {{ $t("createListing") }}
        </div>
        <div class="listing-form">
            <div class="form-horizontal">
                <label for="image">{{ $t('image') }}</label>
                <ImageSelector @file-select="onImagesSelected" />
            </div>
            <div class="form-group" v-if="selectedImagesUrls.length > 0">
                <label for="uploadedImages"> {{ $t('uploadedImages') }}</label>
                <PhotoGallery :images="selectedImagesUrls" id="uploadedImages" />
            </div>
            <div class="form-group">
                <label for="title">{{ $t('title') }}</label>
                <TextInput v-model="title" type="text" id="title" name="title" autocomplete="off" />
            </div>
            <div class="form-group">
                <label for="description">{{ $t('description') }}</label>
                <textarea id="description" name="description" rows="4" v-model="description"></textarea>
            </div>
            <div class="form-group">
                <label for="price">{{ $t('price') }}</label>
                <TextInput v-model="price" type="number" id="price" name="price" autocomplete="off" />
            </div>
            <div class="form-group">
                <label for="postalCode">{{ $t('postalCode') }}</label>
                <TextInput v-model="postalCode" type="number" id="postalCode" name="postalCode" autocomplete="off" />
            </div>

            <!-- TODO: REMOVE THIS WHEN BACKEND IS READY WITH CATEGORIES!! -->
            <div class="form-group">
                <label for="category">{{ $t('category') }}</label>
                <TextInput v-model="category" type="text" id="category" name="category" autocomplete="off" />
            </div>
            <Button label="Submit" variant="primary" @click="onSubmit">
                {{ $t('create') }}
            </Button>
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

.outer-wrapper {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-top: 1rem;
}
</style>
