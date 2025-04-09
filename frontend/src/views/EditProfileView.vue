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
import Breadcrumb from 'primevue/breadcrumb';

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


const profileImagePreview = ref<string | null>(image.value ? toImgString(image.value) : null);
const firstName = ref(user.value?.firstName ?? "");
const lastName = ref(user.value?.lastName ?? "");


watch(profileImage, (newImage) => {
  if (newImage) {
    profileImagePreview.value = URL.createObjectURL(newImage);
  }
})

watchOnce(user, (newuser) => {
  if (newuser) {
    firstName.value = newuser.firstName ?? "";
    lastName.value = newuser.lastName ?? "";
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
  updateUser({
    firstName: firstName.value,
    lastName: lastName.value,
    profileImage: profileImage.value ?? undefined,
  });
}

</script>

<template>
  <div class="outer-wrapper">
    <div class="edit-profile-header">
      <div class="title-wrapper">
        <div class="title">{{ $t("profile.editProfile") }}: </div>
      </div>
      <div class="breadcrumb">
        <Breadcrumb
          :model="[{ label: $t('breadcrumb.profile'), url: '/profile' }, { label: $t('breadcrumb.editProfile') }]" />
      </div>
    </div>
    <div class="listing-form">
      <div class="form-group">
        <label for="profileImage">{{ $t("register.profilePicture") }}</label>
        <div class="image-upload-preview">
          <ImageSelector @file-select="onImageSelected" />
          <img v-if="profileImagePreview" :src="profileImagePreview" alt="Forhåndsvisning" width="120" />
        </div>
      </div>
      <FormGroup name="firstName" :label="$t('profile.firstName')">
        <TextInput v-model="firstName" type="text" id="firstName" />
      </FormGroup>
      <FormGroup name="lastName" :label="$t('profile.lastName')">
        <TextInput v-model="lastName" type="text" id="lastName" />
      </FormGroup>
      <div class="save-button-wrapper">
        <Button variant="primary" @click="onSubmit">
          <template v-if="isUpdatingUser">
            <LoadingSpinner />
          </template>
          <template v-else>
            {{ $t("profile.saveChanges") }}
          </template>
        </Button>
      </div>
      <Button class="edit-password-button" variant="primary" @click="router.push('/profile/change-credentials')">
        {{ $t("profile.editPassword") }}
      </Button>
    </div>
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

@media (max-width: 700px) {
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

@media (max-width: 800px) {
  .listing-form {
    width: 100%;
    padding-left: 1rem;
    padding-right: 1rem;
  }
}

.outer-wrapper {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding-top: 1rem;
  padding-bottom: 5rem;
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

.button-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  margin-top: 1rem;
}

.save-button-wrapper {
  margin: 0 auto;
}

.edit-password-button {
  width: 10rem;
}
</style>
