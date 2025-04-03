import { API_BASE_URL, type Chat, type Message } from "@/types"
import Fetch from "@/util/fetch"
import { useQuery } from "@tanstack/vue-query";

export const getAllMessages = async (): Promise<Message> => {
    return await Fetch(`${API_BASE_URL}/api/messages/all`);
}

export const getChats = async (): Promise<Chat[]> => {
    let messages = await getAllMessages();
    console.log(messages);
    return [];
}

export const useChats = () => {
    return useQuery({
        queryKey: ['messages'],
        queryFn: getChats
    })
}

export const getListingMessages = async (listingId: number): Promise<Message[]> => {
    return await Fetch(`${API_BASE_URL}/api/messages/${listingId}`);

}