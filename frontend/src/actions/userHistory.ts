import { API_BASE_URL, type userHistoryRequest } from "@/types";
import { FetchWithoutParse } from "@/util/fetch";

/**
 * Pushes user history to the server
 * @param req The request object containing the params for the user history (listingId)
 * @throws if the request fails and the user history could not be pushed
 */
export const pushUserHistory = (req: userHistoryRequest) => {
    return FetchWithoutParse(`${API_BASE_URL}/api/history`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(req),
    }
    );
}