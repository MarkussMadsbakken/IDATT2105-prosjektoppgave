<script setup lang="ts">
import useRegister from '@/actions/register';
import { useUpdateUser } from '@/actions/user';
import Button from '@/components/Button.vue';
import LoadingSpinner from '@/components/LoadingSpinner.vue';
import ChooseOptionals from '@/components/register/chooseOptionals.vue';
import ChoosePassword from '@/components/register/choosePassword.vue';
import ChooseUsername from '@/components/register/chooseUsername.vue';
import { useQueryClient } from '@tanstack/vue-query';
import { AnimatePresence, motion } from 'motion-v';
import { computed, ref } from 'vue';
import { useRouter } from 'vue-router';

const username = ref("");
const usernameIsValid = ref(false);
const password = ref("");
const userImage = ref<File | null>(null);
const firstName = ref("");
const lastName = ref("");

const queryClient = useQueryClient();
const router = useRouter();

const step = ref(0);
const previousStep = ref(0);
const hasPreviousStep = computed(() => step.value !== 0);
const hasNextStep = computed(() => {
    return usernameIsValid.value
});

const { isError, error, isPending, mutate: register } = useRegister({
    onSuccess: () => {
        router.push({ name: "me" });
    }
});

const { mutate: editUser } = useUpdateUser();


const handleNextStep = () => {

    if (!usernameIsValid.value) {
        return;
    }

    if (!password.value && step.value > 0) {
        return;
    }

    if (step.value === 2) {

        if (!password.value) {
            return;
        }

        register({ username: username.value, password: password.value }, {
            onSuccess: () => {
                editUser({
                    firstName: firstName.value,
                    lastName: lastName.value,
                    profileImage: userImage.value ?? undefined
                }, {
                    onSettled: () => {
                        queryClient.invalidateQueries({
                            queryKey: ["user", "image"]
                        });
                        router.push({ name: "me" });
                    }
                });
            }
        })
        return;
    }

    previousStep.value = step.value;
    step.value++;
}


const handlePrevStep = () => {
    previousStep.value = step.value;
    step.value--;
}

const variants = {
    initialFromRight: { opacity: 0, x: 100 },
    initialFromLeft: { opacity: 0, x: -100 },
    center: { opacity: 1, x: 0, scale: 1 },
    exit: {
        opacity: 0,
        scale: 0.8,
        transition: {
            duration: 0.1,
        }
    }
}

</script>

<template>
    <div class="outer-wrapper">
        <h1 class="title">{{ $t("register.register") }}</h1>

        <motion.form layout @submit.prevent="handleNextStep" class="register-form">
            <AnimatePresence mode="wait">
                <motion.template v-if="step === 0" exit="exit" :initial="previousStep === 1 ? 'initialFromLeft' : false"
                    :animate="'center'" :variants="variants">
                    <ChooseUsername :initial-username="username" @username-selected="(newVal) => {
                        username = newVal.username;
                        usernameIsValid = newVal.isValid;
                    }" />
                </motion.template>

                <motion.template v-else-if="step === 1" :animate="'center'" exit="exit"
                    :initial="previousStep === 0 ? 'initialFromRight' : 'initialFromLeft'" :variants="variants">
                    <ChoosePassword @password-selected="password = $event" :username="username" />
                </motion.template>

                <motion.template v-else-if="step === 2" :animate="'center'" exit="exit"
                    :initial="previousStep === 3 ? 'initialFromLeft' : 'initialFromRight'" :variants="variants">
                    <ChooseOptionals @name-selected="(newVal) => {
                        firstName = newVal.firstName;
                        lastName = newVal.lastName;
                    }" @profile-image-selected="userImage = $event" />
                </motion.template>
            </AnimatePresence>

            <div class="step-group">
                <Button class="prev-step" type="button" @click="handlePrevStep" :disabled="!hasPreviousStep">
                    {{ $t("register.previous") }}
                </Button>

                <Button class="next-step" type="submit" :disabled="!hasNextStep">
                    <template v-if="isPending">
                        <LoadingSpinner />
                    </template>
                    <template v-else>
                        <template v-if="step == 0">{{ $t("register.next") }}</template>
                        <template v-else-if="step == 1">{{ $t("register.next") }}</template>
                        <template v-else-if="step == 2">{{ $t("register.createAccount") }}</template>
                    </template>
                </Button>
            </div>
            <div v-if="isError" class="errorText">{{ error?.message ?? $t("register.somethingWentWrong") }} </div>
        </motion.form>
        <div>
            {{ $t("register.haveAccount") }}
            <RouterLink to="/profile" class="login-link">
                {{ $t("register.login") }}
            </RouterLink>
        </div>
    </div>
</template>

<style scoped>
.step-group {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    width: 100%;
}

.register-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 1rem;
    width: 40%;
    height: fit-content;
}

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

.outer-wrapper {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 2rem;
}
</style>
