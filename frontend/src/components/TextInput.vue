<script setup lang="ts">

const props = withDefaults(defineProps<{
    type?: "text" | "password";
    placeholder?: string;
    modelValue?: string;
}>(), {
    type: "text",
    placeholder: "",
    modelValue: "",
});

</script>


<template>
    <input :type="props.type" :placeholder="props.placeholder" :value="props.modelValue" v-bind="$attrs"
        @input="$emit('update:modelValue', ($event.target as HTMLInputElement)?.value)" />
</template>

<style scoped>
input {
    padding: 0.5rem;
    border-radius: 5px;
    border: 1px solid black;
    width: 100%;
    box-sizing: border-box;
    font-size: 16px;
    margin-bottom: 1rem;
}
</style>