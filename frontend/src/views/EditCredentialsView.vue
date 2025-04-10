<script setup lang="ts">
import TextInput from '@/components/TextInput.vue';
import FormGroup from '@/components/FormGroup.vue';
import Button from '@/components/Button.vue';
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import { useToast } from 'primevue/usetoast';
import { useI18n } from 'vue-i18n';
import { useGetUser, useUpdateUserCredentials } from "@/actions/user.ts";
import { useAuth } from "@/stores/auth.ts";
import { watchOnce } from "@vueuse/core";
import { validatePassword } from '@/util/passwordValdation';
import LoadingSpinner from '@/components/LoadingSpinner.vue';
import Breadcrumb from 'primevue/breadcrumb';

const { t } = useI18n();
const router = useRouter();
const toast = useToast();


const auth = useAuth();
const { data: user, isError, error, isPending } = useGetUser(auth.userId!);

const userName = ref(user.value?.username);
const currentPassword = ref('');
const repeatPassword = ref('');
const newPassword = ref('');

const passwordMismatch = ref(false);
const usernameIsError = ref(false);
const passwordHasError = ref(false);
const passwordError = ref('');
const currenctPasswordNotFilledIn = ref(false);

watch(newPassword, (newPassword) => {
  const { valid, error } = validatePassword(newPassword);

  if (!valid) {
    passwordHasError.value = true;
    passwordError.value = error;
    return;
  }

  passwordHasError.value = false;
  passwordError.value = '';
});

watch(repeatPassword, (repeatPassword) => {
  if (repeatPassword !== newPassword.value) {
    passwordMismatch.value = true;
    return;
  }
  passwordMismatch.value = false;
});

watchOnce(user, (user) => {
  if (!user) {
    router.replace("/404");
  }
  userName.value = user?.username;
});

watch(currentPassword, (currentPassword) => {
  if (!currentPassword) {
    currenctPasswordNotFilledIn.value = true;
    return;
  }
  currenctPasswordNotFilledIn.value = false;
});


const {
  mutate: updateUserCredentials,
  isPending: isUpdatingUser,
  isError: isUpdateError,
  error: updateError
} = useUpdateUserCredentials();

const onSubmit = () => {
  passwordMismatch.value = false;
  usernameIsError.value = false;

  if (newPassword.value !== repeatPassword.value) {
    passwordMismatch.value = true;
    return;
  }

  if (!userName.value) {
    usernameIsError.value = true;
    return;
  }

  if (!userName.value) {
    usernameIsError.value = true;
    return;
  }

  if (!newPassword.value) {
    passwordHasError.value = true;
    passwordError.value = t("passwordCannotBeEmpty");
    return;
  }

  if (!currentPassword.value) {
    currenctPasswordNotFilledIn.value = true;
    return;
  }

  updateUserCredentials({
    username: userName.value,
    currentPassword: currentPassword.value,
    newPassword: newPassword.value
  },
    {
      onSuccess: (data) => {
        auth.swapToken(data.token);
        toast.add(
          {
            severity: "success",
            summary: t("success"),
            detail: t("profile.credentialsUpdated"),
            life: 3000,
            closable: true,
          }
        );
        router.push('/profile');
      },
      onError: (error) => {
        toast.add({
          severity: "error",
          summary: t("error"),
          detail: t("profile.updateFailed"),
          life: 3000,
          closable: true,
        });
      },
    }
  );
};
</script>

<template>
  <div class="outer-wrapper">
    <div class="edit-profile-header">
      <div class="title-wrapper">
        <div class="title">{{ $t("profile.editPassword") }}: </div>
      </div>
      <div class="breadcrumb">
        <Breadcrumb
          :model="[{ label: $t('breadcrumb.profile'), url: '/profile' }, { label: $t('breadcrumb.editProfile'), url: '/profile/edit' }, { label: $t('breadcrumb.editCredentials') }]" />
      </div>
    </div>
    <form class="listing-form" @submit.prevent="onSubmit">
      <FormGroup name="userName" :label="$t('login.username')" :is-not-filled-in="usernameIsError">
        <TextInput v-model="userName" type="text" id="userName" :class="{ invalid: usernameIsError }" />
      </FormGroup>
      <FormGroup name="currentPassword" :label="t('profile.currentPassword')"
        :is-not-filled-in="currenctPasswordNotFilledIn">
        <TextInput v-model="currentPassword" type="password" id="currentPassword" />
      </FormGroup>

      <FormGroup name="newPassword" :label="t('profile.newPassword')">
        <TextInput v-model="newPassword" type="password" id="newPassword" />
        <p class="error-text" v-if="passwordHasError">{{ $t(`profile.${passwordError}`) }}</p>
      </FormGroup>

      <FormGroup name="repeatPassword" :label="t('profile.repeatPassword')">
        <TextInput v-model="repeatPassword" type="password" id="repeatPassword" />
        <p class="error-text" v-if="passwordMismatch">{{ t("profile.passwordMismatch") }}</p>
      </FormGroup>

      <div class="button-wrapper">
        <Button class="save-password-button" variant="primary" type="submit">
          <template v-if="isUpdatingUser">
            <LoadingSpinner />
          </template>
          <template v-else>
            {{ t("profile.savePassword") }}
          </template>
        </Button>
      </div>
    </form>
  </div>
</template>

<style scoped>
.edit-profile-header {
  position: relative;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  padding: 2rem;
  padding-bottom: 0.1rem;
  width: 100%;
}

.breadcrumb {
  position: absolute;
  left: 2rem;
}

@media (max-width: 950px) {
  .edit-profile-header {
    flex-direction: column-reverse;
    gap: 0.5rem;
  }

  .breadcrumb {
    position: static;
    width: fit-content;
  }

}

.breadcrumb>* {
  background-color: var(--color-background);
}

.title {
  font-size: 2rem;
  font-weight: bold;
}

.title-wrapper {
  text-align: center;
}


.outer-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding-top: 1rem;
}

.page-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 1rem;
}

.listing-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 50rem;
  max-width: 90%;
}

.error-text {
  color: red;
  font-size: 0.875rem;
  margin-top: 0.25rem;
}

.save-password-button {
  justify-content: center;
}

.button-wrapper {
  width: 100%;
  display: flex;
  justify-content: center;
}
</style>
