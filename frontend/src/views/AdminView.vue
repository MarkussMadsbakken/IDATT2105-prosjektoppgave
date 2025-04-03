<script setup lang="ts">
import CreateCategory from '@/components/admin/CreateCategory.vue';
import Divider from '@/components/Divider.vue';
import EditCategories from '@/components/admin/EditCategories.vue';
import { Folders, Grid, Users } from 'lucide-vue-next';
import { ref } from 'vue';
import EditUsers from '@/components/admin/EditUsers.vue';


const pages = [
    {
        title: "Categories",
        icon: Folders,
        component: EditCategories
    },
    {
        title: "Users",
        icon: Users,
        component: EditUsers
    }
]

const selectedPage = ref(0);

</script>

<template>
    <div class="outer-wrapper">
        <div class="sidebar">
            <div class="title">
                Admin Panel
            </div>
            <Divider />
            <div v-for="(page, index) in pages" :key="index" @click="selectedPage = index"
                :class="{ 'selected': selectedPage === index }" class="page-link">
                <component :is="page.icon" />
                <span>{{ page.title }}</span>
            </div>
        </div>
        <div class="page">
            <component :is="pages[selectedPage].component" />
        </div>
    </div>
</template>

<style scoped>
.page-link.selected {
    background-color: oklch(0.87 0 0);
}

.page-link {
    display: flex;
    gap: 0.5rem;
    padding: 0.5rem;
    cursor: pointer;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: 0 1px 3px 0 rgb(0 0 0 / 0.1), 0 1px 2px -1px rgb(0 0 0 / 0.1);
}

.page-link.selected:hover {
    background-color: oklch(0.87 0 0);
    box-shadow: 0 1px 3px 0 rgb(0 0 0 / 0.1), 0 1px 2px -1px rgb(0 0 0 / 0.1);
}

.page-link:hover {
    background-color: #f0f0f0;
    box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1);
}

.title {
    font-size: 1rem;
    font-weight: 500;
}

.sidebar {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    padding: 1rem;
    width: 15rem;
    border-right: 1px solid #ccc;
}

.page {
    height: 100%;
    padding: 1rem;
    flex: 1;
    overflow-y: scroll;
    padding-bottom: 5rem;
}

.outer-wrapper {
    display: flex;
    flex-direction: row;
    height: 80vh;
}
</style>