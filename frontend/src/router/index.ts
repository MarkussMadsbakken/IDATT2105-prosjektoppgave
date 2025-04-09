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
      path: "/search/map",
      name: "searchMap",
      component: () => import('../views/MapView.vue'),
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
      component: () => import('../views/MyProfileView.vue'),
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/profile/edit",
      name: "editProfile",
      component: () => import('../views/EditProfileView.vue'),
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
      path: "/profile/change-credentials",
      name: "editPassword",
      component: () => import('../views/EditCredentialsView.vue'),
    },
    {
      path: "/profile/:userid/listings",
      component: () => import("../views/UserListingsView.vue"),
      children: [
        {
          path: "",
          name: "listings",
          component: () => import("../views/userListings/ActiveListingsView.vue"),
        },
        {
          path: "archived",
          name: "archivedListings",
          component: () => import("../views/userListings/ArchivedListingsView.vue"),
        }
      ]
    }
    ,
    {
      path: "/profile/listings/create",
      name: "createListing",
      component: () => import("../views/CreateListingView.vue"),
      meta: {
        requiresAuth: true
      }
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
      path: "/chat",
      name: "chat",
      component: () => import("../views/MessagesView.vue"),
      meta: {
        requiresAuth: true
      },
      children: [
        {
          path: "/new/:listingId/:userId",
          name: "newChat",
          component: () => import("../views/chat/ChatView.vue"),
        },
        {
          path: "",
          name: "chatNotFound",
          component: () => import("../views/chat/SelectChatView.vue"),
        },
        {
          path: ":id",
          name: "chatroom",
          component: () => import("../views/chat/ChatView.vue"),
        },
      ]
    },
    {
      path: "/login",
      name: "login",
      component: () => import("../views/LoginView.vue"),
      meta: {
        requiresUnauthorized: true
      }
    },
    {
      path: "/register",
      name: "register",
      component: () => import("../views/RegisterView.vue"),
      meta: {
        requiresUnauthorized: true
      }
    },
    {
      path: "/admin",
      name: "admin",
      component: () => import("../views/AdminView.vue"),
      meta: {
        requiresAuth: true,
        requiresAdmin: true,
      }
    },
    { path: '/:pathMatch(.*)*', name: 'NotFound', component: () => import("../views/NotFoundView.vue") },
  ],
})

export default router
