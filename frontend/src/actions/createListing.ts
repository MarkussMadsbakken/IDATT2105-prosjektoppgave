import { API_BASE_URL, ApiError, type CreateListingRequest, type CreateListingResponse, type LoginRequest, type LoginResponse } from "@/types";
import Fetch from "@/util/fetch";
import { useMutation } from "@tanstack/vue-query";
import { objectOmit } from "@vueuse/core";

/**
 * Sends a login request to the api
 */
export const createListing = async (req: CreateListingRequest): Promise<CreateListingResponse> => {
    const body = new FormData();
    if (req.images.length === 0) {
        body.append("pictures", " 2");
    }
    req.images.forEach((image) => {
        body.append("pictures", image);
    });

    const listingRequestBlob = new Blob(
        [JSON.stringify({ ...objectOmit(req, ["images"]) })],
        { type: "application/json" }
    );
    body.append("listingRequest", listingRequestBlob);

    console.log(body.get("listingRequest"));
    console.log(body.get("pictures"));

    return await Fetch(`${API_BASE_URL}/api/listing/`, {
        method: "POST",
        body: body
    });
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