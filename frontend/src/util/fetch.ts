import { useAuth } from "@/stores/auth";
import { ApiError } from "@/types";

/**
 * Sends a fetch request with the token in the Authorization header
 */
export default async function Fetch(input: string | URL | globalThis.Request, init?: RequestInit) {
    const auth = useAuth();

    let res = await fetch(input, init && {
        ...init,
        headers: {
            ...(init?.headers || {}),
            Authorization: `Bearer ${auth.rawToken}`
        }
    });

    if (res.status === 403) {
        auth.logout();
        throw new ApiError("Not authorized");
    }

    if (!res.ok) {
        const err = await res.json();
        console.log(err);
        throw new ApiError(err.error);
    }

    return res.json();
}