<script setup lang="ts">
import TextInput from '@/components/TextInput.vue';
import FormGroup from '@/components/FormGroup.vue';
import Button from '@/components/Button.vue';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useToast } from 'primevue/usetoast';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
const router = useRouter();
const toast = useToast();

const currentPassword = ref('');
const newPassword = ref('');
const repeatPassword = ref('');

const passwordMismatch = ref(false);
const isSubmitting = ref(false);

const onSubmit = () => {
  passwordMismatch.value = false;

  if (newPassword.value !== repeatPassword.value) {
    passwordMismatch.value = true;
    return;
  }

  isSubmitting.value = true;


  const printHello = () => {
    console.log("Hello");
  }};


</script>


<template>
    <div class="outer-wrapper">
      <div class="page-title">{{ t("profile.editPassword") }}</div>
      <div class="listing-form">
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
          <Button class="save-password-button" variant="primary">
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
