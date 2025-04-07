<script setup lang="ts">
import Button from '@/components/Button.vue';
import { useRouter } from "vue-router";
import UserImage from './UserImage.vue';
import { useGetUser } from '@/actions/user';
import { computed } from 'vue';
import { useAuth } from '@/stores/auth';
import SellerInfoSkeleton from './skeleton/SellerInfoSkeleton.vue';

const props = defineProps<{
  userId: number;
  canContactSeller?: boolean;
}>();

const emit = defineEmits<{
  (e: 'contact-seller'): void;
}>();

const router = useRouter();
const auth = useAuth();

const { data: user, isPending, isError, error } = useGetUser(props.userId);


const joinedYear = computed(() => user.value && user.value.createdAt ? "Medlem siden " + new Date(user.value.createdAt).getFullYear() : '');
const handleContactClick = () => {
  emit('contact-seller');
};

const handleProfileClick = () => {
  if (user?.value?.id === auth.userId) {
    router.push('/profile');
    return;
  }

  router.push(`/profile/${user.value?.id}`);
};

</script>

<template>
  <template v-if="isPending">
    <SellerInfoSkeleton />
  </template>
  <div v-else-if="isError">
    <p>Feil: {{ error?.message }}</p>
  </div>
  <div class="seller-container" v-else>
    <div class="seller-left">
      <div class="seller-wrapper" @click="handleProfileClick">
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
    </div>
    <Button variant="outline" class="contact-button" @click="handleContactClick"
      v-if="props.canContactSeller && user?.id !== auth.userId">{{
        $t("contactSeller") }}</Button>
  </div>

</template>

<style scoped>
.seller-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  outline: black;
  width: 100%;
  height: 8rem;
  border-radius: 5px;
  border: 1px solid black;
  background-color: white;
}

.seller-container.centered {
  justify-content: center;
}

.seller-wrapper {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  gap: 1rem;
  cursor: pointer;
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
