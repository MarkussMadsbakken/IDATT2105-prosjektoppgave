import { API_BASE_URL, ApiError, type CreateListingRequest, type CreateListingResponse, type LoginRequest, type LoginResponse } from "@/types";
import Fetch from "@/util/fetch";
import { useMutation } from "@tanstack/vue-query";

/**
 * Sends a login request to the api
 */
export const createListing = async (req: CreateListingRequest): Promise<CreateListingResponse> => {
    let res = await Fetch(`${API_BASE_URL}/api/listing`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(req),
    });

    if (!res.ok) {
        const err = await res.json();
        throw new ApiError(err.error);
    }

    return res.json();
}

/**
 * Hook for logging in via a mutation
 */
const useCreateListing = (params?: { onSuccess: () => void }) => {
    return useMutation({
        mutationFn: createListing,
        onSuccess: params?.onSuccess
    });
}

export default useCreateListing;