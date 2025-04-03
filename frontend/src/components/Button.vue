<script setup lang="ts">

const props = withDefaults(defineProps<{
  variant?: 'primary' | 'secondary' | 'outline' | 'destructive' | 'accept';
}>(), {
  variant: 'primary',
});

defineEmits<{
  (e: "click", value: MouseEvent): void
}>();

</script>
<template>
  <button @click="$emit('click', $event)" :class="['base-button', props.variant]">
    <slot />
  </button>
</template>

<style scoped>
.base-button {
  all: unset;
  border-radius: 5px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
  padding: 0.5rem 1rem;
  font-size: 16px;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  width: 10rem;
  height: 2rem;
}

.base-button:hover {
  cursor: pointer;
}

.primary {
  font-weight: bold;
  background-color: var(--color-primaryButton);
  color: white;
}

.secondary {
  font-weight: bold;
  background-color: var(--color-secondaryButton);
  color: black;
}

.outline {
  outline: black;
  border: 1px solid black;
}

.destructive {
  background-color: var(--color-destructiveButton);
  color: white;
  font-weight: bold;
}

.accept {
  background-color: var(--color-acceptButton);
  color: white;
  font-weight: bold;
}
</style>
