<script setup lang="ts">

import ArticleSummary from "@/components/ArticleSummary.vue";
import {useRoute} from "vue-router";
import TextInput from "@/components/TextInput.vue";
import FormGroup from "@/components/FormGroup.vue";
import {ref} from "vue";
import Button from "@/components/Button.vue";


const route = useRoute();
const listingId = route.params.id as string
const cardNumber = ref('')
const errors = ref<{ field: string; isError: boolean }[]>([]);
const userName = ref('');
const submitPurchase = () => {
  errors.value = [];

  if (!cardNumber.value){
    errors.value.push({field: 'cardNumber', isError: true})
  }
  if (!userName.value) {
    errors.value.push({field: 'name', isError: true})
  }
  if (errors.value.length === 0) {
  alert("Kjøp fullført!")
  }
};

</script>

<template>
  <ArticleSummary :listing-id="listingId" />
  <div class="form">
    <form @submit.prevent="submitPurchase">

      <FormGroup :label="$t('name')" name="name"
                 :isNotFilledIn="errors.find(e => e.field === 'name')?.isError">
        <TextInput v-model="userName" type="text" id="name" name="name" />
      </FormGroup>

      <FormGroup :label="$t('Kortnummer (Ikke skriv inn ekte)')" name="cardNumber"
                 :isNotFilledIn="errors.find(e => e.field === 'cardNumber')?.isError"
      >
        <TextInput v-model="cardNumber" type="text" id="cardNumber" name="cardNumber" />
      </FormGroup>
    </form>
      <Button variant="accept" class="buy-button">Kjøp</Button>
  </div>
</template>

<style scoped>
.form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  align-items: center;
  width: 30rem;
  margin: 0 auto;
  gap: 2rem;
}
.buy-button {
  gap: 3rem;
}
</style>

