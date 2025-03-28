import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import { VueQueryPlugin } from '@tanstack/vue-query'
import { createI18n } from 'vue-i18n'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(VueQueryPlugin)

const i18n = createI18n({
    locale: "no",
    fallbackLocale: "en",
    messages: {
        no: {
            recommended: "Anbefalinger",
            interior: "Interiør",
            electronics: "Elektronikk",
            appliances: "Hvitevarer",
            leisure: "Fritid",
            sports: "Sport",
            clothing: "Klær",
            transport: "Transport",
            garden: "Hage"

        },
        en: {
            recommended: "Recommended",
            interior: "Interior",
            electronics: "Electronics",
            appliances: "Appliances",
            leisure: "Leisure",
            sports: "Sports",
            clothing: "Clothing",
            transport: "Transport",
            garden: "Garden"
        }
    },
});

app.use(i18n);

app.mount('#app')
