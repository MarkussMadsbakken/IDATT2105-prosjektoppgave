<script setup lang="ts">

const props = withDefaults(defineProps<{
  variant : 'primary' | 'secondary'
  width?: string
  height?: string
}>(), {
  width: '200px',
  height: '50px'
})

</script>
<template>
  <button
  :class="['base-button',variant]"
  :style="{width: props.width, height: props.height}"
  >
    <slot />
  </button>
</template>

<style>

.base-button {
  all: unset;
  font-weight: bold;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.2);
  padding: 0.5rem 1rem;

  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
}
.base-button:hover{
  cursor: pointer;
}
.primary{
  background-color: var(--color-primaryButton);
  color: white;
}
.secondary{
  background-color: var(--color-secondaryButton);
  color: black;
}

</style>
