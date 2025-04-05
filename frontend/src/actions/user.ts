import { API_BASE_URL } from "@/types";
import type { EditUserInfo, GetUserResponse, Image, Listing } from "@/types";
import Fetch from "@/util/fetch";
import { useInfiniteQuery, useMutation, useQuery } from "@tanstack/vue-query";
import { objectOmit } from "@vueuse/core";
import { useAuth } from "@/stores/auth.ts";

export const updateUser = async (req: EditUserInfo & { profileImage?: File }) => {
  try {
    const body = new FormData();

    if (req.profileImage) {
      body.append("userImage", req.profileImage);
    }

    const userInfoBlob = new Blob(
      [JSON.stringify(objectOmit(req, ["profileImage"]))],
      { type: "application/json" }
    );
    body.append("userUpdate", userInfoBlob);


    const res = await Fetch(`${API_BASE_URL}/api/user/update`, {
      method: "PUT",
      body: body,
    });

    return res;
  } catch (err) {
    console.error("Feil i updateUser:", err);
    throw err;
  }
};

const useUpdateUser = (params?: { onSuccess?: () => void }) => {
  return useMutation({
    mutationFn: updateUser,
    onSuccess: () => {
      params?.onSuccess?.();
    },
  });
};

export const getUser = async (userId: number): Promise<GetUserResponse> => {
  return await Fetch(`${API_BASE_URL}/api/user/${userId}`)
}

export const getUserImage = async (userId: number): Promise<Image> => {
  return await Fetch(`${API_BASE_URL}/api/user/${userId}/image`)
}

export const useUserImage = (userId: number) => {
  return useQuery({
    queryKey: ["user", userId, "image"],
    queryFn: () => getUserImage(userId),
    retry: false
  });
};


export const useGetUser = (userId: number) => {
  return useQuery({
    queryKey: ['profile', userId],
    queryFn: async () => {
      return getUser(userId);
    }
  })
}
export const getUserListings = async (userId: number): Promise<Listing[]> => {
  const res = await Fetch(`${API_BASE_URL}/api/listing/user/${userId}?page=0&offset=100`);
  return res.content;
}

export const useGetUserListings = (userId: number) => {
  return useQuery({
    queryKey: ['userListings', userId],
    queryFn: () => getUserListings(userId),
  });
};

export const getUserBookmarks = async (): Promise<Listing[]> => {
  return await Fetch(`${API_BASE_URL}/api/bookmark/user`);
}
export const useGetUserBookmarks = () => {
  return useQuery({
    queryKey: ['userBookmarks'],
    queryFn: getUserBookmarks,
  });
};
export default useUpdateUser;
