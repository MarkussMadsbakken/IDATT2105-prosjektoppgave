<script setup lang="ts">
import { onMounted, ref } from 'vue';
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import { OpenStreetMapProvider } from 'leaflet-geosearch';

const props = defineProps<{
    latitude: number;
    longitude: number;
}>();

let map: L.Map;
const marker = L.marker({
    lat: props.latitude,
    lng: props.longitude,
});

const addressString = ref("");

const provider = new OpenStreetMapProvider();

onMounted(() => {
    map = L.map('map-display').setView({
        lat: props.latitude,
        lng: props.longitude,
    }, 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    marker.addTo(map);

    provider.search({
        query: `${props.latitude}, ${props.longitude}`,
    }).then((result) => {
        if (result.length > 0) {
            addressString.value = result[0].label;
        }
    });
});


</script>


<template>
    <div class="map-container">
        <div class="map" id="map-display"></div>
        <div class="address">
            {{ addressString }}
        </div>
    </div>
</template>

<style scoped>
.map-container {
    width: 100%;
    height: 100%;
}

.map {
    width: 100%;
    height: 10rem;
}
</style>