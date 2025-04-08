<script setup lang="ts">
import { ref, watch } from 'vue';
import FormGroup from '../FormGroup.vue';
import TextInput from '../TextInput.vue';

const props = defineProps<{
    username?: string
}>();


const username = ref(props.username ?? "");
const password = ref("");

const emit = defineEmits<{
    (e: 'password-selected', password: string): void;
}>();

watch(password, (newPassword) => {
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
        </FormGroup>
    </div>
</template>

<style scoped>
.password-select-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    gap: 1rem;
}
</style>