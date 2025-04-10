import { API_BASE_URL, ApiError, type CreateListingRequest, type CreateListingResponse, type LoginRequest, type LoginResponse, type DefaultResponse, type EditListingRequest } from "@/types";
import Fetch from "@/util/fetch";
import { objectOmit } from "@vueuse/core";

/**
 * Creates a new listing with the given params
 * @param req The request object containing the params for the new listing
 * @returns The created listing
 * @throws if the request fails and the listing could not be created
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

/**
 * Modifies a listing with the given params
 * @param req The request object containing the params for the listing
 * @returns A response object containing a message
 * @throws if the request fails and the listing could not be modified
 */
export const editListing = async (req: EditListingRequest): Promise<DefaultResponse> => {
    return await Fetch(`${API_BASE_URL}/api/listing`, {
        method: "PUT",
        body: JSON.stringify(req),
        headers: {
            "Content-Type": "application/json"
        }
    });
}

/**
 * Deletes a listing with the given id
 * @param listingId The id of the listing to delete
 * @throws if the request fails and the listing could not be deleted
 */
export const deleteListing = async (listingId: string): Promise<void> => {
    return await Fetch(`${API_BASE_URL}/api/listing/${listingId}`, {
        method: "DELETE"
    });
}
