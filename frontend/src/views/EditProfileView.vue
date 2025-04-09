<script setup lang="ts">
import { useUpdateUser, useUserImage } from '@/actions/user.ts';
import Button from '@/components/Button.vue';
import ImageSelector from '@/components/ImageSelector.vue';
import TextInput from '@/components/TextInput.vue';
import { ref, watch } from 'vue';
import { useAuth } from "@/stores/auth.ts";
import { useGetUser } from '@/actions/user';
import router from "@/router";
import LoadingSpinner from "@/components/LoadingSpinner.vue";
import FormGroup from "@/components/FormGroup.vue";
import { toImgString } from "@/util/imageToImgString";
import { watchOnce } from "@vueuse/core";
import { useToast } from 'primevue/usetoast';
import { useI18n } from 'vue-i18n';

const toast = useToast();
const { t } = useI18n();

const profileImage = ref<File | null>(null);
const auth = useAuth();

if (!auth.userId) {
  throw new Error("Bruker-ID mangler");
}
const {
  data: user,
  isError,
  error,
  isPending
} = useGetUser(auth.userId);

const {
  mutate: updateUser,
  isPending: isUpdatingUser,
  isError: isUpdateError,
  error: updateError
} = useUpdateUser({
  onSuccess: () => {
    toast.add(
      {
        severity: "success",
        summary: t("success"),
        detail: t("profile.updated"),
        life: 3000,
        closable: true,
      }
    );
    router.push('/profile');
  },
});

const {
  data: image,
} = useUserImage(auth.userId);

const usernameIsError = ref(false);
const profileImagePreview = ref<string | null>(image.value ? toImgString(image.value) : null);
const firstName = ref(user.value?.firstName ?? "");
const lastName = ref(user.value?.lastName ?? "");
const userName = ref(user.value?.username ?? "");

watch(profileImage, (newImage) => {
  if (newImage) {
    profileImagePreview.value = URL.createObjectURL(newImage);
  }
})

watchOnce(user, (newuser) => {
  if (newuser) {
    firstName.value = newuser.firstName ?? "";
    lastName.value = newuser.lastName ?? "";
    userName.value = newuser.username!;
  }
});

watchOnce(image, (newImage) => {
  if (newImage) {
    profileImagePreview.value = toImgString(newImage);
  }
});

const onImageSelected = (files: File[]) => {
  if (files.length > 0) {
    profileImage.value = files[0];
  }
};
const onSubmit = () => {

  if (!userName.value) {
    usernameIsError.value = true;
    return;
  }

  updateUser({
    username: userName.value,
    firstName: firstName.value,
    lastName: lastName.value,
    profileImage: profileImage.value ?? undefined,
  });
}

</script>

<template>
  <div class="outer-wrapper">
    <div class="page-title">Rediger profil</div>

    <div class="listing-form">

      <div class="form-group">
        <label for="profileImage">Profilbilde</label>
        <div class="image-upload-preview">
          <ImageSelector @file-select="onImageSelected" />
          <img v-if="profileImagePreview" :src="profileImagePreview" alt="Forhåndsvisning" width="120" />
        </div>
      </div>
      <FormGroup name="firstName" :label="$t('firstName')">
        <TextInput v-model="firstName" type="text" id="firstName" />
      </FormGroup>
      <FormGroup name="lastName" :label="$t('lastName')">
        <TextInput v-model="lastName" type="text" id="lastName" />
      </FormGroup>
      <FormGroup name="userName" :label="$t('username')" :is-not-filled-in="usernameIsError">
        <TextInput v-model="userName" type="text" id="userName" :class="{ invalid: usernameIsError }" />
      </FormGroup>
      <Button variant="primary" @click="onSubmit">
        <template v-if="isUpdatingUser">
          <LoadingSpinner />
        </template>
        <template v-else>
          {{ $t("saveChanges") }}
        </template>
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

.image-upload-preview {
  display: flex;
  flex-direction: row;
  gap: 2rem;
}

.image-upload-preview img {
  max-width: 200px;
  max-height: 200px;
  object-fit: contain;
  border-radius: 6px;
}

.error-text {
  color: red;
  font-size: 0.875rem;
  margin-top: 0.25rem;
}

.invalid {
  border: 1px solid red;
}
</style>
