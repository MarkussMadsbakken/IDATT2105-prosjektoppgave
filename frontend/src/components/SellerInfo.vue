<script setup lang="ts">
import { UserIcon } from 'lucide-vue-next'
import type { User } from '@/types/user.ts'
import Button from '@/components/Button.vue';
import { useRouter } from "vue-router";
import UserImage from './UserImage.vue';

const router = useRouter();
const props = defineProps<{
  userEntity: User;
  canContactSeller?: boolean;
}>();


const fullName = `${props.userEntity.firstName} ${props.userEntity.lastName}`;
const joinedYear = "Medlem siden " + new Date(props.userEntity.createdAt).getFullYear();
const userName = `(${props.userEntity.username})`

const handleContactClick = () => {
  // TODO
  router.push(`/message/${props.userEntity.id}`);
};

</script>

<template>

  <div class="seller-container">
    <div class="seller-left">
      <UserImage :src="props.userEntity.imageUrl" />
      <div class="seller-info" :class="{ centered: !props.canContactSeller }">
        <div class="seller-names">
          <div class="seller-name">{{ fullName }}</div>
          <div class="username">{{ userName }}</div>
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
}

.contact-button {
  margin-right: 1rem;
}
</style>
