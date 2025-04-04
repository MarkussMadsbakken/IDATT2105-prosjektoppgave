import { API_BASE_URL, ApiError, type CreateListingRequest, type CreateListingResponse, type LoginRequest, type LoginResponse, type DefaultResponse, type EditListingRequest } from "@/types";
import Fetch from "@/util/fetch";
import { objectOmit } from "@vueuse/core";

/**
 * Sends a login request to the api
 */
export const createListing = async (req: CreateListingRequest): Promise<CreateListingResponse> => {
    const body = new FormData();
    if (req.images.length === 0) {
        body.append("images", "");
    }
    req.images.forEach((image) => {
        body.append("images", image);
    });

    const listingRequestBlob = new Blob(
        [JSON.stringify({ ...objectOmit(req, ["images"]) })],
        { type: "application/json" }
    );
    body.append("listingRequest", listingRequestBlob);

    return await Fetch(`${API_BASE_URL}/api/listing`, {
        method: "POST",
        body: body
    });
}

export const editListing = async (req: EditListingRequest): Promise<DefaultResponse> => {
    return await Fetch(`${API_BASE_URL}/api/listing`, {
        method: "PUT",
        body: JSON.stringify(req),
        headers: {
            "Content-Type": "application/json"
        }
    });
}

export const deleteListing = async (listingId: string): Promise<void> => {
    return await Fetch(`${API_BASE_URL}/api/listing/${listingId}`, {
        method: "DELETE"
    });
}