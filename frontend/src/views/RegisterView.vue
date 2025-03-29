<script setup lang="ts">
import useRegister from '@/actions/register';
import Button from '@/components/Button.vue';
import LoadingSpinner from '@/components/LoadingSpinner.vue';
import TextInput from '@/components/TextInput.vue';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const username = ref("");
const password = ref("");

const router = useRouter();

const { isError, error, isPending, mutate: register } = useRegister({
    onSuccess: () => {
        router.push({ name: "me" });
    }
});

const onSubmit = async (event: Event) => {
    event.preventDefault();

    if (isPending.value) {
        return;
    }

    register({ username: username.value, password: password.value });
    password.value = "";
}

const onEnter = (event: KeyboardEvent) => {
    (event.target as HTMLInputElement).blur()
};

</script>

<template>
    <div class="outer-wrapper">
        <h1 class="title">{{ $t("register") }}</h1>
        <form class="register-form" @submit="onSubmit">
            <div>
                <label for="username">{{ $t('username') }}</label>
                <TextInput v-model="username" type="text" id="username" name="username" autocomplete="off"
                    @keypress.enter="onEnter" />
            </div>
            <div>
                <label for="password">{{ $t('password') }}</label>
                <TextInput v-model="password" type="password" autocomplete="off" @keypress.enter="onEnter" />
            </div>
            <Button variant="primary" type="submit" class="submit">
                <template v-if="isPending">
                    <LoadingSpinner />
                </template>
                <template v-else>
                    {{ $t("register") }}
                </template>
            </button>
        </form>
        <div v-if="isError" class="errorText">{{ error?.message ?? $t("somethingWentWrong") }} </div>
        <div>
            {{ $t("haveAccount") }}
            <RouterLink to="/profile" class="login-link">
                {{ $t("login") }}
            </RouterLink>
        </div>
    </div>
</template>

<style scoped>
.errorText {
    color: oklch(0.637 0.237 25.331);
}

.submit {
    height: 2rem;
    width: 20rem;
}

.title {
    margin-top: 2rem;
    font-size: 2.5rem;
}

.register-form {
    display: flex;
    flex-direction: column;
    gap: 1rem;
}

.register-form>div {
    display: flex;
    flex-direction: column;
    gap: 0.25rem;
}

.outer-wrapper {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 2rem;
}
</style>