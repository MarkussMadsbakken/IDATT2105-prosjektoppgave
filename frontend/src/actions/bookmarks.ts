import { API_BASE_URL, type GetListingBookmarksResponse } from "@/types";
import Fetch, { FetchWithoutParse } from "@/util/fetch";
import { useMutation, useQuery } from "@tanstack/vue-query";
import { DomUtil } from "leaflet";
import enableImageDrag = DomUtil.enableImageDrag;

export const getListingBookmarks = async (listingId: string): Promise<GetListingBookmarksResponse> => {
  const bookMarkCount = await Fetch(`${API_BASE_URL}/api/bookmark/${listingId}/count`, {
    method: "GET",
  });
  return {
    bookMarkCount,
  }
}
export const getHasBookmarked = async (listingId: string): Promise<boolean> => {
  const hasBookmarked = await Fetch(`${API_BASE_URL}/api/bookmark/${listingId}/exists`, {
    method: "GET",
  });
  return hasBookmarked;
};

export const useHasBookmarked = (listingId: string, options?: { enabled?: boolean }) => {
  return useQuery({
    queryKey: ["listing", "bookmarks", listingId, "exists"],
    queryFn: () => getHasBookmarked(listingId),
    enabled: options?.enabled ?? true,
  });
};

export const useListingBookmarks = (listingId: string) => {
  return useQuery({
    queryKey: ["listing", "bookmarks", listingId],
    queryFn: async () => {
      return await getListingBookmarks(listingId);
    }
  });
}

export const addBookmark = async (listingId: string): Promise<void> => {
  await FetchWithoutParse(`${API_BASE_URL}/api/bookmark`, {
    method: "POST",
    body: JSON.stringify({ listingId: listingId }),
    headers: {
      "Content-Type": "application/json"
    }
  });
}

export const removeBookmark = async (listingId: string): Promise<void> => {
  await FetchWithoutParse(`${API_BASE_URL}/api/bookmark`, {
    method: "DELETE",
    body: JSON.stringify({ listingId: listingId }),
    headers: {
      "Content-Type": "application/json"
    }
  });

}
