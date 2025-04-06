import { API_BASE_URL, PAGE_SIZE, type GetListingsRequest, type GetListingsResponse, type Listing, type Page } from "@/types";
import Fetch from "@/util/fetch";
import { useInfiniteQuery, useQuery } from "@tanstack/vue-query";
import { getListingImages } from "./images";

export const getListings = async (req: GetListingsRequest): Promise<GetListingsResponse> => {
    const params = new URLSearchParams();
    params.append("page", req.page.toString());
    params.append("offset", req.offset.toString());
    return await Fetch(`${API_BASE_URL}/api/listing?${params.toString()}`);
}


export const useGetListings = () => {
    return useInfiniteQuery({
        queryKey: ['listings'],
        queryFn: async ({ pageParam = 0 }) => {
            return getListings({ page: pageParam, offset: PAGE_SIZE });
        },
        getNextPageParam: (lastPage: Page<Listing>) => {
            if (lastPage.last) {
                return undefined;
            }

            return lastPage.number + 1;
        },
        initialPageParam: 0
    })
}

export const getListing = async (uuid: string): Promise<Listing> => {
    return await Fetch(`${API_BASE_URL}/api/listing/${uuid}`);
}

export const useGetListing = (uuid: string) => {
    return useQuery({
        queryKey: ['listing', uuid],
        queryFn: async () => {
            return getListing(uuid);
        }
    });
}

export const getListingWithImages = async (uuid: string) => {
    const listing = await getListing(uuid);
    const images = await getListingImages(uuid);
    return {
        listing: listing,
        images: images
    }
}

export const useGetListingWithImages = (uuid: string) => {
    return useQuery({
        queryKey: ["listing", uuid],
        queryFn: async () => { return await getListingWithImages(uuid) }
    })
}