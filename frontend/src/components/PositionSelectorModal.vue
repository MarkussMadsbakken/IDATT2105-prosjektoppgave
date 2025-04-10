<script setup lang="ts">
import { inject, onMounted, ref, watch } from 'vue';
import L from "leaflet";
import { MAP_DEFAULT_COORDINATES } from '@/types';
import "leaflet/dist/leaflet.css";
import 'leaflet-geosearch/dist/geosearch.css';
import Button from './Button.vue';
import { OpenStreetMapProvider } from 'leaflet-geosearch';
import FormGroup from './FormGroup.vue';
import TextInput from './TextInput.vue';
import { useDebounceFn } from '@vueuse/core';
import Popover from 'primevue/popover';
import LoadingSpinner from './LoadingSpinner.vue';


const emit = defineEmits<{
    (e: 'position-selected', position: {
        latitude: number;
        longitude: number;
        label: string;
    }): void;
}>();

interface DialogRef {
    value: {
        data: {
            initialLatitude?: number;
            initialLongitude?: number;
        },
    },
    close: () => void;
}

const dialogRef = inject<DialogRef>("dialogRef");
const address = ref<string>("");
const popoverRef = ref();
const searchResults = ref<any[]>([]);
const searching = ref(false);
const selectedPosition = ref<[number, number]>([0, 0]);
const markerIsAdded = ref(false);

const markerCoords = [
    dialogRef?.value.data.initialLatitude !== 0 && dialogRef?.value.data.initialLatitude
        ? dialogRef?.value.data.initialLatitude
        : MAP_DEFAULT_COORDINATES[0],
    dialogRef?.value.data.initialLongitude !== 0 && dialogRef?.value.data.initialLongitude
        ? dialogRef?.value.data.initialLongitude
        : MAP_DEFAULT_COORDINATES[1],
] as [number, number];


const marker = L.marker(markerCoords);
const searchProvider = new OpenStreetMapProvider();

watch(address, () => {
    searching.value = true;
    searchDebounded();
});

const searchDebounded = useDebounceFn(() => {
    searchProvider.search({ query: address.value })
        .then((result) => {
            searchResults.value = result;
            searching.value = false;
        });
}, 500);

let map: L.Map;

onMounted(() => {
    map = L.map('map-selector').setView(markerCoords, 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    map.addEventListener("click", e => {
        if (!markerIsAdded.value) {
            marker.addTo(map);
            markerIsAdded.value = true;
        }

        marker.setLatLng(e.latlng);
        selectedPosition.value = [e.latlng.lat, e.latlng.lng];
        address.value = "";
    });


    if (dialogRef?.value.data.initialLatitude && dialogRef?.value.data.initialLongitude) {
        marker.addTo(map);
        marker.setLatLng(markerCoords);
        markerIsAdded.value = true;
        selectedPosition.value = markerCoords;
    }
});

const selectPosition = (result: any) => {
    if (!markerIsAdded.value) {
        marker.addTo(map);
        markerIsAdded.value = true;
    }

    map.panTo({
        lat: result.y,
        lng: result.x
    });
    marker.setLatLng([result.y, result.x]);
    address.value = result.label;
    selectedPosition.value = [result.y, result.x];
}

</script>

<template>
    <div class="outer-position-selector-wrapper">
        <div id="map-selector">
        </div>
        <div class="address-search">
            <FormGroup name="address" :label="$t('map.searchAddress')">
                <TextInput id="address" name="address" type="text" v-model="address" @focus="popoverRef?.show($event)"
                    autocomplete="off" @blur="popoverRef?.hide()" />
                <Popover ref="popoverRef">
                    <div class="popover-content">
                        <LoadingSpinner v-if="searching" />
                        <div class="no-results" v-else-if="searchResults.length === 0">
                            <p>{{ $t('map.noResults') }}</p>
                        </div>
                        <div v-else v-for="result in searchResults" class="popover-item"
                            @click="selectPosition(result)">
                            <p>{{ result.label }}</p>
                        </div>
                    </div>
                </Popover>
            </FormGroup>
        </div>
        <div class="actions">
            <Button variant="outline" @click="dialogRef?.close()">
                {{ $t('map.cancel') }}
            </Button>
            <Button id="select-position-popup" @click="emit('position-selected', {
                latitude: selectedPosition[0],
                longitude: selectedPosition[1],
                label: address
            })">
                {{ $t('map.selectPosition') }}
            </Button>
        </div>
    </div>

</template>

<style scoped>
.no-results {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
}

.popover-content {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    height: 20vw;
    width: 40vw;
    overflow-y: auto;
}

.popover-item {
    padding: 0.1rem;
    cursor: pointer;
}

.search-form {
    display: flex;
    gap: 1rem;
}

.address-search {
    margin-top: 1rem;
}

.actions {
    display: flex;
    margin-top: 1rem;
    justify-content: center;
    gap: 2rem;
}

#map-selector {
    height: 40vh;
    width: 80vw;
}
</style>
