import { API_BASE_URL, type GetListingBookmarksResponse } from "@/types";
import Fetch from "@/util/fetch";
import { useMutation, useQuery } from "@tanstack/vue-query";

export const getListingBookmarks = async (listingId: string): Promise<GetListingBookmarksResponse> => {
    console.log("Fetching listing bookmarks", listingId);

    const bookMarkCount = await Fetch(`${API_BASE_URL}/api/bookmark/${listingId}/count`, {
        method: "GET",
    });

    console.log("Fetched listing bookmarks", bookMarkCount);

    const hasBookmarked = await Fetch(`${API_BASE_URL}/api/bookmark/${listingId}/exists`, {
        method: "GET",
    });

    console.log("Fetched listing bookmarks", bookMarkCount, hasBookmarked);

    return {
        bookMarkCount,
        hasBookmarked
    }
}

export const useListingBookmarks = (listingId: string) => {
    return useQuery({
        queryKey: ["listing", "bookmarks", listingId],
        queryFn: async () => {
            return await getListingBookmarks(listingId);
        }
    });
}

export const addBookmark = async (listingId: string): Promise<void> => {
    return await Fetch(`${API_BASE_URL}/api/bookmark`, {
        method: "POST",
        body: JSON.stringify({
            listingId: listingId,
        }),
        headers: {
            "Content-Type": "application/json"
        }
    });
}

export const removeBookmark = async (listingId: string): Promise<void> => {
    return await Fetch(`${API_BASE_URL}/api/bookmark`, {
        method: "DELETE",
        body: JSON.stringify({
            listingId: listingId,
        }),
        headers: {
            "Content-Type": "application/json"
        }
    });
}