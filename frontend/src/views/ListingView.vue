<script setup lang="ts">
import type { Listing, User } from '@/types'
import SellerInfo from "@/components/SellerInfo.vue";
import Button from '@/components/Button.vue';
import { useRouter, useRoute } from "vue-router";
import PhotoGallery from "@/components/PhotoGallery.vue";
import { marked } from 'marked';
import { Trash2, Pencil, Bookmark, BookmarkCheck } from 'lucide-vue-next'
import { useAuth } from "@/stores/auth.ts";
import { computed, ref, watch } from 'vue'
import { useGetListing } from '@/actions/getListing';
import ListingImages from '@/components/ListingImages.vue';
import { useDialog } from 'primevue/usedialog';
import ConfirmDialog from '@/components/ConfirmDialog.vue';
import { useMutation, useQueryClient } from '@tanstack/vue-query';
import { deleteListing } from '@/actions/createListing';
import Alert from '@/components/Alert.vue';
import { addBookmark, removeBookmark, useListingBookmarks } from '@/actions/bookmarks';

const router = useRouter();
const route = useRoute();
const auth = useAuth();
const dialog = useDialog();
const queryClient = useQueryClient();

const listingId = route.params.id as string

const {
  data: listing,
  isError,
  error,
  isPending
} = useGetListing(listingId);

const { data: bookmarks } = useListingBookmarks(listingId);

const bookmarked = ref(bookmarks.value?.hasBookmarked);

const parsedDescription = computed(() => marked(listing.value?.description ?? ''));
const isOwnListing = computed(() => {
  if (!listing.value) return false;
  return listing.value.ownerId === auth.userId;
});

const handleContactClick = () => {
  // TODO
  router.push(`/buy/${listing.value?.uuid}`);
};

const handleReserve = () => {
  // TODO
};


const deleteMutation = useMutation({
  mutationFn: deleteListing,
});

const bookmarkCountLocal = ref(bookmarks.value?.bookMarkCount || 0);
watch(bookmarks, (newval) => {
  bookmarkCountLocal.value = newval ? newval.bookMarkCount : 0;
  bookmarked.value = newval ? newval.hasBookmarked : false;
}, { deep: true });

watch(bookmarked, () => {
  console.log(bookmarked.value);
})

const bookmarkMutation = useMutation({
  mutationFn: async (removingBookmark: boolean) => {
    if (!listing.value) return;
    if (removingBookmark) {
      return await removeBookmark(listingId);
    } else {
      return await addBookmark(listingId);
    }
  },
  onMutate: async () => {
    if (bookmarked.value) {
      bookmarkCountLocal.value -= 1;
    } else {
      bookmarkCountLocal.value += 1;
    }
    bookmarked.value = !bookmarked.value;
  },
  onSuccess: (data) => {
    queryClient.invalidateQueries({
      queryKey: ['listing', listingId, "bookmarks"],
    })
  }
});

const handleDelete = () => {
  const d = dialog.open(ConfirmDialog, {
    props: {
      header: 'Delete listing',
      modal: true,
      draggable: false,
      dismissableMask: true,
    },
    data: {
      message: 'Are you sure you want to delete this listing?',
      variant: 'Caution',
      confirmLoading: deleteMutation.isPending,
    },
    emits: {
      onAccept: () => {
        deleteMutation.mutate(listingId, {
          onSuccess: () => {
            d.close();
            router.push('/');
          },
        });
      }
    }
  });
}

</script>

<template>
  <div v-if="isPending">
    <p>Laster inn...</p>
  </div>
  <div v-else-if="isError">
    <p>Feil: {{ error?.message }}</p>
  </div>
  <div class="listing" v-else>
    <div class="title-picture">
      <h3 class="listing-title">
        {{ listing?.name }}
      </h3>
      <ListingImages :listing-id="listingId" />
      <div class="picture-footing">
        <div class="listing-price">{{ listing?.price }},-</div>
        <div class="listing-actions">
          <Button variant="outline" v-if="isOwnListing">
            Rediger
            <Pencil :size="18" style="margin-left: 0.5rem;" />
          </Button>
          <Button variant="destructive" v-if="isOwnListing || auth.isAdmin" @click="handleDelete">
            Slett
            <Trash2 :size="18" style="margin-left: 0.5rem;" />
          </Button>
          <div @click="() => bookmarkMutation.mutate(bookmarked)" style="cursor: pointer" v-if="auth.isLoggedIn()"
            class="bookmark">
            <div class="bookmark-count">
              {{ bookmarkCountLocal }}
            </div>
            <Bookmark class="bookmark" v-if="!bookmarked" :size="38" />
            <BookmarkCheck v-else :size="38" />
          </div>
          <div v-if="!auth.isLoggedIn()">
          </div>
        </div>
      </div>
      <div v-if="listing?.description" class="listing-description" v-html="parsedDescription"></div>
      <Alert v-if="!listing?.description && isOwnListing" variant="Info">
        {{ $t('listingHasNoDescriptionLong') }}
      </Alert>
    </div>
    <div class="buy-box">
      <SellerInfo :userId="listing?.ownerId!" :can-contact-seller="auth.isLoggedIn()" />
      <div v-if="auth.isLoggedIn()" class="button-box">
        <Button variant="primary" style="width: 10rem; height: 3rem;" @click="handleContactClick">{{ $t("buy")
          }}</Button>
        <Button variant="secondary" style="width: 10rem; height: 3rem;" @click="handleContactClick">{{ $t("reserve")
          }}</Button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.bookmark-count {
  font-size: 1rem;
  font-weight: bold;
  width: 1rem;
  text-align: center;
}

.bookmark {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.listing {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 2rem;
}

.listing-title {
  font-size: 60px;
  font-weight: bold;
}

.button-box {
  display: flex;
  flex-direction: row;
  gap: 2rem;
}

.buy-box {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;
}

.listing-description {
  max-width: 55rem;
  font-size: 20px;
  padding: 2px;
  margin-bottom: 2rem;
}

.title-picture {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;
}

.picture-footing {
  display: flex;
  flex-direction: row;
  max-width: 45rem;
  justify-content: center;
  width: 100%;
  align-items: center;
}

.listing-actions {
  display: flex;
  flex-direction: row;
  gap: 1rem;
  margin-left: auto;
  align-items: center;
  justify-content: center;
}

.listing-price {
  font-size: 3rem;
  font-weight: bold;
}
</style>
