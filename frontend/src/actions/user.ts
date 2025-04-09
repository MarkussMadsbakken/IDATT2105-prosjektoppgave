import { API_BASE_URL } from "@/types";
import type { ChangeCredentialsResponse, changeUserCredentialsRequest } from "@/types";
import type { EditUserInfo, GetUserResponse, Image, Listing } from "@/types";
import Fetch, { FetchWithoutParse } from "@/util/fetch";
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

export const useUpdateUser = (params?: { onSuccess?: () => void }) => {
  return useMutation({
    mutationFn: updateUser,
    onSuccess: () => {
      params?.onSuccess?.();
    },
  });
};

export const updateUserCredentials = async (
  req: changeUserCredentialsRequest
): Promise<ChangeCredentialsResponse> => {
  const res = await FetchWithoutParse(`${API_BASE_URL}/api/auth/change-credentials`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(req),
  });

  if (!res.ok) {
    const errorText = await res.text();
    throw new Error(errorText);
  }

  const data: ChangeCredentialsResponse = await res.json();
  console.log(data);
  return data;
};

export const useUpdateUserCredentials = () => {
  return useMutation({
    mutationFn: updateUserCredentials,
  });
}


export const getUser = async (userId: number): Promise<GetUserResponse> => {
  return await Fetch(`${API_BASE_URL}/api/user/${userId}`)
}

export const getUserImage = async (userId: number): Promise<Image> => {
  return await Fetch(`${API_BASE_URL}/api/user/${userId}/image`)
}

export const useUserImage = (userId: number) => {
  return useQuery({
    queryKey: ["user", "image", userId],
    queryFn: () => getUserImage(userId),
    retry: false
  });
};


export const useGetUser = (userId: number) => {
  return useQuery({
    queryKey: ['user', userId],
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

export const getUserByUsername = async (username: string): Promise<GetUserResponse> => {
  return await Fetch(`${API_BASE_URL}/api/user/username/${username}`);
}

export const useGetUserByUsername = (username: string) => {
  return useQuery({
    queryKey: ['user', username],
    queryFn: () => getUserByUsername(username),
  });
}


export const getUsernameIsAvaiable = async (username: string): Promise<boolean> => {
  const res = await FetchWithoutParse(`${API_BASE_URL}/api/user/username/${username}`);

  if (res.status === 404) {
    return true;
  }

  if (!res.ok) {
    const err = await res.json();
    console.error(err);
    throw new Error(err.error);
  }

  return false;
}

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
