<script setup lang="ts">

const props = withDefaults(defineProps<{
  variant : 'primary' | 'secondary' | 'outline' | 'destructive' | 'accept'
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
  font-weight: normal;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.2);
  padding: 0.5rem 1rem;
  font-size: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
}
.base-button:hover{
  cursor: pointer;
}
.primary{
  font-weight: bold;
  background-color: var(--color-primaryButton);
  color: white;
}
.secondary{
  font-weight: bold;
  background-color: var(--color-secondaryButton);
  color: black;
}
.outline{
  outline: black;
  border: 1px solid black;
}
.destructive{
  background-color: var(--color-destructiveButton);
  color: white;
  font-weight: bold;
}
.accept{
  background-color: var(--color-acceptButton);
  color: white;
  font-weight: bold;
}

</style>
