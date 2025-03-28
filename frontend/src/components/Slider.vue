<script setup lang="ts">
import { ref, watch } from 'vue';
import Slider from "primevue/slider"

const props = defineProps<{
  modelValue: [number, number]
  min?: number
  max?: number
  step?: number
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: [number, number]): void
}>()

const localValue = ref<[number,number]>([...props.modelValue])

watch(
  ()=> props.modelValue,
  (newVal) => {
    localValue.value = [...newVal]
  })

const minDistance = props.step ?? 5

watch(localValue, (newVal) => {
  const [start, end] = newVal

  if (end - start < minDistance) {
    if (start !== props.modelValue[0]) {
      localValue.value = [end - minDistance, end]
    } else {
      localValue.value = [start, start + minDistance]
    }
    return
  }

  emit('update:modelValue', newVal)
})


</script>

<template>
  <div class ="custom-slider">
    <div class="slider-labels">


      <span class="min">{{ localValue[0] }}</span>
          <div class ="container">
            <Slider
              v-model="localValue"
              :range="true"
              :min="props.min ?? 0"
              :max="props.max ?? 100"
              :step="props.step ?? 1"
              />
        </div>
      <span class="max">{{ localValue[1] }}</span>
    </div>
    <slot/>
  </div>
</template>

<style scoped>
.custom-slider :deep(.p-slider) {
  background-color: white;
  height: 8px;
  border-radius: 4px;
  position: relative;
}

.custom-slider :deep(.p-slider-range) {
  background-color: #dcdbdb;
}

.custom-slider :deep(.p-slider-handle::before) {
  background-color: transparent;
  box-shadow: none;
}

.custom-slider :deep(.p-slider-handle) {
  width: 16px;
  height: 16px;
  background-color: black;
  border: 2px solid black;
  border-radius: 50%;
  margin-top: -8px;
}
.slider-labels{
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
}
.slider-labels .min,
.slider-labels .max {
  font-size: 0.9rem;
  color: #333;
  width: 30px;
}

.container {
  width: 300px;
  padding: 1rem;
  background-color: rgba(0, 0, 0, 0.05);
  border-radius: 12px;
}

</style>
