<script setup lang="ts">
import { UserIcon } from 'lucide-vue-next'
import type { User } from '@/types/user.ts'
import Button from '@/components/Button.vue';
import { useRouter } from "vue-router";
import UserImage from './UserImage.vue';
import { useGetUser } from '@/actions/user';
import { computed } from 'vue';

const router = useRouter();
const props = defineProps<{
  userId: number;
  canContactSeller?: boolean;
}>();

const emit = defineEmits<{
  (e: 'contact-seller'): void;
}>();


const { data: user, isPending, isError, error } = useGetUser(props.userId);

const joinedYear = computed(() => user.value && user.value.createdAt ? "Medlem siden " + new Date(user.value.createdAt).getFullYear() : '');
const handleContactClick = () => {
  emit('contact-seller');
};

</script>

<template>
  <div v-if="isPending">
    <p>Laster inn...</p>
  </div>
  <div v-else-if="isError">
    <p>Feil: {{ error?.message }}</p>
  </div>
  <div class="seller-container" v-else>
    <div class="seller-left">
      <UserImage :user-id="user?.id!" />
      <div class="seller-info" :class="{ centered: !props.canContactSeller }">
        <div class="seller-names">
          <div class="seller-name">{{ user?.firstName }} {{ user?.lastName }}</div>
          <div class="username">{{ user?.username }}</div>
        </div>
        <div class="seller-meta">
          <span class="joined-site">{{ joinedYear }}</span>
        </div>
      </div>
    </div>
    <Button variant="outline" class="contact-button" @click="handleContactClick" v-if="props.canContactSeller">{{
      $t("contactSeller") }}</Button>
  </div>

</template>

<style scoped>
.seller-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  outline: black;
  border: 1px solid black;
  border-radius: 5px;
  width: 40rem;
  height: 8rem;
}

.seller-container.centered {
  justify-content: center;
}

.seller-left {
  position: relative;
  flex: 1;
  margin-left: 1rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 1rem;
}

.seller-info.centered {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
}

.seller-name {
  margin-top: -0.2rem;
  font-weight: bold;
  font-size: 2rem;
  line-clamp: 1;
}

.seller-names {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 0rem;
}

.joined-site {
  font-size: 1rem;
  text-align: center;
}

.seller-meta {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}

.contact-button {
  margin-right: 1rem;
}
</style>
