import { API_BASE_URL } from "@/types";
import type { ChangeCredentialsResponse, changeUserCredentialsRequest } from "@/types";
import type { EditUserInfo, GetUserResponse, Image, Listing } from "@/types";
import Fetch, { FetchWithoutParse } from "@/util/fetch";
import { useInfiniteQuery, useMutation, useQuery } from "@tanstack/vue-query";
import { objectOmit } from "@vueuse/core";
import { useAuth } from "@/stores/auth.ts";

/**
 * Updates the current user with the given params
 * @param req The request object containing the params for the user
 * @returns A response object containing a message
 * @throws if the request fails and the user could not be updated
 */
export const updateUser = async (req: EditUserInfo & { profileImage?: File }) => {
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
};

/**
 * Hook to update the current user
 * @param params Optional params for the mutation
 * @returns A mutation object containing the update function and other properties
 * @throws if the request fails and the user could not be updated
 */
export const useUpdateUser = (params?: { onSuccess?: () => void }) => {
  return useMutation({
    mutationFn: updateUser,
    onSuccess: () => {
      params?.onSuccess?.();
    },
  });
};

/**
 * Updates the user credentials with the given params
 * @param req The request object containing the params for the user credentials
 * @returns A response object containing the new JWT token
 * @throws if the request fails and the user credentials could not be updated
 */
export const updateUserCredentials = async (req: changeUserCredentialsRequest): Promise<ChangeCredentialsResponse> => {
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
  return data;
};

/**
 * Hook to update the user credentials
 * @returns A mutation object containing the update function and other properties
 */
export const useUpdateUserCredentials = () => {
  return useMutation({
    mutationFn: updateUserCredentials,
  });
}

/**
 * Gets a user by id
 * @param userId The id of the user to get
 * @returns The user object
 * @throws if the request fails and the user could not be retrieved
 */
export const getUser = async (userId: number): Promise<GetUserResponse> => {
  return await Fetch(`${API_BASE_URL}/api/user/${userId}`)
}

/**
 * 
 * @param userId The id of the user to get the image for
 * @returns The image object
 * @throws if the request fails and the image could not be retrieved, or if the image does not exist
 */
export const getUserImage = async (userId: number): Promise<Image> => {
  return await Fetch(`${API_BASE_URL}/api/user/${userId}/image`)
}

/**
 * Hook to get the user image for a given userId
 * @param userId The id of the user to get the image for
 * @returns A query object containing the image
 */
export const useUserImage = (userId: number) => {
  return useQuery({
    queryKey: ["user", "image", userId],
    queryFn: () => getUserImage(userId),
    retry: false
  });
};


/**
 * Hook to get a user by id
 * @param userId The id of the user to get
 * @return A query object containing the user
 */
export const useGetUser = (userId: number) => {
  return useQuery({
    queryKey: ['user', userId],
    queryFn: async () => {
      return getUser(userId);
    }
  })
}

/**
 * Gets all active listings for a given user
 * @param userId The id of the user to get listings for
 * @returns A list of listings
 * @throws if the request fails and the listings could not be retrieved
 */
export const getUserListings = async (userId: number): Promise<Listing[]> => {
  const res = await Fetch(`${API_BASE_URL}/api/listing/user/${userId}?page=0&offset=100`);
  return res.content;
}

/**
 * Hook to get all active listings for a given user
 * @param userId The id of the user to get listings for
 * @returns A query object containing the listings
 */
export const useGetUserListings = (userId: number) => {
  return useQuery({
    queryKey: ['userListings', userId],
    queryFn: () => getUserListings(userId),
  });
};

/**
 * Gets a user by username
 * @param username The username of the user to get
 * @returns The user object
 * @throws if the request fails and the user could not be retrieved, or if the user does not exist
 */
export const getUserByUsername = async (username: string): Promise<GetUserResponse> => {
  return await Fetch(`${API_BASE_URL}/api/user/username/${username}`);
}

/**
 * Hook to get a user by username
 * @param username The username of the user to get
 * @returns A query object containing the user
 */
export const useGetUserByUsername = (username: string) => {
  return useQuery({
    queryKey: ['user', username],
    queryFn: () => getUserByUsername(username),
  });
}

/**
 * Checks if a username is available
 * @param usernamem The username to check
 * @returns True if the username is available, false otherwise
 */
export const getUsernameIsAvaiable = async (username: string): Promise<boolean> => {
  const res = await FetchWithoutParse(`${API_BASE_URL}/api/user/username/${username}`);

  if (res.status === 404) {
    return true;
  }

  if (!res.ok) {
    const err = await res.json();
    throw new Error(err.error);
  }

  return false;
}

/**
 * Gets the bookmarks for the current user
 * @returns A list of bookmarks
 * @throws if the request fails and the bookmarks could not be retrieved
 */
export const getUserBookmarks = async (): Promise<Listing[]> => {
  const bookmarks = await Fetch(`${API_BASE_URL}/api/bookmark/user`);
  if (!bookmarks) {
    return [];
  }
  return bookmarks;
}

/**
 * Hook to get the bookmarks for the current user
 * @returns A query object containing the bookmarks
 */
export const useGetUserBookmarks = () => {
  return useQuery({
    queryKey: ['userBookmarks'],
    queryFn: getUserBookmarks,
  });
};

export default useUpdateUser;
