import {
  API_BASE_URL,
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
  });
}

export const getRecommendedListings = async (req: GetListingsRequest): Promise<GetListingsResponse> => {
  return await Fetch(`${API_BASE_URL}/api/recommended`);
}

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

export const getAllListings = async (): Promise<Listing[]> => {
  return await Fetch(`${API_BASE_URL}/api/listing/all`);
}

export const useGetAllListings = () => {
  return useQuery({
    queryKey: ['listings', 'all'],
    queryFn: async () => {
      return getAllListings();
    },
  });
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

export const searchListings = async (queryString: string, page: number): Promise<GetListingsResponse> => {
  const pageParams = new URLSearchParams();
  pageParams.append("page", page.toString());
  pageParams.append("size", PAGE_SIZE.toString());
  return await Fetch(`${API_BASE_URL}/api/search?${queryString.toString()}&${pageParams.toString()}`);
}

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
export const purchaseListing = async (
  uuid: string,
): Promise<DefaultResponse> => {
  return await Fetch(`${API_BASE_URL}/api/listing/${uuid}/purchase`, {
    method: "POST"
  });
};
export const usePurchaseListing = () => {
  return useMutation({
    mutationFn: ({ uuid }: { uuid: string }) =>
      purchaseListing(uuid),
  });
};
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

export const useReserveListing = () => {
  return useMutation({
    mutationFn: ({ uuid }: { uuid: string }) =>
      reserveListing(uuid)
  });
};
export const checkForReservation = async (
  uuid: string
): Promise<ReservationResponse> => {
  const params = new URLSearchParams({ listingId: uuid });
  return await Fetch(`${API_BASE_URL}/api/reservation/${uuid.toString()}`);
}
export const useCheckForReservation = (uuid: string, enabled: boolean) => {
  return useQuery({
    queryKey: ['reservation', uuid],
    queryFn: () => checkForReservation(uuid),
    enabled
  });
};

export const toggleArchiveListing = async (
  uuid: string,
  state: boolean
): Promise<DefaultResponse> => {

  const params = new URLSearchParams({ state: state.toString() });
  return await Fetch(`${API_BASE_URL}/api/listing/${uuid}/archive?${params.toString()}`, {
    method: "POST",
  });
};
export const useToggleArchive = () => {
  return useMutation({
    mutationFn: ({ uuid, state }: { uuid: string; state: boolean }) =>
      toggleArchiveListing(uuid, state),
  });
};

export const getArchivedListingsByUser = async (
  userId: number,
  page: number
): Promise<GetListingsResponse> => {
  const params = new URLSearchParams();
  params.append("page", page.toString());
  params.append("offset", PAGE_SIZE.toString());
  return await Fetch(`${API_BASE_URL}/api/listing/user/${userId}/archived?${params.toString()}`);
};

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







