import { API_BASE_URL, type Image } from "@/types";
import Fetch from "@/util/fetch";
import { useQuery } from "@tanstack/vue-query";

export const getListingImages = async (listingId: string): Promise<Image[]> => {
    return await Fetch(`${API_BASE_URL}/api/listing/${listingId}/images`);
}

export const useGetListingImages = (listingId: string) => {
    return useQuery({
        queryKey: ['listing', listingId, 'images'],
        queryFn: async () => {
            return getListingImages(listingId);
        }
    });
}