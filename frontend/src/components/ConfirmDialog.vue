<script setup lang="ts">
import { inject } from 'vue';
import Button from './Button.vue';
import Alert from './Alert.vue';
import LoadingSpinner from './LoadingSpinner.vue';

interface DialogRef {
    data: {
        message: string;
        variant: "Caution" | "Warning" | "Info";
        confirmText?: string;
        cancelText?: string;
        confirmLoading?: boolean;
    },
    close: () => void;
}

const dialogRef = inject<DialogRef>("dialogRef");

defineEmits(["accept", "cancel"]);


</script>

<template>
    <div class="confirm-dialog">
        <div class="message">
            <Alert :variant="dialogRef?.data.variant ?? 'Caution'">
                {{ dialogRef?.data.message }}
            </Alert>
        </div>
        <div class="buttons">
            <Button @click="dialogRef?.close()" variant="outline" class="confirm-dialog-button">
                {{ dialogRef?.data.cancelText ?? $t("cancel") }}
            </Button>
            <Button @click="$emit('accept')" variant="primary" class="confirm-dialog-button">
                <template v-if="dialogRef?.data.confirmLoading">
                    <LoadingSpinner />
                </template>
                <template v-else>
                    {{ dialogRef?.data.confirmText ?? $t("confirm") }}
                </template>
            </Button>
        </div>
    </div>
</template>

<style scoped>
.confirm-dialog-button {
    width: 5rem;
    height: 1.5rem;
    font-size: 0.8rem;
    font-weight: 500;
}

.confirm-dialog {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    align-items: center;
}

.message {
    font-size: 1rem;
}

.buttons {
    display: flex;
    flex-direction: row;
    gap: 1rem;
}
</style>