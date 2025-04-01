import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import PrimeVue from 'primevue/config';
import Aura from '@primeuix/themes/aura';

import App from './App.vue'
import router from './router'
import { VueQueryPlugin } from '@tanstack/vue-query'
import { createI18n } from 'vue-i18n'
import { useAuth } from './stores/auth';

const app = createApp(App)

app.use(createPinia())

const auth = useAuth();

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!auth.isLoggedIn()) {
            next({ name: 'login' })
        } else {
            next()
        }

    } else if (to.matched.some(record => record.meta.requiresUnauthorized)) {
        if (auth.isLoggedIn()) {
            next({ name: 'me' })
        } else {
            next()
        }
    } else {
        next()
    }
});

app.use(router)
app.use(VueQueryPlugin)
app.use(PrimeVue, {
    theme: {
        preset: Aura,
    }

})

const i18n = createI18n({
    locale: "no",
    fallbackLocale: "en",
    messages: {
        no: {
            recommended: "Anbefalt for deg",
            interior: "Interiør",
            electronics: "Elektronikk",
            appliances: "Hvitevarer",
            leisure: "Fritid",
            sports: "Sport",
            clothing: "Klær",
            transport: "Transport",
            garden: "Hage",
            username: "Brukernavn",
            password: "Passord",
            login: "Logg inn",
            register: "Lag en bruker",
            noAccount: "Har du ikke bruker?",
            haveAccount: "Har du allerede bruker?",
            registerHere: "Lag en bruker her",
            loginHere: "Logg inn her",
            logout: "Logg av",
            somethingWentWrong: "Noe gikk galt",
            search: "Søk",
            open: "Åpne",
            close: "Lukk",
            showCategories: "Vis kategorier",
            hideCategories: "Skjul kategorier",
            notifications: "Varslinger"
        },
        en: {
            recommended: "Recommended for you",
            interior: "Interior",
            electronics: "Electronics",
            appliances: "Appliances",
            leisure: "Leisure",
            sports: "Sports",
            clothing: "Clothing",
            transport: "Transport",
            garden: "Garden",
            username: "Username",
            password: "Password",
            login: "Login",
            register: "Register",
            noAccount: "Don't have an account?",
            haveAccount: "Already have an account?",
            registerHere: "Register here",
            loginHere: "Log in here",
            logout: "Log out",
            somethingWentWrong: "Something went wrong",
            search: "Search",
            open: "Open",
            close: "Close",
            showCategories: "Show categories",
            hideCategories: "Hide categories",
            notifications: "Notifications"
        }
    },
});

app.directive('click-outside', {
    beforeMount: (el, binding) => {
        el.clickOutsideEvent = (event: any) => {
            if (!(el == event.target || el.contains(event.target))) {
                binding.value();
            }
        };
        document.addEventListener("click", el.clickOutsideEvent);
    },
    unmounted: el => {
        document.removeEventListener("click", el.clickOutsideEvent);
    },
})

app.use(i18n);

app.mount('#app')
