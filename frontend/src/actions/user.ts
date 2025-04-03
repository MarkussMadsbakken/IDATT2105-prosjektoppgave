import { API_BASE_URL } from "@/types";
import type { EditUserInfo, GetUserResponse } from "@/types";
import Fetch from "@/util/fetch";
import { useInfiniteQuery, useMutation, useQuery } from "@tanstack/vue-query";
import { objectOmit } from "@vueuse/core";
import { useAuth } from "@/stores/auth.ts";

export const updateUser = async (req: EditUserInfo & { profileImage?: File }): Promise<void> => {

    const body = new FormData();

    if (req.profileImage) {
        body.append("userImage", req.profileImage)
    }

    const userInfoBlob = new Blob(
        [JSON.stringify(objectOmit(req, ["profileImage"]))],
        { type: "application/json" }
    );
    body.append("userUpdate", userInfoBlob)

    console.log(body.get("EditUserInfo"))

    return await Fetch(`${API_BASE_URL}/api/user/update`, {
        method: "POST",
        body: body,
    });
};

const useUpdateUser = (params?: { onSuccess?: () => void }) => {
    return useMutation({
        mutationFn: updateUser,
        onSuccess: params?.onSuccess,
    });
};

export const getUser = async (userId: number): Promise<GetUserResponse> => {
    return await Fetch(`${API_BASE_URL}/api/user/${userId}`)
}

export const useGetUser = (userId: number) => {
    return useQuery({
        queryKey: ['profile', userId],
        queryFn: async () => {
            return getUser(userId);
        }
    })
}
export default useUpdateUser;
