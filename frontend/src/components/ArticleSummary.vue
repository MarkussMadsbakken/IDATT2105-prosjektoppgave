<script setup lang="ts">

import type {Listing} from "@/types";
import {useGetListing} from "@/actions/getListing.ts";
import ListingHeaderImage from "@/components/ListingHeaderImage.vue";
import FormGroup from "@/components/FormGroup.vue";
import Divider from "@/components/Divider.vue";
import SellerInfo from "@/components/SellerInfo.vue";

const props = defineProps<{
  listingId: string;
}>();

const {
  data: listing,
  isError,
  error,
  isPending
} = useGetListing(props.listingId);


</script>

<template>
  <div class="title-wrapper">
      <div class="title">{{ $t('checkout.buy') }}</div>
  </div>

  <div class="summary-wrapper">
    <div class="oppsummert">{{ $t('inSummary') }}</div>
    <Divider class="divider"/>
    <div class="content">
      <div class="image-wrapper">
        <ListingHeaderImage :listing-id="listing!.uuid" />
      </div>
      <div class="content-wrapper">
        <div class="title-description-wrapper">
          <div class="article-title" >
            {{ listing?.name }}
          </div>
          <div class="description">
            {{ listing?.description }}
          </div>
        </div>
        <div class="price-seller">
          {{ listing?.price }}kr
          <SellerInfo class="seller-info" :user-id="listing!.ownerId" :size="'small'"></SellerInfo>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
  .title {
    font-size: 4rem;
    font-weight: bold;
  }

  .title-wrapper {
    text-align: center;
    margin-bottom: 0.2rem;
  }
  .image-wrapper {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
  }
  .oppsummert{
    font-size: 2rem;
    padding: 0;
    margin-bottom: 0.5rem;
  }
  .content-wrapper {
    flex: 1;
    padding: 1rem;
    display: flex;
    gap: 0.5rem;
    flex-direction: column;
    justify-content: space-between;
    text-align: left;
  }
  .description {
    line-height: 1.6;
    font-weight: 400;
    font-size: smaller;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
  }
  .summary-wrapper{
      background-color: oklch(97.7% 0.013 236.62);
      border-radius: 1rem;
      max-width: 900px;
      margin: 2rem auto;
      display: flex;
      flex-direction: column;
      box-shadow: 0 1px 2px 0 rgb(0 0 0 / 0.05);
      text-align: center;
      padding-left: 2rem;
    padding-bottom: 2rem;
  }
  .image-wrapper{
    border: solid black;
    border-radius: 0.4rem;
    max-width: 15rem;
    max-height: 15rem;
  }
  .article-title {
    font-size: 2rem;
    font-weight: bold;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    line-height: 1;
  }
  .title-description-wrapper{
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
  }
  .price-seller{
    display: flex;
    align-items: center;
    font-size: 2rem;
    font-weight: bold;
    justify-content: space-between;
  }
  .image-wrapper img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  .content {
    display: flex;
    flex-direction: row;
  }
  .divider {
    border-color: black;
    border-width: 2px;
    margin-bottom: 1rem;
  }
  .seller-info {
      margin-left: auto;
      transform: scale(0.7);
  }
</style>
