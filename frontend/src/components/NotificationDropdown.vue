<script setup lang="ts">
  import {computed, ref} from 'vue'
  import type { Notification } from "@/types/notification.ts";
  import {  Bell } from 'lucide-vue-next';
  import { onMounted, onBeforeUnmount } from "vue";

  const notifications = ref<Notification[]>(
    [{message:"Test1", link:"test2",read:false}]
  )

  const isOpen = ref(false)

  const toggleDropdown = () => {
    isOpen.value = !isOpen.value
  }


  const dropdownRef = ref<HTMLElement | null>(null)

  const handleClickOutside = (event: MouseEvent): void  =>{
      if (dropdownRef.value && !dropdownRef.value.contains(event.target as Node)){
        isOpen.value = false
      }
  }
  onMounted(()=> {
    document.addEventListener('click', handleClickOutside)
  })
  onBeforeUnmount(()=>{
    document.removeEventListener('click', handleClickOutside)
  })

  const unreadCount = computed(() => notifications.value.filter(n=>!n.read).length)

  const handleNotificationClick = (notification: Notification) => {
    if (!notification.read) {
      console.log("Setting true");
      notification.read = true;

    }
    isOpen.value=false;
    console.log("Timeout starting: ")
    setTimeout(()=>{
        window.location.href = notification.link;
    },300)


  }

</script>


<template>

  <div class="relative" ref="dropdownRef">
    <div class="bellWrapper" @click="toggleDropdown">
        <Bell class = "bell"/>
        <span v-if="unreadCount>0" class="notificationBadge">
          {{unreadCount}}
        </span>
    </div>
    <div v-if="isOpen" class="dropdown">
    <ul>
        <li
          v-if="notifications.length !== 0"
          v-for="notification in notifications"
          :key="notification.message"
          :class="['dropdownItem', { unread: !notification.read }]"
        >
          <a
            href="#"
            @click.prevent="handleNotificationClick(notification)"
          >
            {{ notification.message }}
            <span v-if= "!notification.read" class="dot"></span>
          </a>
        </li>
          <li v-if="notifications.length === 0" class ="dropdownEmpty">
          Ingen notifikasjoner
        </li>
      </ul>
    </div>
  </div>
</template>

<style>
 .bell{
   cursor: pointer;
 }
 .dropdown {
   position: absolute;
   top: 80%;
   right: 4.5rem;
   background-color: transparent;
   z-index: 1000;
   min-width: 200px;
   max-height: 300px;
   overflow-y: auto;
 }

 .dropdownItem{
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
.unread{
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
 .dropdownEmpty{
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

