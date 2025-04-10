<script setup lang="ts">
import useLogin from '@/actions/login';
import Button from '@/components/Button.vue';
import LoadingSpinner from '@/components/LoadingSpinner.vue';
import TextInput from '@/components/TextInput.vue';
import { useQueryClient } from '@tanstack/vue-query';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const username = ref("");
const password = ref("");

const router = useRouter();
const queryClient = useQueryClient();

const { isError, error, isPending, mutate: login } = useLogin({
    onSuccess: () => {
        queryClient.invalidateQueries({
            queryKey: ["user", "image"]
        });
        router.push("/");
    }
});

const onSubmit = async (event: Event) => {
    event.preventDefault();

    if (isPending.value) {
        return;
    }

    login({ username: username.value, password: password.value });
    password.value = "";
}

const onEnter = (event: KeyboardEvent) => {
    (event.target as HTMLInputElement).blur()
};

</script>

<template>
    <div class="outer-wrapper">
        <h1 class="title">{{ $t("login.login") }}</h1>
        <form class="login-form" @submit="onSubmit">
            <div>
                <label for="username">{{ $t('login.username') }}</label>
                <TextInput v-model="username" type="text" id="username" name="username" autocomplete="off"
                    @keypress.enter="onEnter" />
            </div>
            <div>
                <label for="password">{{ $t('login.password') }}</label>
                <TextInput id="password" v-model="password" type="password" autocomplete="off" @keypress.enter="onEnter" />
            </div>
            <Button id="login-button" variant="primary" type="submit" class="submit">
                <template v-if="isPending">
                    <LoadingSpinner />
                </template>
                <template v-else>
                    {{ $t("login.login") }}
                </template>
            </button>
        </form>
        <div v-if="isError" class="errorText">{{ $t(error?.message ?? "login.somethingWentWrong") }} </div>
        <div>
            {{ $t("login.noAccount") }}
            <RouterLink to="/register" class="register-link">
                {{ $t("login.register") }}
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

.login-form {
    display: flex;
    flex-direction: column;
    gap: 1rem;
}

.login-form>div {
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
