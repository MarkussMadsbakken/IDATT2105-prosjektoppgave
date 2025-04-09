<script setup lang="ts">

import ArticleSummary from "@/components/ArticleSummary.vue";
import {useRoute} from "vue-router";
import TextInput from "@/components/TextInput.vue";
import FormGroup from "@/components/FormGroup.vue";
import {ref} from "vue";
import Button from "@/components/Button.vue";
import {usePurchaseListing} from "@/actions/getListing.ts";
import {useI18n} from "vue-i18n";
import Alert from "@/components/Alert.vue";


const route = useRoute();
const listingId = route.params.id as string
const cardNumber = ref('')
const errors = ref<{ field: string; isError: boolean }[]>([]);
const userName = ref('');
const { t } = useI18n();
const showSuccess = ref(false);

const { mutate: purchaseListing, isPending, isError, error, isSuccess } = usePurchaseListing();
const submitPurchase = async () => {
  errors.value = [];

  if (!cardNumber.value){
    errors.value.push({field: 'cardNumber', isError: true})
    console.log("CardNumber not working");
  }
  if (!userName.value) {
    errors.value.push({field: 'name', isError: true})
    console.log("Name not working");
  }
  if (errors.value.length === 0) {
    purchaseListing(
      { uuid: listingId },
      {
        onSuccess: () => {
          showSuccess.value = true;
        },
        onError: (err) => {
          console.error("Purchase failed:", err);
        }
      }
    );
  }
};


</script>

<template>
  <ArticleSummary :listing-id="listingId" />
  <div v-if="!showSuccess" class="form">
    <form @submit.prevent="submitPurchase">

      <FormGroup :label="$t('name')" name="name"
                 :isNotFilledIn="errors.find(e => e.field === 'name')?.isError">
        <TextInput class="name-input" v-model="userName" type="text" id="name" name="name" />
      </FormGroup>

      <FormGroup :label="$t('cardNumber')" name="cardNumber"
                 :isNotFilledIn="errors.find(e => e.field === 'cardNumber')?.isError"
      >
        <TextInput class="cardnumber-input" v-model="cardNumber" type="text" id="cardNumber" name="cardNumber" />
      </FormGroup>

      <div class="button-wrapper">
        <Button type="submit" variant="accept" class="buy-button" :disabled="isPending">
          <template v-if="isPending">Behandler...</template>
          <template v-else>{{ $t('buy') }}</template>
        </Button>
      </div>
    </form>
  </div>
</template>

<style scoped>
.form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  width: 30rem;
  margin: 0 auto;
  gap: 2rem;
}
.cardnumber-input{
  width: 30rem;
}
.name-input{
  width: 30rem;
}
.button-wrapper{
  padding-top: 2rem;
  padding-bottom: 2rem;
  display: flex;
  justify-content: center;
  width: 100%;
}
.purchased-alert{
  width: 50rem;
}
</style>

