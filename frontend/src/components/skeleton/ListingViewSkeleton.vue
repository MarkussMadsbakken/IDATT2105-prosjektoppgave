<script setup lang="ts">
import Divider from '../Divider.vue';
import CategoryCardSkeleton from './CategoryCardSkeleton.vue';
import SellerInfoSkeleton from './SellerInfoSkeleton.vue';

</script>

<template>
    <div class="listing">
        <div class="listing-images-skeleton"></div>
        <div class="picture-footer">
            <div class="listing-title-skeleton">
            </div>
            <div class="listing-actions">
                <div class="listing-price-skeleton"></div>
                <div class="listing-option-buttons-skeleon">
                </div>
            </div>
            <Divider />
        </div>
        <div class="listing-content">
            <div class="listing-description">
                <div class="listing-description-title">
                    {{ $t("listings.view.description") }}
                </div>
                <div class="listing-description-content-skeleton"></div>
            </div>
            <div class="listing-description-skeleton">
            </div>
            <div class="listing-details">
                <div class="listing-description-title">
                    {{ $t("listings.view.details") }}
                </div>
                <CategoryCardSkeleton />
                <div class="listing-publish-date-skeleton">
                </div>
            </div>
            <div class="listing-description-title">
                {{ $t("listings.view.seller") }}
            </div>
            <div class="seller-info-skeleton">
                <SellerInfoSkeleton />
            </div>
        </div>
    </div>
</template>

<style scoped>
.listing-description-content-skeleton {
    width: 20rem;
    max-width: 44rem;
    height: 2rem;
    background-color: var(--color-skeleton);
    border-radius: 0.5rem;
    animation: pulse 1.5s infinite ease-in-out;
}

.listing-price-skeleton {
    width: 10rem;
    height: 2rem;
    background-color: var(--color-skeleton);
    border-radius: 0.5rem;
    animation: pulse 1.5s infinite ease-in-out;
}

.listing-title-skeleton {
    width: 100%;
    max-width: 44rem;
    height: 2rem;
    background-color: var(--color-skeleton);
    border-radius: 0.5rem;
    animation: pulse 1.5s infinite ease-in-out;
}

.listing-images-skeleton {
    width: 100%;
    max-width: 44rem;
    height: 24.5rem;
    background-color: var(--color-skeleton);
    border-radius: 0.5rem;
    animation: pulse 1.5s infinite ease-in-out;
}

.buy-button {
    flex: 1;
    height: 2.5rem;
}

.seller-info {
    display: flex;
    flex-direction: column;
}

.listing-publish-date {
    margin-top: 0.5rem;
    display: flex;
    flex-direction: row;
    gap: 0.5rem;
}

.listing-details {
    max-width: 55rem;
    font-size: 20px;
    padding: 2px;
    margin-bottom: 2rem;
}

.listing-description-title {
    font-size: 1.2rem;
    font-weight: 600;
    margin-bottom: 0.5rem;
}

.buy-wrapper {
    display: flex;
    flex-direction: row;
    gap: 2rem;
    justify-content: space-between;
    align-items: center;
}

.listing-content {
    display: flex;
    flex-direction: column;
    max-width: 44rem;
    width: 100%;
}

.bookmark-count {
    font-size: 1rem;
    font-weight: bold;
    width: 1rem;
    text-align: center;
}

.bookmark {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
}

.bookmark.clickable {
    cursor: pointer;
}

.listing {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: start;
    padding: 1rem;
    gap: 2rem;
}

.listing-title {
    font-size: 1.8rem;
    font-weight: 600;
    width: 45rem;
    text-wrap: break-word;
    max-width: 100%;
}

.button-box {
    display: flex;
    flex-direction: row;
    gap: 2rem;
}

.listing-option-button {
    width: 5rem;
    height: 2rem;
    font-size: 0.8rem;
}

.active-button {
    background-color: #f0f0f0;
    border-color: #d5d4d4;
    color: #333;
}

.buy-box {
    display: flex;
    flex-direction: column;
    gap: 2rem;
    align-items: center;
}

.listing-description {
    max-width: 55rem;
    font-size: 20px;
    padding: 2px;
    margin-bottom: 2rem;
}

.title-picture {
    display: flex;
    flex-direction: column;
    gap: 2rem;
    align-items: center;
}

.picture-footer {
    display: flex;
    flex-direction: column;
    max-width: 44rem;
    width: 100%;
    gap: 1rem;
}

.listing-option-buttons {
    display: flex;
    flex-direction: row;
    gap: 1rem;
    justify-content: center;
    align-items: center;
}

.listing-actions {
    display: flex;
    flex-direction: row;
    gap: 1rem;
    justify-content: space-between;
    align-items: center;
}

.listing-price {
    font-size: 1.5rem;
    font-weight: bold;
}

.sold-warning {
    width: 45rem;
}

.is-disabled {
    background-color: #ccc !important;
    color: #999 !important;
    cursor: not-allowed !important;
    border: 1px solid #aaa !important;
    opacity: 0.7;
    pointer-events: none;
}
</style>