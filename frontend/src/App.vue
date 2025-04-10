<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import { MessageSquare, SquarePlus, ShieldUser } from 'lucide-vue-next';
import { useAuth } from './stores/auth';
import NotificationDropdown from "@/components/NotificationDropdown.vue";
import DynamicDialog from 'primevue/dynamicdialog';
import { useI18n } from 'vue-i18n';
import UserImage from './components/UserImage.vue';
import Toast from 'primevue/toast';
const auth = useAuth();
const i18n = useI18n();

const changeLanguage = (lang: string) => {
  i18n.locale.value = lang;
}

</script>

<template>
  <header>
    <div class="background" />
    <nav>
      <div class="left-elements">
        <RouterLink to="/" class="link">
          <div class="logo">
            Logo
          </div>
        </RouterLink>
        <div class="translation-selector">
          <div class="translation-button" v-for="lang in i18n.availableLocales" :key="lang"
            @click="changeLanguage(lang)" :style="{ textDecoration: i18n.locale.value === lang ? 'underline' : '' }">
            {{ lang }}
          </div>
        </div>
      </div>
      <div class="right-elements">
        <RouterLink to="/admin" class="link" v-if="auth.isAdmin">
          <div class="admin-link">
            Admin
            <ShieldUser stroke-width="1px" />
          </div>
        </RouterLink>

        <RouterLink to="/profile/listings/create" class="link" v-if="auth.isLoggedIn()" data-testid="create-listing-link">
          <SquarePlus />
        </RouterLink>

        <div class="link" v-if="auth.isLoggedIn()">
          <NotificationDropdown></NotificationDropdown>
        </div>
        <RouterLink to="/chat" class="link" v-if="auth.isLoggedIn()">
          <MessageSquare />
        </RouterLink>
        <RouterLink :to="auth.isLoggedIn() ? '/profile' : '/login'" class="link profile">
          <UserImage :user-id="auth.userId ?? 0" :size="60" :key="auth.userId" />
        </RouterLink>
      </div>
    </nav>
  </header>
  <div class="color-wrapper">
    <main class="main">
      <DynamicDialog />
      <Toast position="bottom-right" />
      <RouterView />
    </main>
  </div>
</template>

<style scoped>
.translation-selector {
  display: flex;
  gap: 1rem;
  color: white;
}

.left-elements {
  display: flex;
  flex-direction: row;
  gap: 2rem;
  align-items: center;
}

.admin-link {
  display: flex;
  gap: 0.2rem;
  align-items: center;
}

.color-wrapper {
  background-color: var(--color-background);
}

.main {
  min-height: 80vh;
}

.translation-button {
  cursor: pointer;
  user-select: none;
  font-size: 1rem
}

.translation-selector {
  margin-right: 1rem;
  display: flex;
  justify-content: right;
  gap: 1rem;
}

.footer {
  padding: 1rem;
  background-color: #1E6676;
  color: white;
}

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
  z-index: -1;
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
