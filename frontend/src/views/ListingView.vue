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
    useGetListingWithCategory,
    useReserveListing,
    useToggleArchive
} from '@/actions/getListing';
import ListingImages from '@/components/ListingImages.vue';
import { useDialog } from 'primevue/usedialog';
import ConfirmDialog from '@/components/ConfirmDialog.vue';
import { useMutation, useQueryClient } from '@tanstack/vue-query';
import { deleteListing } from '@/actions/createListing';
import Alert from '@/components/Alert.vue';
import {
    addBookmark,
    removeBookmark,
    useHasBookmarked,
    useListingBookmarks
} from '@/actions/bookmarks';
import { useI18n } from 'vue-i18n';
import { createChat } from '@/actions/chat';
import LoadingSpinner from "@/components/LoadingSpinner.vue";
import { useToast } from 'primevue/usetoast';
import { pushUserHistory } from '@/actions/userHistory';
import Divider from '@/components/Divider.vue';
import DOMPurify from "dompurify";
import CategoryCard from '@/components/CategoryCard.vue';
import PositionDisplay from '@/components/PositionDisplay.vue';
import ListingViewSkeleton from '@/components/skeleton/ListingViewSkeleton.vue';

const router = useRouter();
const route = useRoute();
const auth = useAuth();
const dialog = useDialog();
const queryClient = useQueryClient();
const i18n = useI18n();
const toast = useToast();

const listingId = route.params.id as string

// Push user history
if (listingId && auth.loggedIn) {
    pushUserHistory({
        listingId: listingId,
    })
};

const {
    data: listing,
    isError,
    error,
    isPending
} = useGetListingWithCategory(listingId);


const parsedDescription = computed(() => DOMPurify.sanitize(listing.value?.description ?? ''));
const isOwnListing = computed(() => {
    if (!listing.value) return false;
    return listing.value.ownerId === auth.userId;
});

const { mutate: createChatMutation } = useMutation({
    mutationFn: createChat,
    onSuccess: (data) => {
        router.push(`/chat/${data.chatId}`);
    }
})

const { mutate: reserve, isPending: isReservePending, isError: isPendingError, error: reserveError, isSuccess: isReserveSuccess } = useReserveListing();

const handleReserve = () => {
    reserve({ uuid: listingId }, {
        onSuccess: () => {
            toast.add(
                {
                    severity: "success",
                    summary: i18n.t("success"),
                    detail: i18n.t("listings.view.reserved"),
                    life: 3000,
                    closable: true,
                }
            );
            queryClient.setQueryData(["reservation", listingId], (oldData: any) => ({
                ...oldData,
                userId: auth.userId,
                createdAt: new Date().toISOString(),
            }));
            queryClient.invalidateQueries({ queryKey: ['listing'] });
            queryClient.invalidateQueries({ queryKey: ['reservation', listingId] });
        },
        onError: (error) => {
            toast.add(
                {
                    severity: "error",
                    summary: i18n.t("Error"),
                    detail: i18n.t("listings.reservedError"),
                    life: 3000,
                    closable: true,
                }
            );
        }
    });
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

    return reservation?.value?.userId === auth.userId;
})
const reserveButtonText = computed(() => {
    if (reservation?.value && !isReservedByMe.value) {
        return `${i18n.t("listings.view.reservedByAnotherUser")}: ${reservationEndTime.value}`;
    }

    if (reservation?.value && isReservedByMe.value) {
        return i18n.t("listings.view.reservedByMe");
    }

    return i18n.t("listings.reserve");
});

const deleteMutation = useMutation({
    mutationFn: deleteListing,
});

const { data: bookmarks } = useListingBookmarks(listingId);
const { data: hasBookmarkedRef } = useHasBookmarked(listingId, {
    enabled: auth.isLoggedIn(),
});

const bookmarked = computed(() => auth.isLoggedIn() && (hasBookmarkedRef?.value ?? false));
const bookmarkCount = computed(() => bookmarks.value?.bookMarkCount || 0);

const bookmarkMutation = useMutation({
    mutationFn: async (removingBookmark: boolean) => {
        if (!listing.value) return;
        return removingBookmark ? removeBookmark(listingId) : addBookmark(listingId);
    },
    onMutate: async (removingBookmark) => {
        queryClient.setQueryData(["listing", "bookmarks", listingId], (oldData: any) => ({
            ...oldData,
            bookMarkCount: oldData.bookMarkCount + (removingBookmark ? -1 : 1),
        }));
        queryClient.setQueryData(["listing", "bookmarks", listingId, "exists"], !removingBookmark);
    },
    onError: (error, removingBookmark) => {
        console.error("Error updating bookmark:", error);
        queryClient.invalidateQueries({ queryKey: ["listing", "bookmarks", listingId] });
        queryClient.invalidateQueries({ queryKey: ["listing", "bookmarks", listingId, "exists"] });
    },
    onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: ["listing", "bookmarks", listingId] });
        queryClient.invalidateQueries({ queryKey: ["listing", "bookmarks", listingId, "exists"] });
    },
});


const { mutate: toggleArchive, isPending: isArchivePending } = useToggleArchive();

const handleToggleArchive = () => {
    const newState = !listing.value?.active;

    toggleArchive(
        { uuid: listingId, state: newState },
        {
            onSuccess: () => {
                queryClient.setQueryData(["listing", listingId], (oldData: any) => ({
                    ...oldData,
                    active: newState,
                }));
                toast.add(
                    {
                        severity: "success",
                        summary: i18n.t("success"),
                        detail: i18n.t(newState ? "listings.view.restored" : "listings.view.archived"),
                        life: 3000,
                        closable: true,
                    }
                );
                queryClient.invalidateQueries({ queryKey: ['listing', listingId] });
            },
            onError: (error) => {
                toast.add(
                    {
                        severity: "error",
                        summary: i18n.t("error"),
                        detail: i18n.t("listings.view.archiveError"),
                        life: 3000,
                        closable: true,
                    }
                );
            }
        }
    );
}


const handleDelete = () => {
    const d = dialog.open(ConfirmDialog, {
        props: {
            header: i18n.t("listings.view.deleteListing"),
            modal: true,
            draggable: false,
            dismissableMask: true,
        },
        data: {
            message: i18n.t("form.areYouSureYouWantToDelete", { content: i18n.t("listings.view.thisListing") }),
            variant: 'Caution',
            confirmLoading: deleteMutation.isPending,
        },
        emits: {
            onAccept: () => {
                deleteMutation.mutate(listingId, {
                    onSuccess: (a) => {
                        toast.add(
                            {
                                severity: "success",
                                summary: i18n.t("success"),
                                detail: i18n.t("listings.deleted"),
                                life: 3000,
                                closable: true,
                            }
                        );
                        d.close();
                        router.push('/');
                    },
                    onError: (e) => {
                        toast.add(
                            {
                                severity: "error",
                                summary: i18n.t("Error"),
                                detail: i18n.t("listing.deleteError"),
                                life: 3000,
                                closable: true,
                            }
                        );
                    }
                });
            }
        }
    });
}

</script>

<template>
    <ListingViewSkeleton v-if="isPending" />
    <div v-else-if="isError">
        <p>Feil: {{ error?.message }}</p>
    </div>
    <div class="listing" v-else>
        <ListingImages :listing-id="listingId" />
        <div class="picture-footer">
            <Alert variant="Warning" v-if="listing?.deleted">
                {{ $t("listings.view.isDeleted") }}
            </Alert>
            <Alert variant="Info" v-else-if="!listing?.active">
                {{ $t("listings.view.isInactive") }}
            </Alert>
            <Alert class="sold-warning" variant="Info" v-else-if="listing?.sold">
                {{ $t("checkout.listingIsPurchased") }}
            </Alert>
            <h3 class="listing-title">
                {{ listing?.name }}
            </h3>
            <div class="listing-actions">
                <div class="listing-price">{{ listing?.price }}kr</div>
                <div class="listing-option-buttons">
                    <Button class="listing-option-button" variant="outline" v-if="isOwnListing && !listing?.sold"
                        @click="router.push(`/listing/${listingId}/edit`)">
                        {{ $t("listings.view.edit") }}
                        <Pencil :size="18" style="margin-left: 0.5rem;" />
                    </Button>

                    <Button class="listing-option-button" variant="outline" v-if="isOwnListing && !listing?.sold"
                        :disabled="isArchivePending" @click="handleToggleArchive">
                        <template v-if="isArchivePending">
                            <LoadingSpinner />
                        </template>
                        <template v-else>
                            {{ listing?.active ? $t("listings.view.archiveThis") : $t("listings.view.restore") }}
                            <component :is="listing?.active ? Archive : ArchiveRestore" :size="18"
                                style="margin-left: 0.5rem;" />
                        </template>
                    </Button>
                    <Button class="listing-option-button" variant="destructive" v-if="isOwnListing || auth.isAdmin"
                        @click="handleDelete">
                        {{ $t("listings.delete") }}
                        <Trash2 :size="18" style="margin-left: 0.5rem;" />
                    </Button>
                    <div @click="() => { if (auth.userId === listing?.ownerId) { return } bookmarkMutation.mutate(bookmarked!) }"
                        :class="{ 'clickable': auth.isLoggedIn() && listing?.ownerId !== auth.userId }"
                        v-if="auth.isLoggedIn()" class="bookmark">
                        <div class="bookmark-count">
                            {{ bookmarkCount }}
                        </div>
                        <Bookmark class="bookmark" v-if="!bookmarked" :size="38" />
                        <BookmarkCheck v-else :size="38" />
                    </div>
                </div>
            </div>
            <div v-if="auth.isLoggedIn() && !listing!.sold && listing?.ownerId !== auth.userId" class="buy-wrapper">
                <Button variant="primary" class="buy-button" :class="{ 'is-disabled': reservation && !isReservedByMe }"
                    @click="router.push(`/listing/${listingId}/checkout`)" :disabled="reservation && !isReservedByMe">{{
                        $t("listings.view.buy")
                    }}</Button>
                <Button variant="outline" class="buy-button" :class="{ 'is-disabled': reservation && !isReservedByMe }"
                    :disabled="reservation && isReservedByMe" @click="handleReserve">
                    <template v-if="isReservePending">
                        <LoadingSpinner />
                    </template>
                    <template v-else>
                        {{ reserveButtonText }} {{ reservationEndTime }}
                    </template>
                </Button>
            </div>
            <Divider />
        </div>
        <div class="listing-content">
            <div v-if="listing?.description" class="listing-description">
                <div class="listing-description-title">
                    {{ $t("listings.view.description") }}
                </div>
                <div v-html="parsedDescription" class="listing-description-content"></div>
            </div>
            <Alert v-else-if="isOwnListing" variant="Info">
                {{ $t('listings.view.noDescriptionLong') }}
            </Alert>
            <div class="listing-details">
                <div class="listing-description-title">
                    {{ $t("listings.view.details") }}
                </div>
                <CategoryCard @click="router.push(`/search?category=${listing.category.id}`)" v-if="listing?.category"
                    :icon="listing.category.icon">
                    {{ listing.category.name }}
                </CategoryCard>
                <div class="listing-publish-date">
                    <div class="listing-published-title">
                        {{ $t("listings.view.published") }}:
                    </div>
                    {{ new Date(listing?.createdAt ?? 0).toLocaleDateString("no", {
                        year: "numeric",
                        month: "2-digit",
                        day: "2-digit"
                    }) }}
                </div>
            </div>
            <div class="seller-info">
                <div class="listing-description-title">
                    {{ $t("listings.view.seller") }}
                </div>
                <SellerInfo :userId="listing?.ownerId!" :can-contact-seller="auth.isLoggedIn()" size="medium"
                    @contact-seller="createChatMutation(listingId)" />
            </div>
            <div class="map-container">
                <div class="listing-description-title">
                    {{ $t("listings.view.position") }}
                </div>
                <PositionDisplay v-if="listing?.latitude && listing?.longitude" :latitude="listing.latitude"
                    :longitude="listing.longitude" />
                <Alert v-else-if="isOwnListing" variant="Info">
                    {{ $t('listings.view.hasNoPosition') }}
                </Alert>
            </div>
        </div>
    </div>
</template>

<style scoped>
.map-container {
    margin-top: 1rem;
    width: 100%;
    height: 100%;
}

.buy-button {
    flex: 1;
    height: 2.5rem;
}

.seller-info {
    display: flex;
    flex-direction: column;
}

.listing-publish-date {
    margin-top: 0.5rem;
    display: flex;
    flex-direction: row;
    gap: 0.5rem;
}

.listing-details {
    max-width: 55rem;
    font-size: 20px;
    padding: 2px;
    margin-bottom: 2rem;
}

.listing-description-title {
    font-size: 1.2rem;
    font-weight: 600;
    margin-bottom: 0.5rem;
}

.buy-wrapper {
    display: flex;
    flex-direction: row;
    gap: 2rem;
    justify-content: space-between;
    align-items: center;
}

.listing-content {
    display: flex;
    flex-direction: column;
    max-width: 44rem;
    width: 100%;
}

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
}

.bookmark.clickable {
    cursor: pointer;
}

.listing {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: start;
    padding: 1rem;
    gap: 2rem;
}

.listing-title {
    font-size: 1.8rem;
    font-weight: 600;
    width: 45rem;
    text-wrap: break-word;
    max-width: 100%;
}

.button-box {
    display: flex;
    flex-direction: row;
    gap: 2rem;
}

.listing-option-button {
    width: 5rem;
    height: 2rem;
    font-size: 0.8rem;
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

.picture-footer {
    display: flex;
    flex-direction: column;
    max-width: 44rem;
    width: 100%;
    gap: 1rem;
}

.listing-option-buttons {
    display: flex;
    flex-direction: row;
    gap: 1rem;
    justify-content: center;
    align-items: center;
}

.listing-actions {
    display: flex;
    flex-direction: row;
    gap: 1rem;
    justify-content: space-between;
    align-items: center;
}

@media only screen and (max-width: 520px) {
    .buy-wrapper {
        flex-direction: column;
        gap: 1rem;
    }
}

.listing-price {
    font-size: 1.5rem;
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
