<script setup lang="ts">
import Fetch from "@/util/fetch";
import useUpdateUser from '@/actions/user.ts';
import Button from '@/components/Button.vue';
import ImageSelector from '@/components/ImageSelector.vue';
import TextInput from '@/components/TextInput.vue';
import {onMounted, ref} from 'vue';
import {useAuth} from "@/stores/auth.ts";
import { useGetUser } from '@/actions/user';
import {computed} from "vue";


const firstName = ref("");
const lastName = ref("");
const userName = ref("");
const profileImage = ref<File | null>(null);
const userId = useAuth().userId

if (!userId) {
  throw new Error("Bruker-ID mangler");
}
const {
  data,
  isError,
  error,
  isPending
} = useGetUser(userId);

const {
  mutate: updateUser,
  isPending: isUpdatingUser,
  isError: isUpdateError,
  error: updateError
} = useUpdateUser({
  onSuccess: () => {
    console.log("Brukerprofil oppdatert!");
  },
});


const profileImagePreview = computed(() => {
  return profileImage.value ? URL.createObjectURL(profileImage.value) : null;
});

const onImageSelected = (files: File[]) => {
  if (files.length>0){
    profileImage.value=files[0];
  }
};
const onSubmit = () => {
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
      <div class="form-group">
        <label for="firstName">Fornavn</label>
        <TextInput v-model="firstName" type="text" id="firstName" :placeholder="data?.firstName ?? ''"/>
      </div>

      <div class="form-group">
        <label for="lastName">Etternavn</label>
        <TextInput v-model="lastName" type="text" id="lastName" :placeholder="data?.lastName ?? ''"/>
      </div>
      <div class="form-group">
        <label for="userName">Brukernavn</label>
        <TextInput v-model="userName" type="text" id="userName" :placeholder="data?.username ?? ''"/>
      </div>


      <Button :label="'Lagre endringer'" variant="primary" @click="onSubmit" :disabled="isUpdatingUser">
        Lagre endringer
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
.image-upload-preview{
  display:flex;
  flex-direction: row;
  gap: 2rem;
}
.image-upload-preview img{
  max-width: 200px;
  max-height: 200px;
  object-fit: contain;
  border-radius: 6px;
}
</style>
