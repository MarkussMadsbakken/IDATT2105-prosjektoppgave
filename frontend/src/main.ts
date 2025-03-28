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
            recommended: "Anbefalinger"
        },
        en: {
            recommended: "Recommended"
        }
    },
})

app.use(i18n);

app.mount('#app')
