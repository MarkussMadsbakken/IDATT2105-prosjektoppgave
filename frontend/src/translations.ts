import { createI18n } from "vue-i18n";

const listingViewTranslations = {
  no: {
    listingReservedByAnotherUser: "Artikkelen er reservert av en annen bruker fram til",
    listingReservedByMe: "Du har reservert artikkelen fram til",
    listingIsDeleted: "Denne annonsen er slettet",
    listingIsInactive: "Denne annonsen er inaktiv",
    edit: "Rediger",
    archiveThis: "Arkiver",
    restore: "Gjenoprett",
    listingHasNoDescriptionLong: "Annonsen har ingen beskrivelse. Legg til en beskrivelse via \"Rediger\"-knappen.",
    thisListing: "annonsen",
  },
  en: {
    listingReservedByAnotherUser: "Listing is reserved by another user until",
    listingReservedByMe: "You have reserved this listing until",
    listingIsDeleted: "This listing is deleted",
    listingIsInactive: "This listing is inacitve",
    edit: "Edit",
    archiveThis: "Archive",
    restore: "Restore",
    listingHasNoDescriptionLong: "This listing has no description. Add a description via the \"Edit\" button.",
    thisListing: "this listing",
  }
}
const listingTranslations = {
  no: {
    reservedUntil: "Reservert til",
    reserve: "Reserver",
    delete: "Slett",
    areYouSureYouWantToDelete: "Er du sikker på at du har lyst til å slette denne {content}?"
  },
  en: {
    reservedUntil: "Reserved until",
    reserve: "Reserve",
    delete: "Delete",
    areYouSureYouWantToDelete: "Are you sure you want to delete {content}?"
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
    error: "Feil",
    userDoesNotExist: "Brukeren finnes ikke",


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
    noFavorites: "No favorites found"
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
    profilePicture: "Profile picture"
  }
}
const homeViewTranslations = {
  no: {
    recommended: "Anbefalt for deg",
    noMoreListings: "Ingen flere annonser funnet",
  },
  en: {
    recommended: "Recommended for you",
    noMoreListings: "No more listings found"
  }
}
const checkoutTranslations = {
  no: {
    listingIsPurchased: "Denne annonsen er solgt",
    name: "Navn",
    cardNumber: "Kortnummer(Ikke skriv ekte)",
    buy: "Kjøp"
  },
  en: {
    listingIsPurchased: "This listing is purchased",
    name: "Name",
    cardNumber: "Card number (NOT real)",
    buy: "Purchase"
  }
}
const messageViewTranslations = {
  no: {
    messages: "Meldinger",
    noMessages: "Ingen meldinger funnet. Vær den første til å sende en melding!",
    error: "Feil. Kunne ikke hente meldinger"
  },
  en: {
    messages: "Messages",
    noMessages: "No messages found. Be the first to send a message!",
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
  },
  en: {
    username: "Username",
    password: "Password",
    noAccount: "Don't have an account?",
    register: "Register",
    login: "Login",
    somethingWentWrong: "Something went wrong",
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
  },
  en: {
    showCategories: "Show categories",
    search: "Search",
    hideCategories: "Hide categories",
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
    thisFieldIsRequired: "Dette feltet er påkrevd",
    selectSubCategory: "Velg underkategori",
    selectPosition: "Velg posisjon",
    position: "Posisjon"
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
    position: "Position"
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
    createListing: "Create listing"
  }
}

const i18n = createI18n({
  locale: 'no',
  fallbackLocale: 'en',
  messages: {
    no: {
      listings: {
        archive: archivedListingsTranslations.no,
        create: createListingViewTranslations.no,
        seller: sellerInfoTranslations.no,
        view: listingViewTranslations.no,
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
    }, en: {
      listings: {
        archive: archivedListingsTranslations.en,
        create: createListingViewTranslations.en,
        seller: sellerInfoTranslations.en,
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
    }
  }
});

const notUsedTranslations = {
  no: {
    listing: "annonse",
    interior: "Interiør",
    electronics: "Elektronikk",
    appliances: "Hvitevarer",
    leisure: "Fritid",
    sports: "Sport",
    clothing: "Klær",
    transport: "Transport",
    garden: "Hage",
    registerHere: "Lag en bruker her",
    loginHere: "Logg inn her",
    open: "Åpne",
    close: "Lukk",
    createNewSubCategory: "Opprett ny underkategori",
    finish: "Fullfør",
    purchaseAccomplished: "Kjøp fullført",
    userReservedYourListing: "En bruker har reservert annonsen din",
  },
  en: {
    interior: "Interior",
    electronics: "Electronics",
    appliances: "Appliances",
    leisure: "Leisure",
    sports: "Sports",
    clothing: "Clothing",
    transport: "Transport",
    garden: "Garden",
    registerHere: "Register here",
    loginHere: "Log in here",
    open: "Open",
    close: "Close",
    price: "Price",
    noSubCategoriesFound: "No sub categories found",
    createNewSubCategory: "Create new sub category",
    noSubCategories: "No sub categories",
    selectSubCategory: "Select sub category",
    listing: "listing",
    finish: "Finish",
  }
}
export default i18n;
