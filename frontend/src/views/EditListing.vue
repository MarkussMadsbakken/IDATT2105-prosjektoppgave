<script setup lang="ts">
import { createListing, editListing } from '@/actions/createListing';
import { useGetListingWithImages } from '@/actions/getListing';
import Button from '@/components/Button.vue';
import CategorySelector from '@/components/CategorySelector.vue';
import FormGroup from '@/components/FormGroup.vue';
import ImageSelector from '@/components/ImageSelector.vue';
import LoadingSpinner from '@/components/LoadingSpinner.vue';
import NumberInput from '@/components/NumberInput.vue';
import PhotoGallery from '@/components/PhotoGallery.vue';
import TextInput from '@/components/TextInput.vue';
import { useMutation } from '@tanstack/vue-query';
import { computed, ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();
const route = useRoute();

const listingId = route.params.id as string

const {
    data: listingWithImages,
    isError: listingWithImagesIsError,
    error: listingWithImagesError,
    isPending: listingWithImagesIsPending,
} = useGetListingWithImages(listingId);

const title = ref(listingWithImages?.value?.listing?.name ?? "");
const description = ref(listingWithImages?.value?.listing?.description ?? "");
const price = ref(listingWithImages?.value?.listing?.price ?? 0);
const postalCode = ref(listingWithImages?.value?.listing?.postalCode);
const category = ref<number | null>(null);
const subCategories = ref<number[] | null>(null);

watch(listingWithImages, (newListing) => {
    if (!newListing) return;

    title.value = newListing?.listing.name ?? "";
    description.value = newListing?.listing.description ?? "";
    price.value = newListing?.listing.price ?? 0;
    postalCode.value = newListing?.listing.postalCode ?? 0;
    category.value = newListing?.listing.category
}, { deep: true });

const images = computed(() => {
    return listingWithImages.value?.images?.map((image) => {
        return `data:image/${image.fileType};base64,${image.base64Image}`
    })
});

let errors = ref<{
    field: string;
    isError: boolean;
}[]>([])

const { isPending, isError, error, mutate: editListingMutation } = useMutation({
    mutationFn: editListing,
    onSuccess: () => {
        router.push(`/listing/${listingId}`);
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

    editListingMutation({
        name: title.value,
        description: description.value,
        price: Number(price.value),
        postalCode: Number(postalCode.value),
        category: category.value!,
        subcategory: subCategories?.value?.[0] ?? undefined,
        uuid: listingId,
        active: listingWithImages.value?.listing.active!,
        deleted: listingWithImages.value?.listing.deleted!,
        sold: listingWithImages.value?.listing.sold!,
    });
}
</script>

<template>
    <div class="outer-create-listing-wrapper">
        <div class="page-title">
            {{ $t("editListing") }}
        </div>
        <div class="listing-form" v-if="!listingWithImagesIsPending">
            <FormGroup :label="$t('image')" name="image" v-if="images && images?.length! > 0">
                <PhotoGallery :images="images!" id="uploadedImages" />
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
                <NumberInput v-model="price" type="number" id="price" name="price" autocomplete="off" />
            </FormGroup>
            <FormGroup :label="$t('postalCode')" name="postalCode"
                :isNotFilledIn="errors.find(e => e.field === 'postalCode')?.isError">
                <NumberInput v-model="postalCode" type="number" id="postalCode" name="postalCode" autocomplete="off" />
            </FormGroup>
            <FormGroup :label="$t('category')" name="category"
                :isNotFilledIn="errors.find(e => e.field === 'category')?.isError">
                <CategorySelector
                    :initial-sub-categories="listingWithImages?.listing.subcategory ? [listingWithImages.listing.subcategory] : undefined"
                    :initial-category="listingWithImages?.listing.category" name="category"
                    @category-selected="category = $event" @subcategories-updated="subCategories = $event" />
            </FormGroup>
            <Button label="Submit" variant="primary" @click="onSubmit">
                <template v-if="isPending">
                    <LoadingSpinner />
                </template>
                <template v-else>
                    {{ $t('save') }}
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
    padding-bottom: 3rem;
}
</style>
