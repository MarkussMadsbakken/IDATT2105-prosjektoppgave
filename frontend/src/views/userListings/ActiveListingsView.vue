<script setup lang="ts">
import ListingCard from "@/components/ListingCard.vue";
import { useGetUserListings } from "@/actions/user.ts";
import { useRoute, useRouter } from "vue-router";
import { useToast } from "primevue/usetoast";
import ListingCardSkeleton from "@/components/skeleton/ListingCardSkeleton.vue";
import { useI18n } from "vue-i18n";


const route = useRoute();
const userId = Number(route.params.userid);
const toast = useToast();
const router = useRouter();
const { t } = useI18n();

if (!userId) {
    toast.add({
        severity: "error",
        summary: t("profile.error"),
        detail: t("profile.userDoesNotExist"),
        life: 3000,
    });
    router.replace("/404");
}

const { data: listings, isPending, isError, error } = useGetUserListings(userId);

</script>

<template>
    <div class="active-listings-wrapper">
        <div v-if="isPending" class="listing-grid">
            <ListingCardSkeleton :size="'medium'" v-for="i in 6" :key="i" />
        </div>
        <div v-else-if="isError">{{ $t("profile.couldNotLoadListings") }}</div>

        <div v-else-if="listings && listings?.length" class="listing-grid">
            <ListingCard v-for="listing in listings" :key="listing.uuid" :listing="listing" size="medium" />
        </div>
        <div v-else class="no-listings">
            {{ $t("profile.emptyListings") }}
        </div>
    </div>
</template>


<style scoped>
.active-listings-wrapper {
    padding: 2rem;
    padding-bottom: 5rem;
}


.title {
    font-size: 2rem;
    font-weight: bold;
}

.title-wrapper {
    padding-top: 3rem;
    text-align: center;
}

.listing-grid {
    padding-top: 2rem;
    display: flex;
    flex-wrap: wrap;
    gap: 2rem;
    justify-content: center;
    max-width: calc(3*25rem + 2*2rem);
    margin: 0 auto;
}

.no-listings {
    font-size: 2rem;
    font-weight: bold;
    text-align: center;
}

.tab-buttons {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 1rem;
    font-size: 1.8rem;
    margin-bottom: 2rem;
    padding-top: 1rem;
}

.tab-link {
    text-decoration: none;
    color: black;
    font-weight: 500;
    padding-bottom: 4px;
    transition: border-color 0.2s;
}

.tab-link.active {
    border-bottom: 2px solid black;
    font-weight: bold;
}

.divider {
    color: #666;
    font-size: 1.8rem;
}
</style>
