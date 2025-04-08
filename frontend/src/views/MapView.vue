<script setup lang="ts">
import L from "leaflet";
import { createVNode, getCurrentInstance, onMounted, ref, render, watch } from "vue";
import "leaflet/dist/leaflet.css";
import { useGetAllListings } from "@/actions/getListing";
import ListingMapPopup from "@/components/ListingMapPopup.vue";
import { useRouter } from "vue-router";

const mapRef = ref<HTMLDivElement | null>(null);
const router = useRouter();

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

    const instance = getCurrentInstance();

    // Watch for changes to listings and add markers
    watch(listings, (newListings) => {
        if (newListings) {
            // Clear existing markers
            map.eachLayer((layer) => {
                if (layer instanceof L.Marker) {
                    map.removeLayer(layer);
                }
            });

            // Add new markers
            newListings.forEach(listing => {
                if (listing.latitude === 0 || listing.longitude === 0) return; // Skip if no coordinates
                L.marker([listing.latitude, listing.longitude]).addTo(map).bindPopup(() => {
                    const container = document.createElement('div');
                    const node = createVNode(ListingMapPopup, {
                        listing,
                        position: {
                            latitude: listing.latitude,
                            longitude: listing.longitude
                        },
                        onClick: () => {
                            router.push(`/listing/${listing.uuid}`);
                        }
                    });
                    node.appContext = instance?.appContext!;
                    render(node, container);
                    return container;
                });
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