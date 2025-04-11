<script setup lang="ts">
import { ChevronDown, ChevronUp } from 'lucide-vue-next';
import { ref } from 'vue';
import { AnimatePresence, motion } from 'motion-v';

const props = withDefaults(defineProps<{
    open?: boolean;
    openTitle?: string;
    closedTitle?: string;
}>(), {
    open: false,
});

const open = ref(props.open);
const initial = ref(open.value);

function toggleOpen() {
    if (initial.value) {
        initial.value = false;
    }
    open.value = !open.value;
}

const variants = {
    open: { height: "auto" },
    closed: { height: 0, overflow: "hidden" },
}

</script>

<template>
    <div class="outer-wrapper">
        <AnimatePresence>
            <motion.div v-if="open" :initial="initial ? 'open' : 'closed'" animate="open" exit="closed"
                :variants="variants">
                <div class="inner-wrapper">
                    <slot />
                </div>
            </motion.div>
        </AnimatePresence>
        <div class="title" @click="toggleOpen" data-cy="collapsible-title">
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