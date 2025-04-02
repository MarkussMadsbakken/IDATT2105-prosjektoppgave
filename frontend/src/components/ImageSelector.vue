<script setup lang="ts">
import { useDebounceFn, useDropZone } from '@vueuse/core'
import { AnimatePresence, motion } from 'motion-v';
import { ref } from 'vue';

const dropZoneRef = ref<HTMLDivElement>();

const emit = defineEmits<(e: "file-select", files: File[]) => void>()

const onDrop = (files: File[] | null) => {
    showSuccess.value = true;
    debounceSuccess();

    if (!files) {
        return;
    }

    emit('file-select', files);
}

const { isOverDropZone } = useDropZone(dropZoneRef, {
    onDrop,
    dataTypes: ['image/jpeg', 'image/png', 'image/gif', 'image/webp'],
    multiple: true,
    preventDefaultForUnhandled: false,
});

const variants = {
    hidden: {
        translateY: -20,
        opacity: 0
    },
    visible: {
        translateY: 0,
        opacity: 1
    },
}

const showSuccess = ref(false);

const debounceSuccess = useDebounceFn(() => {
    showSuccess.value = false;
}, 1000);

</script>

<template>
    <div ref="dropZoneRef" class="drop-zone">
        <AnimatePresence mode="popLayout">
            <motion.div v-if="isOverDropZone" initial="hidden" animate="visible" :variants="variants"
                :exit="{ opacity: 0 }">
                {{ $t('releaseToUpload') }}
            </motion.div>
            <motion.div v-if="!isOverDropZone && !showSuccess" initial="hidden" animate="visible" :variants="variants"
                :exit="{ opacity: 0 }">
                {{ $t('chooseImage') }}
            </motion.div>
            <motion.div v-if="showSuccess" initial="hidden" animate="visible" :variants="variants"
                class="success-message" :exit="{ opacity: 0 }">
                {{ $t('imageSelected') }}
            </motion.div>
        </AnimatePresence>
        <input type="file" class="file-input" accept="image/png, image/jpeg, image/gif, image/webp"
            @change="onDrop(Array.from(($event.target as HTMLInputElement)?.files ?? []))" multiple />
    </div>
</template>

<style scoped>
.success-message {
    color: oklch(0.723 0.219 149.579);
}

.file-input {
    position: absolute;
    width: 100%;
    height: 100%;
    opacity: 0;
    cursor: pointer;
}

.drop-zone {
    position: relative;
    width: 30rem;
    height: 10rem;
    border: 1px solid black;
    border-radius: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 1.5rem;
    color: black;
    background-color: oklch(0.97 0 0);
}
</style>