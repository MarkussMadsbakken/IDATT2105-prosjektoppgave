import { API_BASE_URL, PAGE_SIZE, type GetListingsRequest, type GetListingsResponse, type Listing, type Page } from "@/types";
import Fetch from "@/util/fetch";
import { useInfiniteQuery, useQuery } from "@tanstack/vue-query";

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
            return getListings({ page: pageParam, offset: pageParam + PAGE_SIZE });
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