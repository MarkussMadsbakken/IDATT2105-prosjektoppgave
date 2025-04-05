<script setup lang="ts">
import { getUsernameIsAvaiable } from '@/actions/user';
import { ref, watch } from 'vue';
import FormGroup from '../FormGroup.vue';
import TextInput from '../TextInput.vue';
import { useDebounce, useDebounceFn } from '@vueuse/core';
import LoadingSpinner from '../LoadingSpinner.vue';

const props = defineProps<{
    initialUsername?: string
}>();

const username = ref(props.initialUsername ?? "");
const usernameIsAvailable = ref<boolean | null>(null);
const isLoading = ref(false);

watch(username, (newUsername) => {
    emit('username-selected', { username: newUsername, isValid: false });

    if (newUsername.length < 3) {
        usernameIsAvailable.value = null;
        return;
    }
    isLoading.value = true;
    checkUsernameIsAvailable(newUsername);
});

const checkUsernameIsAvailable = useDebounceFn(
    async (username: string) => {
        usernameIsAvailable.value = await getUsernameIsAvaiable(username);
        isLoading.value = false;
        emit('username-selected', { username: username, isValid: usernameIsAvailable.value ?? false });
    },
    200,
);

if (props.initialUsername) {
    isLoading.value = true;
    checkUsernameIsAvailable(username.value);
}

const emit = defineEmits<{
    (e: 'username-selected', payload: { username: string, isValid: boolean }): void;
}>();

</script>

<template>
    <div class="username-select-container">
        <FormGroup name="username" label="Velg brukernavn">
            <TextInput name="username" v-model="username" type="text" autocomplete="off" />
        </FormGroup>
        <div class="username-availability">
            <template v-if="usernameIsAvailable === null && !isLoading">
                <p class="username-is-error">{{ $t("usernameTooShort") }}</p>
            </template>
            <template v-else-if="isLoading">
                <LoadingSpinner />
            </template>
            <template v-else-if="usernameIsAvailable">
                <p class="username-is-avaiable">{{ $t("usernameAvailable") }}</p>
            </template>
            <template v-else>
                <p class="username-is-error"> {{ $t("usernameNotAvailable") }}</p>
            </template>
        </div>
    </div>
</template>

<style scoped>
.username-availability {
    display: flex;
    justify-content: center;
    align-items: center;
}

.username-is-error {
    color: oklch(0.637 0.237 25.331);
}

.username-is-avaiable {
    color: oklch(0.723 0.219 149.579);
}

.username-select-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
}
</style>