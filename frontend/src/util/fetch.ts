import { logout, useAuth } from "@/stores/auth";
import { ApiError } from "@/types";
import { useToast } from "primevue/usetoast";

/**
 * Sends a fetch request with the token in the Authorization header
 */
export default async function Fetch(input: string | URL | globalThis.Request, init?: RequestInit) {
    const res = await FetchWithoutParse(input, init);

    if (!res.ok) {
        if (res.status === 401) {
            logout();
            return;
        }

        const err = await res.json();
        console.log(err);
        throw new ApiError(err.error);
    }

    if (res.status === 204) {
        return null;
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
