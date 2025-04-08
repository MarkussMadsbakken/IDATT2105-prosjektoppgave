<script setup lang="ts">
import Button from '@/components/Button.vue';
import { useRouter } from "vue-router";
import UserImage from './UserImage.vue';
import { useGetUser } from '@/actions/user';
import { computed } from 'vue';
import { useAuth } from '@/stores/auth';
import SellerInfoSkeleton from './skeleton/SellerInfoSkeleton.vue';

const props = withDefaults(defineProps<{
  userId: number;
  canContactSeller?: boolean;
  size?: 'small' | 'medium';
}>(),{
  canContactSeller: true,
  size: "medium"
});

const emit = defineEmits<{
  (e: 'contact-seller'): void;
}>();

const router = useRouter();
const auth = useAuth();

const { data: user, isPending, isError, error } = useGetUser(props.userId);


const date = computed(() => new Date(user?.value?.createdAt!).getFullYear());
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
  <div class="seller-container" :class="[props.size, { centered: !props.canContactSeller }]" v-else>
  <div class="seller-left">
      <div class="seller-wrapper" @click="handleProfileClick">
        <UserImage :user-id="user?.id!" />
        <div class="seller-info" :class="{ centered: !props.canContactSeller }">
          <div class="seller-names">
            <div class="seller-name">{{ user?.firstName }} {{ user?.lastName }}</div>
            <div class="username">{{ user?.username }}</div>
          </div>
          <div class="seller-meta">
            <span class="joined-site">{{ $t('memberSince', {date}) }}</span>
          </div>
        </div>
      </div>
    </div>
    <Button variant="outline" class="contact-button" @click="handleContactClick"
            v-if="props.canContactSeller && props.size !== 'small' && user?.id !== auth.userId">{{
        $t("contactSeller") }}</Button>
  </div>

</template>

<style scoped>
.seller-container.medium {
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
.seller-container.small{
  display: flex;
  justify-content: space-between;
  align-items: center;
  outline: black;
  border: 1px solid black;
  border-radius: 5px;
  width: 25rem;
  height: 8rem;
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
  justify-content: center;
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  gap: 1rem;
}

.seller-info.centered {
  margin: 0 auto;
  transform: none;
  position: static;
}
.seller-container.small .seller-wrapper {
  margin-left: -3rem;
}

.seller-name {
  margin-top: -0.2rem;
  font-weight: bold;
  font-size: 2rem;
  max-width: 10rem;
}
.seller-names {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 0rem;
}

.seller-name,
.username {
  max-width: 10rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  display: inline-block;
}

.seller-name.small,
.username.small {
  max-width: 10rem;
  font-size: 10px;
}

.seller-name.medium,
.username.medium {
  max-width: 12rem;
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
