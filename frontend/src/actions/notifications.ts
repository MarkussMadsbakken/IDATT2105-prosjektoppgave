import { API_BASE_URL } from "@/types";
import type { Notification } from "@/types/notification";
import Fetch from "@/util/fetch";
import { useMutation, useQuery } from "@tanstack/vue-query";

/**
 * Gets all notifications for the current user
 * @returns A list of notifications
 * @throws if the request fails and the notifications could not be retrieved
 */
export const getNotifications = async (): Promise<Notification[]> => {
    return await Fetch(`${API_BASE_URL}/api/notifications/user`);
}

/**
 * Hook to get all notifications for the current user
 * @returns A query object containing the notifications
 */
export const useNotifications = () => {
    return useQuery({
        queryKey: ['notifications', "user"],
        queryFn: async () => {
            return getNotifications();
        }
    });
}

/**
 * Marks a notification as read
 * @param notificationId The id of the notification to mark as read
 * @throws if the request fails and the notification could not be marked as read
 */
export const markNotificationAsRead = async (notificationId: number): Promise<void> => {
    return await Fetch(`${API_BASE_URL}/api/notifications/${notificationId}/read`, {
        method: "PATCH"
    });
}

/**
 * Hook to mark a notification as read
 * @returns A mutation object containing the mutation to mark a notification as read
 */
export const useReadNotification = () => {
    return useMutation({
        mutationFn: markNotificationAsRead,
    })
}

/**
 * Marks multiple notifications as read
 * @returns A mutation object containing the mutation to mark multiple notifications as read
 */
export const useReadMultipleNotifications = () => {
    return useMutation({
        mutationFn: async (ids: number[]) => {
            const promises = ids.map(id => markNotificationAsRead(id));
            return await Promise.all(promises);
        }
    })
}