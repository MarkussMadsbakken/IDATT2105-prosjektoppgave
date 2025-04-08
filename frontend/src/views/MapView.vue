<script setup lang="ts">
import L from "leaflet";
import { onMounted, ref } from "vue";
import "leaflet/dist/leaflet.css";

const mapRef = ref<HTMLDivElement | null>(null);

onMounted(() => {
    const map = L.map(mapRef.value!, {
        center: [63.425, 10.4],
        zoom: 13
    });

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
}
);

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