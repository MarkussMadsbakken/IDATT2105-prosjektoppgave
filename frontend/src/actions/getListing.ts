import {
  API_BASE_URL,
  EMPTY_PAGE,
  PAGE_SIZE,
  type GetListingsRequest,
  type GetListingsResponse,
  type Listing,
  type Page,
} from "@/types";
import type { DefaultResponse, ReservationResponse } from "@/types/apiResponses";
import Fetch from "@/util/fetch";
import { useInfiniteQuery, useMutation, useQuery } from "@tanstack/vue-query";
import { getListingImages } from "./images";
import type { Ref } from "vue";
import { getCategories } from "./categories";

/**
 * Gets a page of listings from the API
 * @param req The request object containing the page and offset
 * @returns A page of listings, or an empty page if no listings were found
 * @throws if the request fails and the listings could not be retrieved
 */
export const getListings = async (req: GetListingsRequest): Promise<GetListingsResponse> => {
  const params = new URLSearchParams();
  params.append("page", req.page.toString());
  params.append("offset", req.offset.toString());

  const listings = await Fetch(`${API_BASE_URL}/api/listing?${params.toString()}`);

  if (!listings) {
    return EMPTY_PAGE;
  }
  return listings;
}

/**
 * Hook for getting an infinite query of listings
 * @returns A query object containing the listings
 */
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
  });
}

/**
 * Gets a page of recommended listings from the API
 * @param req The request object containing the page and offset
 * @returns A page of recommended listings, or a normal page of listings if no recommendations were found
 */
export const getRecommendedListings = async (req: GetListingsRequest): Promise<GetListingsResponse> => {
  let res = await Fetch(`${API_BASE_URL}/api/recommended`);

  // If we have no recommendations, fallback to the default listings
  if (!res) {
    return await getListings(req);
  }
  return res;
}

/**
 * Hook for getting recommended listings
 * @returns A query object containing the recommended listings, 
 * or a normal page of listings if no recommendations were found
 */
export const useGetRecommendedListings = () => {
  return useInfiniteQuery({
    queryKey: ['listings'],
    queryFn: async ({ pageParam = 0 }) => {
      return getRecommendedListings({ page: pageParam, offset: PAGE_SIZE });
    },
    getNextPageParam: (lastPage: Page<Listing>) => {
      if (lastPage.last) {
        return undefined;
      }

      return lastPage.number + 1;
    },
    initialPageParam: 0
  });
}

/**
 * Gets all listings from the API
 * @returns A list of all listings
 * @throws if the request fails and the listings could not be retrieved
 */
export const getAllListings = async (): Promise<Listing[]> => {
  return await Fetch(`${API_BASE_URL}/api/listing/all`);
}

/**
 * Hook for getting all listings
 * @returns A query object containing the listings
 */
export const useGetAllListings = () => {
  return useQuery({
    queryKey: ['listings', 'all'],
    queryFn: async () => {
      return getAllListings();
    },
  });
}

/**
 * Gets a specific listing from the API
 * @param uuid The id of the listing to get
 * @returns The listing object
 * @throws if the request fails and the listing could not be retrieved
 */
export const getListing = async (uuid: string): Promise<Listing> => {
  return await Fetch(`${API_BASE_URL}/api/listing/${uuid}`);
}

/**
 * Hook for getting a specific listing
 * @param uuid The id of the listing to get
 * @returns A query object containing the listing
 */
export const useGetListing = (uuid: string) => {
  return useQuery({
    queryKey: ['listing', uuid],
    queryFn: async () => {
      return getListing(uuid);
    }
  });
}

/**
 * Gets a specific listing with its category from the API
 * @param uuid The id of the listing to get
 * @returns The listing object including its category
 * @throws if the request fails and the listing could not be retrieved
 */
export const useGetListingWithCategory = (uuid: string) => {
  return useQuery({
    queryKey: ['listing', uuid],
    queryFn: async () => {
      const listing = await getListing(uuid);
      const category = await getCategories();
      return {
        ...listing,
        category: category.find((cat) => cat.id === listing.category)
      }
    }
  });
}

/**
 * Gets a listing with its images from the API
 * @param uuid The id of the listing to get
 * @returns The listing object including its images
 * @throws if the request fails and the listing could not be retrieved
 */
export const getListingWithImages = async (uuid: string) => {
  const listing = await getListing(uuid);
  const images = await getListingImages(uuid);
  return {
    listing: listing,
    images: images
  }
}

/**
 * Hook for getting a listing with its images
 * @param uuid The id of the listing to get
 * @returns A query object containing the listing and its images
 */
export const useGetListingWithImages = (uuid: string) => {
  return useQuery({
    queryKey: ["listing", uuid],
    queryFn: async () => { return await getListingWithImages(uuid) }
  })
}

/**
 * Gets a page of listings from the API based on a search query
 * @param queryString The search query string to use
 * @param page The page number to get
 * @returns A page of listings matching the search query, or an empty page if no listings were found
 * @throws if the request fails and the listings could not be retrieved
 */
export const searchListings = async (queryString: string, page: number): Promise<GetListingsResponse> => {
  const pageParams = new URLSearchParams();
  pageParams.append("page", page.toString());
  pageParams.append("size", PAGE_SIZE.toString());
  const listings = await Fetch(`${API_BASE_URL}/api/search?${queryString.toString()}&${pageParams.toString()}`);

  if (!listings) {
    return EMPTY_PAGE;
  }
  return listings;
}

/**
 * Hook for getting search results
 * @param queryString The search query string to use
 * @returns A query object containing the search results
 */
export const useSearchListings = (queryString: Ref<string>) => {
  return useInfiniteQuery({
    queryKey: ['search', queryString],
    queryFn: async ({ pageParam = 0 }) => {
      return searchListings(queryString.value, pageParam);
    },
    getNextPageParam: (lastPage: Page<Listing>) => {
      if (lastPage.last) {
        return undefined;
      }

      return lastPage.number + 1;
    },
    initialPageParam: 0,
    retry: 1,
  })
}

/**
 * Purchases a listing with the given id
 * @param uuid The id of the listing to purchase
 * @returns A response object containing a message
 * @throws if the request fails and the listing could not be purchased
 */
export const purchaseListing = async (uuid: string,): Promise<DefaultResponse> => {
  return await Fetch(`${API_BASE_URL}/api/listing/${uuid}/purchase`, {
    method: "POST"
  });
};

/**
 * Hook for purchasing a listing
 * @returns A mutation object for purchasing a listing
 */
export const usePurchaseListing = () => {
  return useMutation({
    mutationFn: ({ uuid }: { uuid: string }) =>
      purchaseListing(uuid),
  });
};

/**
 * Reserves a listing with the given id
 * @param uuid The id of the listing to reserve
 * @returns A response object containing a message
 * @throws if the request fails and the listing could not be reserved
 */
export const reserveListing = async (
  uuid: string
): Promise<ReservationResponse> => {
  return await Fetch(`${API_BASE_URL}/api/reservation`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ "listingId": uuid })
  });
};

/**
 * Hook for reserving a listing
 * @returns A mutation object for reserving a listing
 */
export const useReserveListing = () => {
  return useMutation({
    mutationFn: ({ uuid }: { uuid: string }) =>
      reserveListing(uuid)
  });
};

/**
 * Checks if a reservation exists for a listing with the given id
 * @param uuid The id of the listing to check
 * @returns A response object containing the reservation details
 * @throws if the request fails and the reservation could not be checked
 */
export const checkForReservation = async (uuid: string): Promise<ReservationResponse> => {
  return await Fetch(`${API_BASE_URL}/api/reservation/${uuid.toString()}`);
}

/**
 * Hook for checking if a reservation exists for a listing
 * @param uuid The id of the listing to check
 * @param enabled Whether the query should be enabled or not
 * @returns A query object containing the reservation details
 */
export const useCheckForReservation = (uuid: string, enabled: boolean) => {
  return useQuery({
    queryKey: ['reservation', uuid],
    queryFn: () => checkForReservation(uuid),
    enabled
  });
};

/**
 * Toggles the archive state of a listing with the given id
 * @param uuid The id of the listing to toggle
 * @param state The new state of the listing (true for archived, false for unarchived)
 * @returns A default server response
 * @throws if the request fails and the listing could not be changed
 */
export const toggleArchiveListing = async (uuid: string, state: boolean): Promise<DefaultResponse> => {
  const params = new URLSearchParams({ state: state.toString() });
  return await Fetch(`${API_BASE_URL}/api/listing/${uuid}/archive?${params.toString()}`, {
    method: "POST",
  });
};

/**
 * Hook for toggling the archive state of a listing
 * @returns A mutation object for toggling the archive state
 */
export const useToggleArchive = () => {
  return useMutation({
    mutationFn: ({ uuid, state }: { uuid: string; state: boolean }) =>
      toggleArchiveListing(uuid, state),
  });
};

/**
 * Gets a page of archived listings for a user
 * @param userId The id of the user to get archived listings for
 * @param page The page number to get
 * @returns A page of archived listings for the user
 * @throws if the request fails and the listings could not be retrieved
 */
export const getArchivedListingsByUser = async (userId: number, page: number): Promise<GetListingsResponse> => {
  const params = new URLSearchParams();
  params.append("page", page.toString());
  params.append("offset", PAGE_SIZE.toString());
  return await Fetch(`${API_BASE_URL}/api/listing/user/${userId}/archived?${params.toString()}`);
};

/**
 * Hook for getting archived listings for a user
 * @param userId The id of the user to get archived listings for
 * @returns A query object containing the archived listings
 */
export const useGetArchivedListingsByUser = (userId: number) => {
  return useInfiniteQuery({
    queryKey: ["archivedListings", userId],
    queryFn: ({ pageParam = 0 }) => getArchivedListingsByUser(userId, pageParam),
    getNextPageParam: (lastPage) => {
      if (lastPage.last) return undefined;
      return lastPage.number + 1;
    },
    initialPageParam: 0,
    enabled: !!userId,
  });
};







