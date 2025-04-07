import { API_BASE_URL } from "@/types";
import type { Notification } from "@/types/notification";
import Fetch from "@/util/fetch";
import { useMutation, useQuery } from "@tanstack/vue-query";

export const getNotifications = async (): Promise<Notification[]> => {
    return await Fetch(`${API_BASE_URL}/api/notifications/user`);
}

export const useNotifications = () => {
    return useQuery({
        queryKey: ['notifications', "user"],
        queryFn: async () => {
            return getNotifications();
        }
    });
}

export const markNotificationAsRead = async (notificationId: number): Promise<void> => {
    return await Fetch(`${API_BASE_URL}/api/notifications/${notificationId}/read`, {
        method: "PATCH"
    });
}

export const useReadNotification = () => {
    return useMutation({
        mutationFn: markNotificationAsRead,
    })
}

export const useReadMultipleNotifications = () => {
    return useMutation({
        mutationFn: async (ids: number[]) => {
            const promises = ids.map(id => markNotificationAsRead(id));
            return await Promise.all(promises);
        }
    })
}