<script setup lang="ts">

const props = withDefaults(defineProps<{
    placeholder?: number;
    modelValue?: number;
}>(), {
    type: "text",
    placeholder: 0,
    modelValue: 0,
});

defineEmits<{
    (e: "update:modelValue", value: number): void
}>();

</script>


<template>
    <input type="number" :placeholder="props.placeholder.toString()" :value="props.modelValue" v-bind="$attrs"
        @input="$emit('update:modelValue', Number(($event.target as HTMLInputElement)?.value))" />
</template>

<style scoped>
input {
    padding: 0.5rem;
    border-radius: 5px;
    border: 1px solid black;
    width: 100%;
    box-sizing: border-box;
    font-size: 16px;
}
</style>