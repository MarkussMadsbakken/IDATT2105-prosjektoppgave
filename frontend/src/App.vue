<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import { UserRound, MessageSquare, Bell, SquarePlus } from 'lucide-vue-next';
import { useAuth } from './stores/auth';
import NotificationDropdown from "@/components/NotificationDropdown.vue";
const auth = useAuth();
</script>

<template>
  <header>
    <div class="background" />
    <nav>
      <div>
        <RouterLink to="/" class="link">
          <div class="logo">
            Logo
          </div>
        </RouterLink>
      </div>
      <div class="right-elements">
        <RouterLink to="/profile/listings/create" class="link" v-if="auth.isLoggedIn()">
          <SquarePlus />
        </RouterLink>

        <!-- Endre til en dropdown når komponenten er ferdig -->
        <div class="link" v-if="auth.isLoggedIn()">
          <NotificationDropdown></NotificationDropdown>
        </div>
        <RouterLink to="/messages" class="link" v-if="auth.isLoggedIn()">
          <MessageSquare />
        </RouterLink>
        <RouterLink :to="auth.isLoggedIn() ? '/profile' : '/login'" class="link profile">
          <div class="profile-wrapper">
            <UserRound :size="40" :stroke-width="1" />
          </div>
        </RouterLink>
      </div>
    </nav>
  </header>
  <main>
    <RouterView />
  </main>
</template>

<style scoped>
.link.profile {
  margin-left: 1rem;
}

.background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 95%;
  background-color: #1E6676;
}

.profile-wrapper {
  border: solid white;
  height: min-content;
  line-height: 0;
  padding: 0.5rem;
  border-radius: 999px;
}

.logo {
  padding: 1.5rem;
  border-radius: 15px;
  border: solid white;
}

.right-elements {
  display: flex;
  flex: row;
  gap: 2rem;
  height: fit-content;
  align-items: center;
}

.right-elements>* {
  height: fit-content;
}

.link {
  text-decoration-color: white;
  color: white;
  text-decoration-line: none;
}

header {
  position: sticky;
  top: 0;
  left: 0;
  width: 100%;
  height: fit-content;
  border: black;
  border-width: 1px;
  z-index: 999;
  line-height: 0;
}

nav {
  padding: 1rem;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  backdrop-filter: blur(20px);
}
</style>
