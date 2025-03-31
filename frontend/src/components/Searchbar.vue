<script setup lang="ts">

import { Search } from 'lucide-vue-next';

const props = withDefaults(defineProps<{
    placeholder?: string;
    value?: string;
}>(), {
    type: "text",
    placeholder: "",
    modelValue: "",
});

const emit = defineEmits<{
    (e: "search", value: string): void
    (e: "input", value: string): void
}>();

const handleSearch = (el: HTMLInputElement) => {
    el.blur();
    emit('search', el.value);
};

</script>


<template>
    <div class="container">
        <input :placeholder="props.placeholder" :value="props.value"
            @input="$emit('input', ($event.target as HTMLInputElement)?.value)"
            @keydown.enter="handleSearch($event.target as HTMLInputElement)" />
        <Search class="search-icon" />
    </div>
</template>

<style scoped>
.container {
    position: relative;
    width: 100%;
    height: 100%;
}

input {
    padding: 1rem;
    border-radius: 5px;
    border: 1px solid black;
    width: 100%;
    box-sizing: border-box;
    font-size: 16px;
}

.search-icon {
    height: inherit;
    position: absolute;
    margin-top: auto;
    margin-bottom: auto;
    right: 0.5rem;
    color: black;
}
</style>