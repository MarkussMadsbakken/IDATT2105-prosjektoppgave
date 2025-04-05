<script setup lang="ts">
import { computed, ref, watch } from 'vue';
import FormGroup from '../FormGroup.vue';
import TextInput from '../TextInput.vue';
import ImageSelector from '../ImageSelector.vue';


const firstName = ref("");
const lastName = ref("");

const profileImage = ref<File | null>(null);
const profileImagePreview = computed(() => {
    if (profileImage.value) {
        return URL.createObjectURL(profileImage.value);
    }
    return null;
});

const onImageSelected = (file: File[]) => {
    profileImage.value = file[0];
    emit('profile-image-selected', file[0]);
}


const emit = defineEmits<{
    (e: 'profile-image-selected', file: File): void;
    (e: 'name-selected', name: { firstName: string, lastName: string }): void;
}>();

watch([firstName, lastName], () => {
    emit('name-selected', { firstName: firstName.value, lastName: lastName.value });
});

</script>

<template>
    <div class="optionals-select-container">
        <div>
            <label for="imageSelector" class="optionals-image-label">Velg profilbilde</label>
            <div class="image-upload-preview">
                <ImageSelector @file-select="onImageSelected" />
                <img v-if="profileImagePreview" :src="profileImagePreview" alt="Forhåndsvisning" width="120" />
            </div>
        </div>
        <FormGroup name="firstname" label="Velg fornavn">
            <TextInput name="firstname" v-model="firstName" type="text" />
        </FormGroup>
        <FormGroup name="lastName" label="Velg etternavn">
            <TextInput name="lastname" v-model="lastName" type="text" />
        </FormGroup>
    </div>
</template>

<style scoped>
.optionals-image-label {
    font-size: 1.2rem;
    margin-bottom: 1rem;
}

.optionals-select-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    gap: 1rem;
}

.image-upload-preview {
    display: flex;
    flex-direction: row;
    gap: 2rem;
}

.image-upload-preview img {
    max-width: 200px;
    max-height: 200px;
    object-fit: contain;
    border-radius: 6px;
}
</style>