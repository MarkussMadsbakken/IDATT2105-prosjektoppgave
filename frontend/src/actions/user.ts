import { API_BASE_URL } from "@/types";
import type { EditUserInfo, GetUserResponse } from "@/types";
import Fetch from "@/util/fetch";
import { useInfiniteQuery, useMutation, useQuery } from "@tanstack/vue-query";
import { objectOmit } from "@vueuse/core";
import { useAuth } from "@/stores/auth.ts";

export const updateUser = async (req: EditUserInfo & { profileImage?: File }): Promise<any> => {

    const body = new FormData();

    if (req.profileImage) {
        body.append("userImage", req.profileImage)
    }

    const userInfoBlob = new Blob(
        [JSON.stringify(objectOmit(req, ["profileImage"]))],
        { type: "application/json" }
    );
    body.append("userUpdate", userInfoBlob)

    console.log("Sender data med blob: "+ body.get("userUpdate"))

    const res = await Fetch(`${API_BASE_URL}/api/user/update`, {
        method: "PUT",
        body: body,
    });
    console.log("Response fra Fetch: ",res);

  if (!res.ok) {
    const text = await res.text();
    console.error("Feil fra server:", res.status, text);
    throw new Error("Feil ved oppdatering av bruker");
  }
  try {
    const json = await res.json();
    console.log("JSON-respons:", json);
    return json;
  } catch {
    console.warn("Kunne ikke parse JSON – returnerer raw response");
    return true;
  }
};

const useUpdateUser = (params?: { onSuccess?: () => void }) => {
  return useMutation({
    mutationFn: updateUser,
    onSuccess: (...args) => {
      console.log("onSuccess i useUpdateUser!", args);
      params?.onSuccess?.();
    },
    onError(error, variables, context) {
      console.log(error);
},
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
