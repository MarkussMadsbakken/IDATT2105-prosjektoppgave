<script setup lang="ts">
import { useDebounceFn, useDropZone } from '@vueuse/core'
import { AnimatePresence, motion } from 'motion-v';
import { useToast } from 'primevue/usetoast';
import { ref } from 'vue';
import { useI18n } from 'vue-i18n';

const dropZoneRef = ref<HTMLDivElement>();
const toast = useToast();
const { t } = useI18n();

const emit = defineEmits<(e: "file-select", files: File[]) => void>();

const onDrop = (files: File[] | null) => {
    if (files && files.length > 0) {
        const file = files[0];
        if (file.size > 1024 * 1024) {
            showSuccess.value = false;
            toast.add({
                severity: 'error',
                summary: t('form.error'),
                detail: t('form.imageTooLarge'),
                life: 3000,
                closable: true,
            });
            return;
        }
    }
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
                {{ $t('form.releaseToUpload') }}
            </motion.div>
            <motion.div v-if="!isOverDropZone && !showSuccess" initial="hidden" animate="visible" :variants="variants"
                :exit="{ opacity: 0 }">
                {{ $t('listings.create.chooseImage') }}
            </motion.div>
            <motion.div v-if="showSuccess" initial="hidden" animate="visible" :variants="variants"
                class="success-message" :exit="{ opacity: 0 }">
                {{ $t('form.imageSelected') }}
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
