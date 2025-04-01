<script setup lang="ts">
import { computed, ref } from 'vue'
import type { Notification } from "@/types/notification.ts";
import { Bell, X } from 'lucide-vue-next';
import { AnimatePresence, motion } from 'motion-v';
import Divider from './Divider.vue';

const notifications = ref<Notification[]>(
  [{ message: "Test1", link: "test2", read: false }]
)

const isOpen = ref(false)

const toggleDropdown = (e: MouseEvent) => {
  e.stopPropagation();
  isOpen.value = !isOpen.value
}

const dropdownRef = ref<HTMLElement | null>(null)

const handleClickOutside = (): void => {
  if (isOpen.value) {
    isOpen.value = false;
  }
}
const unreadCount = computed(() => notifications.value.filter(n => !n.read).length)

//TODO: Change this when backend is ready!
const handleNotificationClick = (notification: Notification) => {
  if (!notification.read) {
    notification.read = true;

  }
  isOpen.value = false;
  console.log("Timeout starting: ")
  setTimeout(() => {
    window.location.href = notification.link;
  }, 300)
}

const variants = {
  closed: {
    height: 0,
    transition: {
      duration: 0.05,
    }
  },
  open: {
    height: "auto",
    transition: {
      duration: 0.2
    }
  }
}
</script>


<template>

  <div class="outer-wrapper" ref="dropdownRef">
    <div class="bellWrapper" @click="toggleDropdown">
      <Bell class="bell" />
      <span v-if="unreadCount > 0" class="notificationBadge">
        {{ unreadCount }}
      </span>
    </div>
    <AnimatePresence>
      <motion.div v-if="isOpen" class="dropdown" :transition="{
        ease: 'circOut'
      }" layout v-click-outside="handleClickOutside" :variants="variants" initial="closed" animate="open"
        exit="closed">
        <div class="dropdown-inner-wrapper">
          <div class="title-wrapper">
            <div class="notification-title">
              {{ $t('notifications') }}
            </div>
            <X @click="toggleDropdown" class="close-button" />
          </div>
          <Divider />
          <div v-if="notifications.length !== 0" v-for="notification in notifications" :key="notification.message"
            :class="['dropdownItem', { unread: !notification.read }]">
            <a href="#" @click.prevent="handleNotificationClick(notification)">
              {{ notification.message }}
              <span v-if="!notification.read" class="dot"></span>
            </a>
            <div v-if="notifications.length === 0" class="dropdownEmpty">
              Ingen notifikasjoner
            </div>
          </div>
        </div>
      </motion.div>
    </AnimatePresence>
  </div>
</template>

<style scoped>
.title-wrapper {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  height: fit-content;
  margin-bottom: 1rem;
  width: 100%;
}

.notification-title {
  color: oklch(0.439 0 0);
  font-weight: 600;
  font-size: 1rem;
}

.close-button {
  position: absolute;
  right: -0.5rem;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
  color: black;
}

.dropdown-inner-wrapper {
  padding: 1rem;
  overflow: visible;
  position: relative;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.dropdown {
  position: absolute;
  top: calc(100% + 10px);
  left: 50%;
  transform: translateX(-50%);
  background-color: white;
  text-align: center;
  border-radius: 5px;
  overflow-y: scroll;
  overflow-x: hidden;
  border: 1px solid oklch(0.97 0 0);
  box-shadow: 0 20px 25px -5px rgb(0 0 0 / 0.1), 0 8px 10px -6px rgb(0 0 0 / 0.1);
}

.dropdownItem {
  line-height: 0;
  display: flex;
  flex-direction: row;
  border-radius: 5px;
  padding: 1rem;
  width: 15rem;
  height: 5rem;
  align-items: center;
  gap: 1rem;
}

.outer-wrapper {
  position: relative;
}

.bell {
  cursor: pointer;
}

.unread {
  font-weight: bold;
}

.dot {
  display: inline-block;
  margin-left: 8px;
  width: 8px;
  height: 8px;
  background-color: red;
  border-radius: 50%;
}

.bellWrapper {
  position: relative;
  display: inline-block;
  cursor: pointer;
}

.notificationBadge {
  position: absolute;
  top: -4px;
  right: -4px;
  background-color: red;
  color: white;
  border-radius: 50%;
  width: 18px;
  height: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.75rem;
  font-weight: bold;
}

.dropdownEmpty {
  color: black;
  line-height: 0;
  display: flex;
  flex-direction: row;
  border: 1px solid black;
  border-radius: 5px;
  padding: 1rem;
  width: 15rem;
  height: 5rem;
  align-items: center;
  gap: 1rem;
  transition: box-shadow;
  background-color: var(--color-secondaryGray);
}
</style>
