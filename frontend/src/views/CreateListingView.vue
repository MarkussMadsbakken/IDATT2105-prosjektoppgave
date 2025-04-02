<script setup lang="ts">
import Button from '@/components/Button.vue';
import TextInput from '@/components/TextInput.vue';
import { Plus } from 'lucide-vue-next';
import { ref } from 'vue';


const title = ref("");
const description = ref("");
const price = ref("");
const postNumber = ref("");

</script>

<template>
    <div class="outer-wrapper">
        <div class="page-title">
            {{ $t("createListing") }}
        </div>
        <div class="listing-form">
            <div class="form-horizontal">
                <label for="image">{{ $t('image') }}</label>
                <Plus id="image" name="image" />
            </div>
            <div class="form-group">
                <label for="title">{{ $t('title') }}</label>
                <TextInput v-model="title" type="text" id="title" name="title" autocomplete="off" />
            </div>
            <div class="form-group">
                <label for="description">{{ $t('description') }}</label>
                <textarea id="description" name="description" rows="4"></textarea>
            </div>
            <div class="form-group">
                <label for="price">{{ $t('price') }}</label>
                <TextInput v-model="price" type="number" id="price" name="price" autocomplete="off" />
            </div>
            <div class="form-group">
                <label for="postnr">{{ $t('postNumber') }}</label>
                <TextInput v-model="postNumber" type="number" id="postnr" name="postnr" autocomplete="off" />
            </div>
            <Button label="Submit" variant="primary">
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
