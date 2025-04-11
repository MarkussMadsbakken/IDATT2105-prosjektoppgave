import { createI18n } from "vue-i18n";

const listingViewTranslations = {
  no: {
    reservedByAnotherUser: "Artikkelen er reservert av en annen bruker fram til",
    reservedByMe: "Du har reservert artikkelen fram til",
    isDeleted: "Denne annonsen er slettet",
    isInactive: "Denne annonsen er inaktiv",
    edit: "Rediger",
    archiveThis: "Arkiver",
    restore: "Gjenoprett",
    noDescriptionLong: "Annonsen har ingen beskrivelse. Legg til en beskrivelse via \"Rediger\"-knappen.",
    thisListing: "denne annonsen",
    archived: "Annonsen er arkivert",
    restored: "Annonsen er gjenopprettet",
    archiveError: "Kunne ikke endre arkiveringsstatus",
    buy: "kjøp",
    description: "Beskrivelse",
    details: "Detaljer",
    published: "Publisert",
    reserved: "Annonsen ble reservert",
    deleteListing: "Slett annonse",
    seller: "Selger",
    position: "Posisjon",
    hasNoPosition: "Denne annonsen har ingen posisjon",
    editListing: "Rediger annonse",
    image: "Bilde",
    price: "Pris",
    category: "Kategori",
    title: "Tittel",
  },
  en: {
    reservedByAnotherUser: "Listing is reserved by another user until",
    reservedByMe: "You have reserved this listing until",
    isDeleted: "This listing is deleted",
    isInactive: "This listing is inacitve",
    edit: "Edit",
    archiveThis: "Archive",
    restore: "Restore",
    noDescriptionLong: "This listing has no description. Add a description via the \"Edit\" button.",
    archived: "Listing is archived",
    restored: "Listing is restored",
    archiveError: "Could not change archive status",
    buy: "buy",
    description: "Description",
    details: "Details",
    published: "Published",
    reserved: "Listing was reserved",
    thisListing: "this listing",
    deleteListing: "Delete listing",
    seller: "Seller",
    position: "Position",
    hasNoPosition: "This listing has no position",
    editListing: "Edit listing",
    image: "Image",
    price: "Price",
    category: "Category",
    title: "Title",
  }
}
const listingTranslations = {
  no: {
    reservedUntil: "Reservert til",
    reserve: "Reserver",
    delete: "Slett",
    areYouSureYouWantToDelete: "Er du sikker på at du har lyst til å slette denne {content}?",
    createNewCategory: "Opprett ny kategori",
    updated: "Annonsen ble oppdatert",
    deleted: "Annonsen ble slettet",
    reservedError: "Kunne ikke reservere annonsen",
    deleteError: "Kunne ikke slette annonsen",
  },
  en: {
    reservedUntil: "Reserved until",
    reserve: "Reserve",
    delete: "Delete",
    areYouSureYouWantToDelete: "Are you sure you want to delete {content}?",
    createNewCategory: "Create new category",
    updated: "The listing was updated",
    deleted: "The listing was deleted",
    reservedError: "Could not reserve the listing",
    deleteError: "Could not delete the listing",
  }
}
const userListingsTranslations = {
  no: {
    archivedListings: "Arkiverte arktikler"
  },
  en: {
    archivedListings: "Archived listings"
  }
}
const profileTranslations = {
  no: {
    memberSince: "Medlem siden {date}",
    logout: "Logg av",
    ownListings: "Mine annonser",
    listingsByUser: "{name} sine annonser",
    showAll: "Vis alle",
    couldNotLoadListings: "Kunne ikke hente annonser",
    emptyListings: "Her er det tomt",
    myFavorites: "Mine favoritter",
    firstName: "Fornavn",
    lastName: "Etternavn",
    saveChanges: "Lagre endringer",
    editProfile: "Rediger profil",
    noFavorites: "Ingen favoritter funnet",
    editPassword: "Endre passord",
    currentPassword: "Nåværende passord",
    newPassword: "Nytt passord",
    passwordUpdated: "Passordet ble oppdatert",
    repeatPassword: "Gjenta passord",
    savePassword: "Lagre passord",
    updateFailed: "Oppdatering feilet",
    credentialsUpdated: "Brukeropplysninger oppdatert",
    passwordMismatch: "Passordene stemmer ikke overens",
    error: "Feil",
    userDoesNotExist: "Brukeren finnes ikke",
    imageTooLarge: "Bilde er for stort",
    passwordCannotBeEmpty: "Passordet kan ikke være tomt",
    passwordMustContainLetter: "Passordet må inneholde minst en bokstav",
    passwordMustContainNumber: "Passordet må inneholde minst ett tall",
    passwordTooShort: "Passordet må være minst 8 tegn langt",
    profileUpdated: "Brukeropplysninger oppdatert",
  },
  en: {
    memberSince: "Member since {date}",
    logout: "Log out",
    ownListings: "My listings",
    listingsByUser: "Listings by {name}",
    showAll: "Show all",
    couldNotLoadListings: "Could not find listings",
    emptyListings: "No listings found",
    myFavorites: "My favorites",
    firstName: "First name",
    lastName: "Last name",
    saveChanges: "Save Changes",
    editProfile: "Edit profile",
    noFavorites: "No favorites found",
    editPassword: "Edit password",
    currentPassword: "Current password",
    newPassword: "New password",
    passwordUpdated: "Password updated",
    repeatPassword: "Repeat password",
    savePassword: "Save password",
    updateFailed: "Update failed",
    credentialsUpdated: "User credentials updated",
    passwordMismatch: "Passwords do not match",
    imageTooLarge: "Image is too large",
    passwordCannotBeEmpty: "Password cannot be empty",
    passwordMustContainLetter: "Password must contain at least one letter",
    passwordMustContainNumber: "Password must contain at least one number",
    passwordTooShort: "Password must be at least 8 characters long",
    profileUpdated: "User profile updated",
  }
}
const registerTranslations = {
  no: {
    register: "Lag en bruker",
    previous: "Forrige",
    next: "Neste",
    createAccount: "Opprett bruker",
    somethingWentWrong: "Noe gikk galt",
    haveAccount: "Har du allerede bruker?",
    login: "Logg inn",
    usernameTooShort: "Brukernavnet må være minst 3 tegn langt",
    usernameAvailable: "Brukernavnet er tilgjengelig",
    usernameNotAvailable: "Brukernavnet er ikke tilgjengelig",
    chooseUsername: "Velg brukernavn",
    choosePassword: "Velg passord",
    chooseProfileImage: "Velg profilbilde",
    profilePicture: "Profilbilde",
    chooseFirstName: "Velg fornavn",
    chooseLastName: "Velg etternavn",
    preview: "Forhåndsvisning",
  },
  en: {
    register: "Register",
    previous: "Previous",
    next: "Next",
    createAccount: "Create account",
    somethingWentWrong: "Something went wrong",
    haveAccount: "Already have an account?",
    login: "Login",
    usernameTooShort: "Username must be at least 3 characters long",
    usernameAvailable: "Username is available",
    usernameNotAvailable: "Username is not available",
    chooseUsername: "Choose username",
    choosePassword: "Choose password",
    chooseProfileImage: "Choose profile image",
    chooseFirstName: "Choose first name",
    chooseLastName: "Choose last name",
    preview: "Preview",
    profilePicture: "Profile picture",
  }
}
const homeViewTranslations = {
  no: {
    recommended: "Anbefalt for deg",
    noMoreListings: "Ingen flere annonser funnet",
    listings: "Annonser",
    adminPanel: "Admin panel",
    noListings: "Ingen annonser funnet",
  },
  en: {
    recommended: "Recommended for you",
    noMoreListings: "No more listings found",
    listings: "Listings",
    adminPanel: "Admin panel",
    noListings: "No listings found",
  }
}
const checkoutTranslations = {
  no: {
    listingIsPurchased: "Denne annonsen er solgt",
    name: "Navn",
    cardNumber: "Kortnummer(Ikke skriv ekte)",
    buy: "Kjøp",
    inSummary: "Oppsummert",
  },
  en: {
    listingIsPurchased: "This listing is purchased",
    name: "Name",
    cardNumber: "Card number (NOT real)",
    buy: "Purchase",
    inSummary: "In summary",
  }
}
const messageViewTranslations = {
  no: {
    messages: "Meldinger",
    noMessages: "Ingen meldinger funnet. Kontakt en bruker gjennom en annonse for å starte en chat.",
    error: "Feil. Kunne ikke hente meldinger"
  },
  en: {
    messages: "Messages",
    noMessages: "No messages found. Contact a user through a listing to start a chat.",
    error: "Error. Could not fetch messages"
  }

}
const loginTranslation = {
  no: {
    username: "Brukernavn",
    password: "Passord",
    noAccount: "Har du ikke bruker?",
    register: "Registrer deg",
    login: "Logg inn",
    somethingWentWrong: "Noe gikk galt",
    badCredentials: "Feil brukernavn eller passord",
  },
  en: {
    username: "Username",
    password: "Password",
    noAccount: "Don't have an account?",
    register: "Register",
    login: "Login",
    somethingWentWrong: "Something went wrong",
    badCredentials: "Bad credentials",
  }
}
const sellerInfoTranslations = {
  no: {
    contactSeller: "Kontakt selger"
  },
  en: {
    contactSeller: "Contact seller"
  }
}
const archivedListingsTranslations = {
  no: {
    archivedListing: "Denne artikkelen er arkivert",
    activeListings: "Aktive aritkler",
  },
  en: {
    archivedListing: "This listing is archived.",
    activeListings: "Active listings",

  }
}
const searchTranslations = {
  no: {
    showCategories: "Vis kategorier",
    search: "Søk",
    hideCategories: "Skjul kategorier",
    categories: "Kategorier",
    users: "Brukere",
    advancedSearch: "Avansert søk",
    noSubCategories: "Ingen underkategorier funnet",
    noResultsFound: "Ingen resultater funnet",
    searchLoadMore: "Last flere resultater",
  },
  en: {
    showCategories: "Show categories",
    search: "Search",
    hideCategories: "Hide categories",
    categories: "Categories",
    users: "Users",
    advancedSearch: "Advanced search",
    noSubCategories: "No sub categories found",
    noResultsFound: "No results found",
    searchLoadMore: "Load more results",
  }
}
const notFoundTranslations = {
  no: {
    pageNotFound: "Fant ikke siden du lette etter",
    backToHomePage: "Tilbake til hjemside",
  },
  en: {
    pageNotFound: "Could not find this page",
    backToHomePage: "Back to home page",
  }
}
const categorySelectorTranslations = {
  no: {
    selectCategory: "Velg kategori",
    subCategories: "Underkategorier",
  },
  en: {
    selectCategory: "Select category",
    subCategory: "Underkategori",
  }
}
const articleSummaryTranslations = {
  no: {
    inSummary: "Oppsummering"
  },
  en: {
    inSummary: "In summary"
  }
}

const formTranslations = {
  no: {
    error: "Feil",
    imageTooLarge: "Bilde er for stort (maks 1MB)",
    releaseToUpload: "Slipp for å laste opp",
    imageSelected: "Bilde valgt",
    chooseImage: "Velg bilde",
    uploadedImages: "Opplastede bilder",
    thisFieldIsRequired: "Dette feltet er påkrevd",
    success: "Suksess",
    cancel: "Avbryt",
    confirm: "Bekreft",
    areYouSureYouWantToDelete: "Er du sikker på at du vil slette {content}?",
    delete: "Slett",
    save: "Lagre",
    choose: "Velg",
    create: "Opprett",
  },
  en: {
    error: "Error",
    imageTooLarge: "Image is too large (max 1MB)",
    releaseToUpload: "Release to upload",
    imageSelected: "Image selected",
    chooseImage: "Choose image",
    uploadedImages: "Uploaded images",
    thisFieldIsRequired: "This field is required",
    success: "Success",
    cancel: "Cancel",
    confirm: "Confirm",
    delete: "Slett",
    save: "Save",
    choose: "Choose",
    create: "Create",
  }

}

const createListingViewTranslations = {
  no: {
    createListing: "Opprett en ny annonse",
    image: "Bilde",
    title: "Tittel",
    description: "Beskrivelse",
    price: "Pris",
    postalCode: "Postnummer",
    category: "Kategori",
    create: "Opprett",
    chooseImage: "Velg bilde",
    uploadedImages: "Opplastede bilder",
    releaseToUpload: "Slipp for å velge bilde",
    imageSelected: "Bilde valgt!",
    noSubCategoriesFound: "Ingen underkategorier funnet",
    noCategoriesFound: "Ingen kategorier funnet",
    thisFieldIsRequired: "Dette feltet er påkrevd",
    selectSubCategory: "Velg underkategori",
    selectPosition: "Velg posisjon",
    position: "Posisjon",
    submit: "Send",
    reserved: "Reservert",
    subCategories: "Underkategorier",
  },
  en: {
    createListing: "Create a new listing",
    image: "Image",
    title: "Title",
    description: "Description",
    price: "Price",
    postalCode: "Postal code",
    category: "Category",
    create: "Create",
    chooseImage: "Choose image",
    uploadedImages: "Uploaded images",
    releaseToUpload: "Release to upload",
    imageSelected: "Image selected!",
    noSubCategories: "Ingen underkategorier",
    thisFieldIsRequired: "This field is required",
    subCategories: "Sub categories",
    subCategory: "Sub category",
    selectPosition: "Select position",
    position: "Position",
    noCategoriesFound: "No categories found",
    submit: "Submit",
  }
}
const notificationTranslations = {
  no: {
    notifications: "Varslinger",
    unreadNotifications: "Du har ingen uleste varslinger | Du har {count} ulest varsling | Du har {count} uleste varslinger",
    markAllAsRead: "Marker alle som lest",
    noNotifications: "Du har ingen varslinger",
    newBookmark: "En bruker har bokmerket annonsen din",
    userPurchasedYourListing: "En bruker har kjøpt annonsen din",
    chatNotSelectedDescription: "Velg en chat for å se meldinger",
    userReservedYourListing: "En bruker har reservert annonsen din",
    chatNotSelected: "Ingen chat valgt",
  },
  en: {
    notifications: "Notifications",
    unreadNotifications: "You have no unread notifications | You have {count} unread notification | You have {count} unread notifications",
    markAllAsRead: "Mark all as read",
    noNotifications: "You have no notifications",
    newBookmark: "A user has bookmarked your listing",
    purchaseAccomplished: "Purchase accomplished!",
    userReservedYourListing: "A user has reserved your listing",
    userPurchasedYourListing: "A user has purchased your listing",
    chatNotSelected: "No chat selected",
    chatNotSelectedDescription: "Select a chat to see messages",
  }
}
const chatTranslations = {
  no: {
    noChats: "Ingen chatter funnet. Kontakt en selger for å starte en chat.",
    youHaveNotSentMessage: "Du har ikke sendt en melding enda",
    youHaveNotReceivedMessage: "Du har ikke mottatt en melding enda",

  },
  en: {
    noChats: "No chats found. Contact a seller to start a chat.",
    youHaveNotSentMessage: "You have not sent a message yet",
    youHaveNotReceivedMessage: "You have not received a message yet",
  }

}
const mapTranslations = {
  no: {
    searchAddress: "Søk etter adresse",
    selectPosition: "Velg posisjon",
    noResults: "Ingen resultater funnet",
    cancel: "Avbryt",
  },
  en: {
    searchAddress: "Search for address",
    selectPosition: "Select position",
    noResults: "No results found",
    cancel: "Cancel",
  }
}

const breadcrumbTranslations = {
  no: {
    home: "Hjem",
    profile: "Profil",
    listings: "Annonser",
    myFavorites: "Mine favoritter",
    myListings: "Mine annonser",
    messages: "Meldinger",
    notifications: "Varslinger",
    checkout: "Kjøp",
    chat: "Chat",
    editProfile: "Rediger profil",
    createListing: "Opprett annonse",
    editCredentials: "Endre passord",
  },
  en: {
    home: "Home",
    profile: "Profile",
    listings: "Listings",
    myFavorites: "My favorites",
    myListings: "My listings",
    messages: "Messages",
    notifications: "Notifications",
    checkout: "Checkout",
    chat: "Chat",
    editProfile: "Edit profile",
    createListing: "Create listing",
    editCredentials: "Edit credentials",
  }
}
const categoryTranslations = {
  no: {
    deleteCategory: "Slett kategori",
    areYouSureYouWantToDelete: "Er du sikker på at du vil slette denne kategorien?",
    createNewCategory: "Opprett ny kategori",
    choose: "Velg",
    icon: "Ikon"
  },
  en: {
    deleteCategory: "Delete category",
    areYouSureYouWantToDelete: "Are you sure you want to delete this category?",
    createNewCategory: "Create new category",
    choose: "Choose",
    icon: "Icon"
  }
}

const adminTranslations = {
  no: {
    adminPanel: "Admin panel",
    categories: "Kategorier",
    createNewCategory: "Opprett ny kategori",
    crateCategory: "Opprett kategori",
    name: "Navn",
    description: "Beskrivelse",
    icon: "Ikon",
    subCategories: "Underkategorier",
    noSubCategoriesFound: "Ingen underkategorier funnet",
    createNewSubCategory: "Opprett ny underkategori",
    createSubCategory: "Opprett underkategori",
    edit: "Rediger {name}",
    deleteCategory: "Slett kategori",
    areYouSureYouWantToDelete: "Er du sikker på at du vil slette {name}?",
    save: "Lagre",
    noCategoriesFound: "Fant ingen kategorier",
  },
  en: {
    adminPanel: "Admin panel",
    categories: "Categories",
    createNewCategory: "Create new category",
    crateCategory: "Create category",
    name: "Name",
    description: "Description",
    icon: "Icon",
    subCategories: "Sub categories",
    noSubCategoriesFound: "No sub categories found",
    createNewSubCategory: "Create new sub category",
    createSubCategory: "Create sub category",
    edit: "Edit {name}",
    deleteCategory: "Delete category",
    areYouSureYouWantToDelete: "Are you sure you want to delete {name}?",
    save: "Save",
    noCategoriesFound: "Could not find any categories",
  }
}

const availableLocales = ['no', 'en']
const browserLocale = (navigator.language || 'en').split('-')[0]
const mappedLocale = browserLocale === 'nb' ? 'no' : browserLocale;
const locale = availableLocales.includes(mappedLocale) ? mappedLocale : 'en';

const i18n = createI18n({
  legacy: false, // if you're using Composition API
  locale: locale,
  fallbackLocale: 'no',
  messages: {
    no: {
      listings: {
        archive: archivedListingsTranslations.no,
        create: createListingViewTranslations.no,
        seller: sellerInfoTranslations.no,
        view: listingViewTranslations.no,
        category: categoryTranslations.no,
        ...listingTranslations.no,

      },
      profile: {
        ...profileTranslations.no,
        listings: userListingsTranslations.no,
      },
      home: homeViewTranslations.no,
      map: mapTranslations.no,
      checkout: checkoutTranslations.no,
      notFound: notFoundTranslations.no,
      messages: {
        ...messageViewTranslations.no,
        chat: chatTranslations.no
      },
      notifications: notificationTranslations.no,
      login: loginTranslation.no,
      register: registerTranslations.no,
      search: {
        categorySelector: categorySelectorTranslations.no,
        ...searchTranslations.no,
      },
      breadcrumb: breadcrumbTranslations.no,
      form: formTranslations.no,
      admin: adminTranslations.no,
      ...formTranslations.no,
    }, en: {
      listings: {
        archive: archivedListingsTranslations.en,
        create: createListingViewTranslations.en,
        view: listingViewTranslations.en,
        seller: sellerInfoTranslations.en,
        category: categoryTranslations.en,
        ...listingTranslations.en,
      },
      profile: {
        ...profileTranslations.en,
        listings: userListingsTranslations.en,
      },
      home: homeViewTranslations.en,
      map: mapTranslations.en,
      checkout: checkoutTranslations.en,
      notFound: notFoundTranslations.en,
      messages: {
        ...messageViewTranslations.en,
        chat: chatTranslations.en
      },
      notifications: notificationTranslations.en,
      login: loginTranslation.en,
      register: registerTranslations.en,
      search: {
        categorySelector: categorySelectorTranslations.en,
        ...searchTranslations.en,
      },
      breadcrumb: breadcrumbTranslations.en,
      form: formTranslations.en,
      admin: adminTranslations.en,
      ...formTranslations.en,
    }
  }
});

export default i18n;
