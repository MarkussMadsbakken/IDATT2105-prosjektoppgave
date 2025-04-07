import { useAuth } from "@/stores/auth";
import { ApiError } from "@/types";

/**
 * Sends a fetch request with the token in the Authorization header
 */
export default async function Fetch(input: string | URL | globalThis.Request, init?: RequestInit) {
    const res = await FetchWithoutParse(input, init);

    if (!res.ok) {
        const err = await res.json();
        console.log(err);
        throw new ApiError(err.error);
    }

    return res.json();
}

export async function FetchWithoutParse(input: string | URL | globalThis.Request, init?: RequestInit) {
    const auth = useAuth();

    const headers = new Headers(init?.headers);


    if (auth.loggedIn) {
        headers.set("Authorization", `Bearer ${auth.rawToken}`);
    }

    return await fetch(input, {
        ...(init || {}),
        headers: headers
    });
};
