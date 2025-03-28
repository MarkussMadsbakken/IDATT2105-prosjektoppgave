<script setup lang="ts">
import { CategoryIcons } from '@/util/CategoryIcons';
import { useRouter } from 'vue-router';
const router = useRouter();


const props = defineProps<{
    icon: keyof typeof CategoryIcons;
    categoryname: string;
}>();

const Icon = CategoryIcons[props.icon];

const handleClick = () => {
    const params = new URLSearchParams();
    params.append('category', props.categoryname);
    router.push("/search?" + params.toString());
};

</script>

<template>
    <div class="outer-wrapper" @click="handleClick">
        <div class="icon-wrapper">
            <Icon :size="30" :stroke-width="1.5" />
        </div>
        <div class="text-wrapper">
            <slot />
        </div>
    </div>
</template>

<style scoped>
.text-wrapper {
    width: 100%;
    height: 100%;
    flex: 1;
    display: flex;
    justify-content: baseline;
    align-items: center;
}

.outer-wrapper {
    line-height: 0;
    display: flex;
    flex-direction: row;
    border: 1px solid black;
    border-radius: 5px;
    padding: 1rem;
    width: 15rem;
    height: 5rem;
    align-items: center;
    gap: 1rem;
    transition: box-shadow;
    transition-duration: 0.2s;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
}

.outer-wrapper:hover {
    box-shadow: 0 10px 15px -3px rgb(0 0 0 / 0.1), 0 4px 6px -4px rgb(0 0 0 / 0.1);
    cursor: pointer;
}
</style>
