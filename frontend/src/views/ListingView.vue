<script setup lang="ts">
import { useRoute } from 'vue-router'
import type { Listing, User } from '@/types'
import ListingPage from "@/components/ListingPage.vue";


const users: User[] = [
  {id: 1, username:"Jackyboy", firstName: "Jacob", lastName:"Lein", createdAt:new Date('2002-08-12'), isAdmin: false},
  {id: 1, username:"MattiBattiboy", firstName: "Markus", lastName:"Madsbakken", createdAt:new Date('2004-01-13'), isAdmin: false}
]
// Dummy-data for testing

let refrigeratorImages: string[];
refrigeratorImages = [
  "https://images.thdstatic.com/productImages/239316ec-24ed-4800-b409-fb93b4b699cb/svn/stainless-steel-frigidaire-top-freezer-refrigerators-frtd2021as-64_600.jpg",
  "https://media.us.lg.com/transform/6e5c652e-0555-486a-b1bf-7744b96df896/bottom-freezer_herobanner_900x600?io=transform:fill,width:752",
  "https://www.maytag.com/is/image/content/dam/business-unit/maytag/en-us/marketing-content/site-assets/page-content/oc-articles/guide-to-refrigerator-sizes-dimensions/guide-to-refrigerator-sizes-dimensions_Image1M.jpg?fmt=png-alpha&qlt=85,0&resMode=sharp2&op_usm=1.75,0.3,2,0&scl=1&constrain=fit,1",
  "https://media.licdn.com/dms/image/v2/D4D03AQHjTY6wQxkVmw/profile-displayphoto-shrink_200_200/profile-displayphoto-shrink_200_200/0/1710778267251?e=2147483647&v=beta&t=gFtp0-oifVS5EF_u07o-CHmwDNmQYGzR-NYdUhecPcs"
];

const listings: Listing[] = [
  { id: "1", title: "Kult kjøleskap", seller:users[0], description: "Beskrivelse \n" +
      " \n" +
      "Velkommen til Max Hvitevarer As \n" +
      "Brukte hvitevarer med garanti! \n" +
      " \n" +
      "MAX hvitevarer As selger nyere hvitevarer fra 2690 kr \n" +
      "Alt selges med garantier, mest normalt fra 6-16 mnd \n" +
      "   \n" +
      "Vi har det meste i Komfyrer, Ovner, Vaskemaskiner, \n" +
      "Tørketromler, Oppvaskmaskiner, Kombiskap, m.m . \n" +
      "Alt som blir annonsert, kanskje vi har det du trenger \n" +
      " \n" +
      "Alle produkter er grundig sjekket, testet og overhalte. \n" +
      "Hvitevarene er grundig vasket/ rengjort og klar til bruk. \n" +
      " \n" +
      "Hjemkjøring: \n" +
      "Levering hver dag fra kl 10:00 til kl 20:00 i Oslo fra kr 499,- / 250,- retur \n" +
      "Tillegg for levering over/under 1 etg uten heis, 50,- pr vare. \n" +
      "Ring oss for levering alle andre steder på Østlandet \n" +
      "Hos oss kan du bestille varer over tlf og få de levert hjem \n" +
      " \n" +
      "Betaling: \n" +
      "Vi tar både kort, kontooverføring og kontanter. \n" +
      " \n" +
      " \n" +
      "Man-fre fra 11:00 til 18:00 \n" +
      "Lør fra 11:00 til 15:00 \n" +
      " \n" +
      "Tlf : 45804666", price: 60000000 , image: refrigeratorImages},
  { id: "2", title: "sjarmerende sofa", seller:users[1], description: "Komfortabel sofa.", price: 3500 },
  { id: "3", title: "elegant lampe", seller: users[1], description: "Elegant lampe.", price: 1500 }
]


const route = useRoute()
const listingId = route.params.id as string

const listing = listings.find(item => item.id === listingId)
</script>

<template>
  <div v-if="listing">
    <ListingPage :listing="listing" />
  </div>
  <div v-else>
    <p>Fant ikke annonsen.</p>
  </div>
</template>
