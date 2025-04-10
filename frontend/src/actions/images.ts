import { API_BASE_URL, type Image } from "@/types";
import Fetch from "@/util/fetch";
import { useQuery } from "@tanstack/vue-query";

/**
 * Gets all images for a listing
 * @param listingId The id of the listing to get images for
 * @returns A list of base64 encoded images
 * @throws if the request fails and the images could not be retrieved
 */
export const getListingImages = async (listingId: string): Promise<Image[]> => {
    return await Fetch(`${API_BASE_URL}/api/listing/${listingId}/images`);
}

/**
 * Hook to get all images for a listing
 * @param listingId The id of the listing to get images for
 * @returns A query object containing the images
 */
export const useGetListingImages = (listingId: string) => {
    return useQuery({
        queryKey: ['listing', listingId, 'images'],
        queryFn: async () => {
            return getListingImages(listingId);
        }
    });
}