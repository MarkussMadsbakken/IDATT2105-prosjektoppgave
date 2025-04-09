<script setup lang="ts">
import TextInput from '@/components/TextInput.vue';
import FormGroup from '@/components/FormGroup.vue';
import Button from '@/components/Button.vue';
import {computed, ref} from 'vue';
import { useRouter } from 'vue-router';
import { useToast } from 'primevue/usetoast';
import { useI18n } from 'vue-i18n';
import user, {updateUser, useGetUser, useUpdateUserCredentials} from "@/actions/user.ts";
import { useAuth } from "@/stores/auth.ts";
import {watchOnce} from "@vueuse/core";

const { t } = useI18n();
const router = useRouter();
const toast = useToast();


const auth = useAuth();
const userId = computed(() => auth.userId);

if (auth.userId !== undefined) {
  const {
    data: user,
    isError,
    error,
    isPending
  } = useGetUser(auth.userId);
}

const userName = ref('');
const currentPassword = ref('');
const repeatPassword = ref('');
const newPassword = ref('');

const passwordMismatch = ref(false);
const isSubmitting = ref(false);
const usernameIsError = ref(false);


const {
  mutate: updateUserCredentials,
  isPending: isUpdatingUser,
  isError: isUpdateError,
  error: updateError
} = useUpdateUserCredentials({
  onSuccess: () => {
    toast.add(
      {
        severity: "success",
        summary: t("success"),
        detail: t("profile.credentialUpdated"),
        life: 3000,
        closable: true,
      }
    );
    router.push('/profile/edit');
  },
  onError: (error) => {
    console.error(error);
    toast.add({
      severity: "error",
      summary: t("error"),
      detail: t("profile.updateFailed"),
      life: 3000,
      closable: true,
    });
  }
});

const onSubmit = () => {
  passwordMismatch.value = false;
  usernameIsError.value = false;
  isSubmitting.value = true;

  if (newPassword.value !== repeatPassword.value) {
    passwordMismatch.value = true;
    isSubmitting.value = false;
    return;
  }
  if (newPassword.value.length < 8 || repeatPassword.value.length < 8){
    toast.add({
      severity: "error",
      summary: t("error"),
      detail: t("profile.passwordTooShort"),
      life: 3000,
      closable: true,
    });
    isSubmitting.value = false;
    return;
  }

  if (!userName.value) {
    usernameIsError.value = true;
    isSubmitting.value = false;
    return;
  }
  if (newPassword.value.length < 8) {
    toast.add({
      severity: "error",
      summary: t("error"),
      detail: t("profile.passwordTooShort"),
      life: 3000,
      closable: true,
    });
    isSubmitting.value = false;
    return;
  }


  updateUserCredentials({
    username: userName.value,
    currentPassword: currentPassword.value,
    newPassword: newPassword.value,
  });

  isSubmitting.value = false;
};





</script>


<template>
    <div class="outer-wrapper">
      <div class="page-title">{{ t("profile.editPassword") }}</div>
      <div class="listing-form">

        <FormGroup name="userName" :label="$t('login.username')" :is-not-filled-in="usernameIsError">
          <TextInput v-model="userName" type="text" id="userName" :class="{ invalid: usernameIsError }" />
        </FormGroup>
        <FormGroup name="currentPassword" :label="t('profile.currentPassword')">
          <TextInput v-model="currentPassword" type="password" id="currentPassword" />
        </FormGroup>

        <FormGroup name="newPassword" :label="t('profile.newPassword')">
          <TextInput v-model="newPassword" type="password" id="newPassword" />
        </FormGroup>

        <FormGroup name="repeatPassword" :label="t('profile.repeatPassword')">
          <TextInput v-model="repeatPassword" type="password" id="repeatPassword" />
          <p class="error-text" v-if="passwordMismatch">{{ t("profile.passwordMismatch") }}</p>
        </FormGroup>

        <div class="button-wrapper">
          <Button class="save-password-button" variant="primary" @click="onSubmit">
            {{ isSubmitting ? t("loading") : t("profile.savePassword") }}
          </Button>
        </div>
      </div>
    </div>
</template>

<style scoped>
.outer-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 1rem;
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
