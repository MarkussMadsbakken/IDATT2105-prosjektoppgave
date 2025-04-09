import { useAuth } from "@/stores/auth";
import { API_BASE_URL, ApiError, type LoginRequest, type LoginResponse } from "@/types";
import { useMutation, useQueryClient } from "@tanstack/vue-query";

// IMPORTANT: Both login and register actions do not use the extended "Fetch" function
// defned in /util/fetch.ts. This is because we do not yet have a token to send with the request.

/**
 * Sends a login request to the api
 */
export const login = async (req: LoginRequest): Promise<LoginResponse> => {
    let res = await fetch(`${API_BASE_URL}/api/auth/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(req),
    });

    if (!res.ok) {
        throw new ApiError("Bad credentials");
    }

    return res.json();
}

/**
 * Hook for logging in via a mutation
 */
const useLogin = (params: { onSuccess: () => void }) => {
    return useMutation({
        mutationFn: async (data: LoginRequest) => {
            await useAuth().login(async () => await login(data));
        },
        onSuccess: params.onSuccess,
    })
}

export default useLogin;
