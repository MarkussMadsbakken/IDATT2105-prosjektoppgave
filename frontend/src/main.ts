import './assets/main.css'

import { createApp, devtools } from 'vue'
import { createPinia } from 'pinia'
import PrimeVue from 'primevue/config';
import Aura from '@primeuix/themes/aura';
import App from './App.vue'
import router from './router'
import { VueQueryPlugin } from '@tanstack/vue-query'
import i18n from "@/translations.ts";
import { useAuth } from './stores/auth';
import DialogService from 'primevue/dialogservice';
import ToastService from 'primevue/toastservice';

const app = createApp(App)

app.use(createPinia())

const auth = useAuth();

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!auth.isLoggedIn()) {
            next({ name: 'login' });
        } else if (to.matched.some(record => record.meta.requiresAdmin)) {
            if (!auth.isAdmin) {
                next({ name: 'me' });
            } else {
                next();
            }
        } else {
            next();
        }

    } else if (to.matched.some(record => record.meta.requiresUnauthorized)) {
        if (auth.isLoggedIn()) {
            next({ name: 'me' });
        } else {
            next();
        }
    } else {
        next();
    }
});

app.use(router)
app.use(VueQueryPlugin)

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
});

app.use(i18n);

// PrimeVue
app.use(PrimeVue, {
    theme: {
        preset: Aura,
    }

});
app.use(DialogService);
app.use(ToastService);

app.mount('#app')
