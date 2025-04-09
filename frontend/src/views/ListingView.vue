<script setup lang="ts">
import type { Listing, User } from '@/types'
import SellerInfo from "@/components/SellerInfo.vue";
import Button from '@/components/Button.vue';
import { useRouter, useRoute } from "vue-router";
import { marked } from 'marked';
import { Trash2, Pencil, Bookmark, BookmarkCheck, Archive, ArchiveRestore } from 'lucide-vue-next'
import { useAuth } from "@/stores/auth.ts";
import { computed, ref, watch } from 'vue'
import {
  useCheckForReservation,
  useGetListing,
  useReserveListing,
  useToggleArchive
} from '@/actions/getListing';
import ListingImages from '@/components/ListingImages.vue';
import { useDialog } from 'primevue/usedialog';
import ConfirmDialog from '@/components/ConfirmDialog.vue';
import { useMutation, useQueryClient } from '@tanstack/vue-query';
import { deleteListing } from '@/actions/createListing';
import Alert from '@/components/Alert.vue';
import { addBookmark, removeBookmark, useListingBookmarks } from '@/actions/bookmarks';
import { useI18n } from 'vue-i18n';
import { createChat } from '@/actions/chat';
import LoadingSpinner from "@/components/LoadingSpinner.vue";

const router = useRouter();
const route = useRoute();
const auth = useAuth();
const dialog = useDialog();
const queryClient = useQueryClient();
const i18n = useI18n();

const listingId = route.params.id as string

const {
  data: listing,
  isError,
  error,
  isPending
} = useGetListing(listingId);

const { data: bookmarks } = useListingBookmarks(listingId);

const bookmarked = ref(bookmarks.value?.hasBookmarked ?? false);

const parsedDescription = computed(() => marked(listing.value?.description ?? ''));
const isOwnListing = computed(() => {
  if (!listing.value) return false;
  return listing.value.ownerId === auth.userId;
});

const { mutate: createChatMutation } = useMutation({
  mutationFn: createChat,
  onSuccess: (data) => {
    console.log(data);
    router.push(`/chat/${data.chatId}`);
  }
})

const { mutate: reserve, isPending: isReservePending, isError: isPendingError, error: reserveError, isSuccess: isReserveSuccess } = useReserveListing();

const handleReserve = () => {
  reserve({ uuid: listingId });
}

const {
  data: reservation,
  isError: isReservationError,
  error: reservationCheckError,
  isPending: reservationChecKIsPending
} = useCheckForReservation(listingId, auth.isLoggedIn());

const reservationEndTime = computed(() => {
  if (!reservation?.value?.createdAt) return null;
  const createdAt = new Date(reservation.value.createdAt)
  const expiresAt = new Date(createdAt.getTime() + 60 * 60 * 1000);

  return expiresAt.toLocaleTimeString(navigator.language, {
    hour: "2-digit",
    minute: "2-digit",
    hour12: false
  });
})

const isReservedByMe = computed(() => {
  console.log(reservation);
  console.log(reservation?.value?.userId);
  console.log(auth.userId);
  console.log(reservation?.value?.userId === auth.userId)
  return reservation?.value?.userId === auth.userId;
})
const reserveButtonText = computed(() => {
  if (reservation?.value && isReservedByMe.value) {
    return `${i18n.t("listing.view.reservedByAnotherUser")}: ${reservationEndTime.value}`;
  }
  return i18n.t("listings.reserve");
})

const deleteMutation = useMutation({
  mutationFn: deleteListing,
});

const bookmarkCountLocal = ref(bookmarks.value?.bookMarkCount || 0);
watch(bookmarks, (newval) => {
  bookmarkCountLocal.value = newval ? newval.bookMarkCount : 0;
  bookmarked.value = newval ? newval.hasBookmarked : false;
});

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


const { mutate: toggleArchive, isPending: isArchivePending } = useToggleArchive();

const handleToggleArchive = () => {

  const oldState = listing.value?.active;
  const newState = !oldState;

  toggleArchive(
    { uuid: listingId, state: newState },
    {
      onSuccess: () => {
        // TODO Toast
        queryClient.invalidateQueries({ queryKey: ['listing', listingId] });
        console.log("Aktivstatus oppdatert:", newState);
      },
      onError: (error) => {
        console.error("Feil ved arkivering:", error);
      }
    }
  );
}


const handleDelete = () => {
  const d = dialog.open(ConfirmDialog, {
    props: {
      header: i18n.t("listing.delete", i18n.t("listing")),
      modal: true,
      draggable: false,
      dismissableMask: true,
    },
    data: {
      message: i18n.t("areYouSureYouWantToDelete", { content: i18n.t("thisListing") }),
      variant: 'Caution',
      confirmLoading: deleteMutation.isPending,
    },
    emits: {
      onAccept: () => {
        deleteMutation.mutate(listingId, {
          onSuccess: (a) => {
            console.log(a);
            console.log("Successed!")
            d.close();
            router.push('/');
          },
          onError: (e) => {
            console.log(error.value)
          }
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
      <Alert class="reserved-info" variant="Info" v-if="reservation && !isReservedByMe">
        {{ $t("listings.view.listingReservedByAnotherUser") }} {{ reservationEndTime }}
      </Alert>
      <Alert class="my-reservation-warn" variant="Info" v-if="reservation && isReservedByMe">
        {{ $t("listing.reservedByMe") }} {{ reservationEndTime }}
      </Alert>
      <h3 class="listing-title">
        {{ listing?.name }}
      </h3>
      <Alert variant="Warning" v-if="listing?.deleted">
        {{ $t("listing.isDeleted") }}
      </Alert>
      <Alert variant="Info" v-else-if="!listing?.active">
        {{ $t("listing.isInactive") }}
      </Alert>
      <Alert class="sold-warning" variant="Info" v-else-if="listing?.sold">
        {{ $t("checkout.listingIsPurchased") }}
      </Alert>
      <ListingImages :listing-id="listingId" />
      <div class="picture-footing">
        <div class="listing-price">{{ listing?.price }}kr</div>
        <div class="listing-actions">
          <Button class="listing-option-button" variant="outline" v-if="isOwnListing && !listing?.sold"
            @click="router.push(`/listing/${listingId}/edit`)">
            {{ $t("listings.view.edit") }}
            <Pencil :size="18" style="margin-left: 0.5rem;" />
          </Button>

          <Button class="listing-option-button" :class="{ 'active-button': !listing?.active }" variant="outline"
            v-if="isOwnListing && !listing?.sold" :disabled="isArchivePending" @click="handleToggleArchive">
            <template v-if="isArchivePending">
              <LoadingSpinner />
            </template>
            <template v-else>
              {{ listing?.active ? $t("listings.view.archiveThis") : $t("listing.view.restore") }}
              <component :is="listing?.active ? Archive : ArchiveRestore" :size="18" style="margin-left: 0.5rem;" />
            </template>
          </Button>

          <Button class="listing-option-button" variant="destructive" v-if="isOwnListing || auth.isAdmin"
            @click="handleDelete">
            {{ $t("listings.delete") }}
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
        {{ $t('listing.hasNoDescriptionLong') }}
      </Alert>
    </div>
    <div v-if="!isOwnListing" class="buy-box">
      <SellerInfo :userId="listing?.ownerId!" :can-contact-seller="auth.isLoggedIn()" size="medium"
        @contact-seller="createChatMutation(listingId)" />


      <div v-if="auth.isLoggedIn() && !listing!.sold" class="button-box">
        <Button variant="primary" :class="{ 'is-disabled': reservation && !isReservedByMe }"
          @click="router.push(`/listing/${listingId}/checkout`)" :disabled="reservation && !isReservedByMe" style="width: 10rem;
                 height: 3rem;">{{ $t("checkout.buy") }}</Button>

        <Button variant="secondary" :class="{ 'is-disabled': reservation && !isReservedByMe }"
          :disabled="reservation && isReservedByMe" @click="handleReserve" style="width: 10rem; height: 3rem;">
          {{ reserveButtonText }}
        </Button>
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
  width: 45rem;
  line-height: 4rem;
}

.button-box {
  display: flex;
  flex-direction: row;
  gap: 2rem;
}

.listing-option-button {
  width: 6.5rem;
}

.active-button {
  background-color: #f0f0f0;
  border-color: #d5d4d4;
  color: #333;
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
  padding-left: 1rem;
}

.listing-price {
  font-size: 3rem;
  font-weight: bold;
}

.sold-warning {
  width: 45rem;
}

.is-disabled {
  background-color: #ccc !important;
  color: #999 !important;
  cursor: not-allowed !important;
  border: 1px solid #aaa !important;
  opacity: 0.7;
  pointer-events: none;
}
</style>
