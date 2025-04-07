<template>
    <div class="chat-card-wrapper">
        <div class="image-container">
            <div class="skeleton-image"></div>
        </div>
        <div class="chat-card-content">
            <div class="chat-card-header">
                <div class="chat-card-title-skeleton">
                </div>
                <div class="chat-card-price-skeleton">
                </div>
            </div>
            <div class="chat-card-message-skeleton">
            </div>
            <div class="chat-card-time-skeleton">
            </div>
        </div>
    </div>
</template>

<style scoped>
.chat-card-message-skeleton {
    margin-top: 0.5rem;
    width: 10rem;
    height: 1rem;
    background-color: var(--color-skeleton);
    border-radius: 10px;
    animation: pulse 1.5s infinite;
}

.chat-card-time-skeleton {
    position: absolute;
    bottom: 1rem;
    width: 9rem;
    height: 1rem;
    background-color: var(--color-skeleton);
    border-radius: 10px;
    animation: pulse 1.5s infinite;
}

.chat-card-price-skeleton {
    width: 3rem;
    height: 1rem;
    background-color: var(--color-skeleton);
    border-radius: 10px;
    animation: pulse 1.5s infinite;
}

.chat-card-title-skeleton {
    width: 10rem;
    height: 1.5rem;
    background-color: var(--color-skeleton);
    border-radius: 10px;
    animation: pulse 1.5s infinite;
}

.chat-card-header {
    display: flex;
    flex-direction: row;
    text-align: justify;
    align-items: baseline;
    gap: 0.5rem;
}

.chat-card-content {
    margin-top: 1rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    position: relative;
    width: 100%;
    height: 100%;
}

.image-container {
    padding: 0.5rem;
    width: 8rem;
    height: 8rem;
    border-radius: 10px;
    overflow: hidden;
    flex-shrink: 0;
}

.skeleton-text {
    width: 5rem;
    height: 1rem;
    background-color: var(--color-skeleton);
    border-radius: 10px;
    animation: pulse 1.5s infinite;
}

.skeleton-image {
    width: 100%;
    height: 100%;
    background-color: var(--color-skeleton);
    animation: pulse 1.5s infinite;
}

.chat-card-wrapper {
    flex-shrink: 0;
    display: flex;
    flex-direction: row;
    align-items: center;
    width: 100%;
    height: 8rem;
    border-radius: 10px;
    cursor: pointer;
    background-color: white;
    border: 1px solid oklch(92.2% 0 0);
    box-shadow: 0 10px 15px -3px rgb(0 0 0 / 0.1), 0 4px 6px -4px rgb(0 0 0 / 0.1);
}
</style>