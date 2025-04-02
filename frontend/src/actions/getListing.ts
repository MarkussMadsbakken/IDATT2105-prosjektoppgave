import { API_BASE_URL, type GetListingsRequest, type GetListingsResponse, type Page } from "@/types";
import Fetch from "@/util/fetch";
import { useInfiniteQuery } from "@tanstack/vue-query";

export const getListings = async (req: GetListingsRequest): Promise<Page<GetListingsResponse>> => {
    const params = new URLSearchParams();
    params.append("page", req.page.toString());
    params.append("offset", req.offset.toString());
    let a = await Fetch(`${API_BASE_URL}/api/listing?${params.toString()}`);
    console.log(a);
    return a;
}

/*
export const useGetListings = useInfiniteQuery({
    queryKey: ["listings"],
    queryFn: ({ pageParam = 1 }) => getListings({ page: pageParam, offset: 10 }),
    getNextPageParam: (lastPage) => {
        if (lastPage.cursor === -1) {
            return undefined;
        }
        return lastPage.cursor;
    },
    initialPageParam: undefined
});
*/