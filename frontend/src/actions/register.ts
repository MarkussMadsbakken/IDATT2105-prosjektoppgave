import { useAuth } from "@/stores/auth";
import { API_BASE_URL, ApiError, type RegisterRequest, type RegisterResponse } from "@/types";
import { useMutation } from "@tanstack/vue-query";

// IMPORTANT: Both login and register actions do not use the extended "Fetch" function
// defned in /util/fetch.ts. This is because we do not yet have a token to send with the request.


/**
 * Sends a register request to the api
 */
export const register = async (req: RegisterRequest): Promise<RegisterResponse> => {
    let res = await fetch(`${API_BASE_URL}/api/auth/register`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(req),
    });

    if (!res.ok) {
        const err = await res.json();
        throw new ApiError(err.error);
    }

    return res.json();
}

/**
 * Hook for registering in via a mutation
 */
const useRegister = (params?: { onSuccess: () => void }) => {
    return useMutation({
        mutationFn: async (data: RegisterRequest) => {
            return useAuth().register(async () => register(data));
        },
        onSuccess: params?.onSuccess,
    })
}

export default useRegister;