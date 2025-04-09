import { API_BASE_URL, type userHistoryRequest } from "@/types";
import { FetchWithoutParse } from "@/util/fetch";

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