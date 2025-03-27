import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: "/search",
      name: "search",
      component: () => import('../views/SearchView.vue'),
    },
    {
      path: "/listing/:id",
      name: "listing",
      component: () => import('../views/ListingView.vue'),
    },
    {
      path: "/listing/:id/checkout",
      name: "checkout",
      component: () => import("../views/CheckoutView.vue"),
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/listing/:id/edit",
      name: "editlisting",
      component: () => import("../views/EditListing.vue"),
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/profile",
      name: "me",
      component: () => import('../views/SearchView.vue'),
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/profile/listings",
      name: "mylistings",
      component: () => import("../views/UserListingsView.vue"),
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/profile/:userid",
      name: "profile",
      component: () => import('../views/ProfileView.vue'),
    },
    {
      path: "/profile/:userid/listings",
      name: "listings",
      component: () => import("../views/UserListingsView.vue")
    },
    {
      path: "/favorites",
      name: "favorites",
      component: () => import("../views/FavoritesView.vue"),
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/messages",
      name: "messages",
      component: () => import("../views/MessagesView.vue"),
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/chat/:chatid",
      name: "chat",
      component: () => import("../views/ChatView.vue"),
      meta: {
        requiresAuth: true
      }
    },
    { path: '/:pathMatch(.*)*', name: 'NotFound', component: () => import("../views/NotFoundView.vue") },
  ],
})

export default router
