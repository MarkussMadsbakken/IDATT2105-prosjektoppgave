<script setup lang="ts">
import { computed, ref, watch } from 'vue'
import { Bell, X } from 'lucide-vue-next';
import { AnimatePresence, motion } from 'motion-v';
import Divider from './Divider.vue';
import { useNotifications, useReadMultipleNotifications, useReadNotification } from '@/actions/notifications';
import type { Notification } from '@/types/notification';
import NotificationElement from './NotificationElement.vue';
import { useRouter } from 'vue-router';
import Button from './Button.vue';
import LoadingSpinner from './LoadingSpinner.vue';
import { useQueryClient } from '@tanstack/vue-query';
import { useWebSocket } from '@/actions/websocket';

const isOpen = ref(false);
const router = useRouter();
const queryClient = useQueryClient();

const toggleDropdown = (e: MouseEvent) => {
  e.stopPropagation();
  isOpen.value = !isOpen.value
}

const dropdownRef = ref<HTMLElement | null>(null);

const handleClickOutside = (): void => {
  if (isOpen.value) {
    isOpen.value = false;
  }
}

const { data: notificationsQuery, isError, error, isPending } = useNotifications();
const { mutate: readMultipleNotifications, isPending: readMultipleIsPending } = useReadMultipleNotifications();
const { mutate: readNotification } = useReadNotification();

const notifications = ref<Notification[] | null>(notificationsQuery.value ?? null);

watch(
  notificationsQuery,
  (newData) => {
    if (!newData) return;
    notifications.value = newData;
  }
);

const unreadCount = computed(() => {
  return notifications.value?.filter(notification => !notification.read).length ?? 0;
});

const ws = useWebSocket();

ws.subscribe(
  "/user/queue/notifications",
  (notification: Notification) => {
    if (!notifications.value) return;
    notifications.value = [...notifications.value, notification];
    queryClient.invalidateQueries({
      queryKey: ['notifications'],
    });
  }
);

const handleMarkAllAsRead = () => {
  if (!notifications.value) return;
  const unreadNotifications = notifications.value.filter(notification => !notification.read);
  if (unreadNotifications.length === 0) return;
  readMultipleNotifications(unreadNotifications.map(notification => notification.id), {
    onSettled: () => {
      queryClient.invalidateQueries({
        queryKey: ['notifications'],
      });
    }
  });
}

const handleNotificationClick = (notification: Notification) => {
  readNotification(notification.id, {
    onSettled: () => {
      queryClient.invalidateQueries({
        queryKey: ['notifications'],
      });
      router.push(notification.link);
      isOpen.value = false;
    }
  });
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
              {{ $t('notifications.notifications') }}
            </div>
            <X @click="toggleDropdown" class="close-button" />
          </div>
          <Divider />
          <div class="unread" v-if="unreadCount > 0">
            <div>
              {{ $t('notifications.unreadNotifications', unreadCount) }}
            </div>
            <Button class="mark-as-read-button" variant="outline" @click="handleMarkAllAsRead">
              <template v-if="readMultipleIsPending">
                <LoadingSpinner />
              </template>
              <template v-else>
                {{ $t('notifications.markAllAsRead') }}
              </template>
            </Button>
          </div>
          <div class="dropdown-notifications">
            <NotificationElement v-if="notifications && notifications.length > 0" v-for="notification in notifications"
              :key="notification.message + notification.id + notification.time" :notification="notification"
              @click.prevent="handleNotificationClick(notification)" />
            <div v-else class="dropdown-empty">
              <div>
                {{ $t('notifications.noNotifications') }}
              </div>
            </div>
          </div>
        </div>
      </motion.div>
    </AnimatePresence>
  </div>
</template>

<style scoped>
.dropdown-notifications {
  display: flex;
  flex-direction: column-reverse;
  gap: 0.5rem;
}

.unread {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 0.75rem;
  font-weight: 400;
  padding: 0.5rem;
  color: black;
}

.mark-as-read-button {
  width: 8rem;
  height: 1rem;
  font-size: 0.75rem;
  font-weight: 400;
}

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
  max-height: 40vh;
  overflow-y: scroll;
  overflow-x: hidden;
  border: 1px solid oklch(0.97 0 0);
  box-shadow: 0 20px 25px -5px rgb(0 0 0 / 0.1), 0 8px 10px -6px rgb(0 0 0 / 0.1);
}

.outer-wrapper {
  position: relative;
}

.bell {
  cursor: pointer;
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

.dropdown-empty {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1rem;
  color: black;
  line-height: 0;
  flex-direction: row;
  padding: 1rem;
  width: 15rem;
  height: 5rem;
  gap: 1rem;
}
</style>
