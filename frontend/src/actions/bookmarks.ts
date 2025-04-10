import { API_BASE_URL, type GetListingBookmarksResponse } from "@/types";
import Fetch, { FetchWithoutParse } from "@/util/fetch";
import { useMutation, useQuery } from "@tanstack/vue-query";
import { DomUtil } from "leaflet";
import enableImageDrag = DomUtil.enableImageDrag;

/**
 * Gets the amount of bookmarks for a listing
 * @param listingId   The id of the listing to get the bookmarks for
 * @returns the amount of bookmarks for the listing
 */
export const getListingBookmarks = async (listingId: string): Promise<GetListingBookmarksResponse> => {
  const bookMarkCount = await Fetch(`${API_BASE_URL}/api/bookmark/${listingId}/count`, {
    method: "GET",
  });
  return {
    bookMarkCount,
  }
}

/**
 * Gets wether the user has bookmarked a certain listing.
 * @param listingId  The id of the listing to check
 * @returns  true if the user has bookmarked the listing, false otherwise
 */
export const getHasBookmarked = async (listingId: string): Promise<boolean> => {
  const hasBookmarked = await Fetch(`${API_BASE_URL}/api/bookmark/${listingId}/exists`, {
    method: "GET",
  });
  return hasBookmarked;
};

/**
 * Hook to get wether the user has bookmarked a certain listing
 * @param listingId  The id of the listing to check
 * @param options  Optional options to pass to the query
 * @returns   true if the user has bookmarked the listing, false otherwise
 */
export const useHasBookmarked = (listingId: string, options?: { enabled?: boolean }) => {
  return useQuery({
    queryKey: ["listing", "bookmarks", listingId, "exists"],
    queryFn: () => getHasBookmarked(listingId),
    enabled: options?.enabled ?? true,
  });
};

/**
 * Hook to get the amount of bookmarks for a listing
 * @param listingId  The id of the listing to get the bookmarks for
 * @returns  the amount of bookmarks for the listing
 */
export const useListingBookmarks = (listingId: string) => {
  return useQuery({
    queryKey: ["listing", "bookmarks", listingId],
    queryFn: async () => {
      return await getListingBookmarks(listingId);
    }
  });
}

/**
 * Adds a bookmark to a listing
 * @param listingId  The id of the listing to add the bookmark to
 * @throws if the request fails and the bookmark could not be added
 */
export const addBookmark = async (listingId: string): Promise<void> => {
  await FetchWithoutParse(`${API_BASE_URL}/api/bookmark`, {
    method: "POST",
    body: JSON.stringify({ listingId: listingId }),
    headers: {
      "Content-Type": "application/json"
    }
  });
}

/**
 *  Removes a bookmark from a listing
 * @param listingId  The id of the listing to remove the bookmark from
 * @throws if the request fails and the bookmark could not be removed
 */
export const removeBookmark = async (listingId: string): Promise<void> => {
  await FetchWithoutParse(`${API_BASE_URL}/api/bookmark`, {
    method: "DELETE",
    body: JSON.stringify({ listingId: listingId }),
    headers: {
      "Content-Type": "application/json"
    }
  });

}
