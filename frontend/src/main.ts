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
import DialogService from 'primevue/dialogservice';

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
            listing: "annonse",
            thisListing: "annonsen",
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
            notifications: "Varslinger",
            messages: "Meldinger",
            contactSeller: "Kontakt selger",
            buy: "Kjøp",
            reserve: "Reserver",
            createListing: "Opprett en ny annonse",
            title: "Tittel",
            description: "Beskrivelse",
            price: "Pris",
            postalCode: "Postnummer",
            image: "Bilde",
            create: "Opprett",
            category: "Kategori",
            uploadedImages: "Opplastede bilder",
            releaseToUpload: "Slipp for å velge bilde",
            chooseImage: "Velg bilde",
            imageSelected: "Bilde valgt!",
            name: "Navn",
            thisFieldIsRequired: "Dette feltet er påkrevd",
            subCategories: "Underkategorier",
            subCategory: "Underkategori",
            noSubCategoriesFound: "Ingen underkategorier funnet",
            createNewSubCategory: "Opprett ny underkategori",
            noMoreListings: "Ingen flere annonser funnet",
            listingsByUser: "{name} sine annonser",
            ownListings: "Mine annonser",
            noSubCategories: "Ingen underkategorier",
            selectCategory: "Velg kategori",
            selectSubCategory: "Velg underkategori",
            listingHasNoDescriptionLong: "Annonsen har ingen beskrivelse. Legg til en beskrivelse via \"Rediger\"-knappen.",
            areYouSureYouWantToDelete: "Er du sikker på at du har lyst til å slette denne {content}?",
            edit: "Rediger",
            delete: "Slett",
            listingIsDeleted: "Denne annonsen er slettet",
            listingIsInactive: "Denne annonsen er inaktiv"
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
            notifications: "Notifications",
            messages: "Messages",
            contactSeller: "Contact seller",
            buy: "Buy",
            reserve: "Reserve",
            createListing: "Create a new listing",
            title: "Title",
            description: "Description",
            price: "Price",
            postalCode: "Postal code",
            image: "Image",
            create: "Create",
            category: "Category",
            uploadedImages: "Uploaded images",
            releaseToUpload: "Release to upload",
            chooseImage: "Choose image",
            imageSelected: "Image selected!",
            name: "Name",
            thisFieldIsRequired: "This field is required",
            subCategories: "Sub categories",
            subCategory: "Sub category",
            noSubCategoriesFound: "No sub categories found",
            createNewSubCategory: "Create new sub category",
            noMoreListings: "No more listings found",
            listingsByUser: "Listings by {name}",
            ownListings: "My listings",
            noSubCategories: "No sub categories",
            selectCategory: "Select category",
            selectSubCategory: "Select sub category",
            listingHasNoDescriptionLong: "This listing has no description. Add a description via the \"Edit\" button.",
            areYouSureYouWantToDelete: "Are you sure you want to delete {content}?",
            listing: "listing",
            thisListing: "this listing",
            edit: "Edit",
            delete: "Delete",
            listingIsDeleted: "This listing is deleted",
            listingIsInactive: "This listing is inacitve"
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
});

app.use(i18n);
app.use(DialogService)

app.mount('#app')
