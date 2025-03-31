<script setup lang="ts">
import { ChevronDown, ChevronUp } from 'lucide-vue-next';
import { computed, nextTick, onMounted, reactive, ref } from 'vue';

const props = withDefaults(defineProps<{
    open?: boolean;
    openTitle?: string;
    closedTitle?: string;
}>(), {
    open: false,
});

const open = ref(props.open);

const slotWrapper = ref<HTMLElement | null>(null);
const measuredHeight = ref(0);

const disableTransition = ref(false);

const wrapperStyle = computed(() => ({
    height: open.value ? `${measuredHeight.value}px` : '0px',
    transition: disableTransition.value ? 'none' : 'all 0.2s',
}));

function toggleOpen() {
    open.value = !open.value;
    if (open.value && slotWrapper.value) {
        nextTick(() => {
            measuredHeight.value = slotWrapper.value!.scrollHeight;
        });
    }
}

onMounted(() => {
    if (open.value && slotWrapper.value) {
        measuredHeight.value = slotWrapper.value.scrollHeight;
        // Disable the transition initially so it just appears open
        disableTransition.value = true;
        // Remove the transition disable after the next frame
        requestAnimationFrame(() => {
            disableTransition.value = false;
        });
    }
});

</script>

<template>
    <div class="outer-wrapper">
        <div class="slot-wrapper" :style="wrapperStyle" ref="slotWrapper">
            <div class="inner-wrapper">
                <slot />
            </div>
        </div>
        <div class="title" @click="toggleOpen">
            <ChevronUp v-if="open" class="icon" stroke-width="2px" />
            {{ open ? props.closedTitle ?? $t("close") : props.openTitle ?? $t("open") }}
            <ChevronDown v-if="!open" class="icon" />
        </div>
    </div>
</template>

<style scoped>
.icon {
    font-weight: normal;
    margin-top: auto;
    margin-bottom: auto;
    align-self: center;
    line-height: 0;
}

.slot-wrapper {
    overflow: hidden;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
}

.inner-wrapper {
    padding-bottom: 1.5rem;
    overflow: visible;
}

.title {
    display: flex;
    flex-direction: column;
    margin-left: auto;
    margin-right: auto;
    width: fit-content;
    user-select: none;
    cursor: pointer;
}
</style>