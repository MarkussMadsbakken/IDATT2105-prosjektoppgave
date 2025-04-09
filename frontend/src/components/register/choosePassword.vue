<script setup lang="ts">
import { ref, watch } from 'vue';
import FormGroup from '../FormGroup.vue';
import TextInput from '../TextInput.vue';
import { validatePassword } from '@/util/passwordValdation';

const props = defineProps<{
    username?: string
}>();


const username = ref(props.username ?? "");
const password = ref("");
const passwordError = ref("");

const emit = defineEmits<{
    (e: 'password-selected', password: string): void;
}>();

watch(password, (newPassword) => {
    const { valid, error } = validatePassword(newPassword);
    if (!valid) {
        passwordError.value = error;
        return;
    }

    passwordError.value = "";
    emit('password-selected', newPassword);
});

</script>

<template>
    <div class="password-select-container">
        <FormGroup name="username" :label="$t('register.chooseUsername')">
            <TextInput name="username" v-model="username" type="text" disabled />
        </FormGroup>
        <FormGroup name="password" :label="$t('register.choosePassword')">
            <TextInput name="password" v-model="password" type="password" />
            <p class="error-text" v-if="passwordError">
                {{ $t(`profile.${passwordError}`) }}
            </p>
        </FormGroup>
    </div>
</template>

<style scoped>
.error-text {
    color: oklch(63.7% 0.237 25.331);
    font-size: 0.9rem;
    margin-top: 0.5rem;
}

.password-select-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    gap: 1rem;
}
</style>