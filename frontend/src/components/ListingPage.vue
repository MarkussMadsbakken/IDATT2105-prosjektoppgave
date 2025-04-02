<script setup lang="ts">
import type { Listing } from '@/types';
import SellerInfo from "@/components/SellerInfo.vue";
import Button from '@/components/Button.vue';
import { useRouter } from "vue-router";
import PhotoGallery from "@/components/PhotoGallery.vue";
import { marked } from 'marked';
import { Trash2, Pencil, Bookmark, BookmarkCheck} from 'lucide-vue-next'
import {useAuth} from "@/stores/auth.ts";
import { ref } from 'vue'

const router = useRouter();
const props = defineProps<{ listing: Listing }>();
const parsedDescription = marked(props.listing.description || '')
const handleContactClick = () => {
  // TODO
  router.push(`/buy/${props.listing.id}`);
};

const auth= useAuth();

const handleReserve = () => {
  // TODO
};
const bookmarked= ref(false);
const toggleBookmark = () => {
  // TODO
  bookmarked.value = !bookmarked.value
}
</script>
<template>

  <div class="listing">

    <div class="titlePicture">
      <h3 class="pageTitle">
          {{ props.listing.title }}
      </h3>
      <PhotoGallery
        :images="Array.isArray(props.listing.image)
      ? props.listing.image
      : props.listing.image ? [props.listing.image] : []"
      />
      <div class="pictureFooting">
        <div class="listingPrice">{{ props.listing.price }},-</div>
        <div v-if="auth.isLoggedIn()" class="footingButtons">
          <div class="editDelete">
            <Button variant="outline">
              Rediger
              <Pencil :size="18" style="margin-left: 0.5rem;"/>
            </Button>
            <Button variant="destructive">
              Slett
              <Trash2 :size="18" style="margin-left: 0.5rem;"/>
            </Button>
          </div>
          <div @click="toggleBookmark" style="cursor: pointer">
            <Bookmark class="Bookmark" v-if="!bookmarked" :size="38"/>
            <BookmarkCheck v-else :size="38"/>
          </div>
        </div>
      </div>
      <div class="listingDescription" v-html="parsedDescription"></div>
    </div>
    <div class="buyBox">
      <SellerInfo :user-entity="listing.seller" />
      <div v-if="auth.isLoggedIn()" class="buttonBox">
        <Button variant="primary"  style="width: 10rem; height: 3rem;" @click="handleContactClick">Kjøp</Button>
        <Button variant="secondary"  style="width: 10rem; height: 3rem;" @click="handleContactClick">Reserver</Button>
      </div>
    </div>
  </div>

</template>

<style scoped>
.listing {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 2rem;
}
.listingTitle{
  font-size: 5rem;
  font-weight: bold;
}
.buttonBox{
  display: flex;
  flex-direction: row;
  gap: 2rem;
}
.buyBox{
  display:flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;
}
.listingDescription{
  max-width: 55rem;
  font-size: 20px;
  padding: 2px;
  margin-bottom: 2rem;
}
.titlePicture{
  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;
}
.pictureFooting{
  display: flex;
  flex-direction: row;
  max-width: 45rem;
  justify-content: center;
  width: 100%;
  align-items: center;
}
.footingButtons{
  display: flex;
  flex-direction: row;
  gap: 0.5rem;
  margin-left: 5rem;
}
.listingPrice{
  font-size: 3rem;
  font-weight: bold;
}
.editDelete{
  display: flex;
  flex-direction: row;
  gap: 2rem;
}
</style>
