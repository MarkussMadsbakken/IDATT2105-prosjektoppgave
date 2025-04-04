import { useAuth } from "@/stores/auth";
import { ApiError } from "@/types";

/**
 * Sends a fetch request with the token in the Authorization header
 */
export default async function Fetch(input: string | URL | globalThis.Request, init?: RequestInit) {
    const auth = useAuth();

    const headers = new Headers(init?.headers);


    if (auth.loggedIn) {
        headers.set("Authorization", `Bearer ${auth.rawToken}`);
    }

    let res = await fetch(input, {
        ...(init || {}),
        headers: headers
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
