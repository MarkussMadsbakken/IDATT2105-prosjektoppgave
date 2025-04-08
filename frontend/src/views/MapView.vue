<script setup lang="ts">
import L from "leaflet";
import { onMounted, ref, watch } from "vue";
import "leaflet/dist/leaflet.css";
import { useGetAllListings } from "@/actions/getListing";

const mapRef = ref<HTMLDivElement | null>(null);

const { data: listings } = useGetAllListings();

onMounted(() => {
    const map = L.map(mapRef.value!, {
        center: [63.425, 10.4],
        zoom: 13
    });

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);

    // Watch for changes to listings and add markers
    watch(listings, (newListings) => {
        if (newListings) {
            newListings.forEach(listing => {
                if (listing.latitude === 0 || listing.longitude === 0) return; // Skip if no coordinates
                L.marker([listing.latitude, listing.longitude]).addTo(map)
                    .bindPopup(listing.name || "Listing");
            });
        }
    }, { immediate: true });
});

</script>

<template>
    <div id="map" ref="mapRef">
    </div>
</template>

<style scoped>
#map {
    height: 80vh;
    width: 100%;
}
</style>