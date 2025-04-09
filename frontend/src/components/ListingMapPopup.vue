<script setup lang="ts">
import type { Listing } from '@/types';
import ListingHeaderImage from './ListingHeaderImage.vue';
import { useGetUser } from '@/actions/user';
import { OpenStreetMapProvider } from 'leaflet-geosearch';
import { ref } from 'vue';

const props = defineProps<{
    listing: Listing;
    position: {
        latitude: number;
        longitude: number;
    };
}>();

const { data: seller } = useGetUser(props.listing.ownerId);
const searchProvider = new OpenStreetMapProvider();
const address = ref<string>('Loading address...');

const getAddress = async () => {
    const results = await searchProvider.search({
        query: `${props.position.latitude}, ${props.position.longitude}`,
    });
    return results[0]?.label || 'Unknown location';
};

getAddress().then((result) => {
    if (result.length > 25) {
        result = result.slice(0, 25) + '...';
    }
    address.value = result;
}).catch(() => {
    address.value = 'Error fetching address';
});



</script>

<template>
    <div class="listing-map-popup">
        <div class="listing-map-popup-header">
            {{ props.listing.name }}
        </div>
        <div class="listing-map-popup-description">
            {{ props.listing.description }}
        </div>
        <div class="listing-map-popup-price">
            {{ props.listing.price }} kr
        </div>
        <div class="listing-map-popup-content">
            <div class="listing-map-popup-image">
                <ListingHeaderImage :listing-id="props.listing.uuid" />
            </div>
        </div>
        <div class="listing-map-popup-seller">
            <div class="listing-map-popup-seller-name">
                {{ seller?.firstName }} {{ seller?.lastName }}
            </div>
            <div class="listing-map-popup-seller-username">
                {{ seller?.username }}
            </div>
        </div>
        <div class="listing-map-popup-address">
            {{ address }}
        </div>
    </div>
</template>

<style scoped>
.listing-map-popup-seller {
    height: 3rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 1rem;
}

.listing-map-popup-seller-name {
    font-size: 1.2rem;
    font-weight: bold;
}

.listing-map-popup-image {
    width: 10rem;
    height: 10rem;
}

.listing-map-popup-image>img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.listing-map-popup-header {
    font-size: 1.5rem;
    font-weight: bold;
    margin-bottom: 0.5rem;
    text-align: center;
}

.listing-map-popup {
    display: flex;
    flex-direction: column;
    background-color: white;
    width: 10rem;
}
</style>