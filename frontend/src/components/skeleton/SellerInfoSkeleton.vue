<script setup lang="ts">
import UserImageSkeleton from './UserImageSkeleton.vue';

const props = defineProps<{
  canContactSeller?: boolean;
}>();

</script>



<template>
  <div class="seller-container">
    <div class="seller-left">
      <div class="seller-wrapper">
        <UserImageSkeleton />
        <div class="seller-info" :class="{ centered: !props.canContactSeller }">
          <div class="seller-names">
            <div class="seller-name-skeleton"></div>
            <div class="username-skeleton"></div>
          </div>
          <div class="seller-meta">
            <span class="joined-site-skeleton"></span>
          </div>
        </div>
      </div>
    </div>
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

.username-skeleton {
  margin-top: 0.25rem;
  width: 7rem;
  height: 1rem;
  background-color: var(--color-skeleton);
  animation: pulse 1.5s infinite;
  border-radius: 10px;
}

.seller-name-skeleton {
  margin-top: -0.2rem;
  font-weight: bold;
  font-size: 2rem;
  line-clamp: 1;
  width: 10rem;
  height: 2rem;
  background-color: var(--color-skeleton);
  animation: pulse 1.5s infinite;
  border-radius: 10px;
}

.seller-names {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 0rem;
}

.joined-site-skeleton {
  width: 5rem;
  height: 1rem;
  background-color: var(--color-skeleton);
  animation: pulse 1.5s infinite;
  border-radius: 10px;
  margin-top: 1rem;
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