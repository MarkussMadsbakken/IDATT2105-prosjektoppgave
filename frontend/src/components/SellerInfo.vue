<script setup lang="ts">
import { UserIcon } from 'lucide-vue-next'
import type { User } from '@/types/user.ts'
import Button from '@/components/Button.vue';
import { useRouter } from "vue-router";


const router = useRouter();
const props = defineProps<{
    userEntity: User;
}>();


const fullName = `${props.userEntity.firstName} ${props.userEntity.lastName}`;
const joinedYear ="Medlem siden " +new Date(props.userEntity.createdAt).getFullYear();


const handleContactClick = () => {
  router.push(`/message/${props.userEntity.id}`);
};

</script>

<template>

  <div class="sellerContainer">
      <div class="sellerLeft">
        <div class="avatar">
            <img v-if="props.userEntity.profileImage" :src="props.userEntity.profileImage" alt="Profilbilde"/>
            <UserIcon v-else :size="60" stroke-width="1.5" />
        </div>
            <div class="sellerInfo">
                <div class="sellerName">{{fullName}}</div>
                <div class="sellerMeta">
                    <span class="joinedSite">{{joinedYear}}</span>
                </div>
            </div>
      </div>
    <Button variant="outline" class="contactButton" @click="handleContactClick">Kontakt selger</Button>
  </div>

</template>

<style>
.sellerContainer{
  display: flex;
  justify-content: flex-start;
  align-items: center;
  gap: 4rem;
  outline: black;
  border: 1px solid black;
  border-radius: 5px;
}
.sellerLeft{
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 1rem;
}
.avatar{
  width:100px;
  height: 100px;
  border-radius: 50%;
  background-color: transparent;
  display: flex;
  justify-content: center;
  align-items: center;
}
.sellerInfo{
  display:flex;
  flex-direction: column;
  gap: 0.25rem;
  justify-content: center;
  align-items: center;
}
.sellerName{
  font-weight: bold;
  font-size: 2rem;
}
.joinedSite{
  font-size: 1rem;
}
.contactButton{
  margin-right: 2rem;
}
</style>
