import { API_BASE_URL, PAGE_SIZE, type Chat, type CreateChatReponse, type GetChatsResponse, type Message, type Page, type SendMessageRequest } from "@/types"
import Fetch from "@/util/fetch"
import { useInfiniteQuery, useQuery } from "@tanstack/vue-query";

export const getChats = async (): Promise<GetChatsResponse> => {
    return await Fetch(`${API_BASE_URL}/api/chat`)
}


export const useChats = () => {
    return useQuery({
        queryKey: ['chats'],
        queryFn: getChats,
    })
}

export const createChat = async (listingId: string): Promise<CreateChatReponse> => {
    return await Fetch(`${API_BASE_URL}/api/chat`, {
        method: 'POST',
        body: JSON.stringify({
            listingId: listingId
        }),
        headers: {
            'Content-Type': 'application/json'
        }
    });
}

export const getChatMessages = async (chatId: number): Promise<Message[]> => {
    return await Fetch(`${API_BASE_URL}/api/chat/${chatId}/messages`);
}

export const getChat = async (chatId: number): Promise<Chat> => {
    return await Fetch(`${API_BASE_URL}/api/chat/${chatId}`);
}

export const useChat = (chatId: number) => {
    return useQuery({
        queryKey: ['chat', chatId],
        queryFn: () => getChat(chatId),
    });
}

export const useChatMessages = (chatId: number) => {
    return useQuery({
        queryKey: ['chat', chatId, 'messages'],
        queryFn: () => getChatMessages(chatId),
    })
}

export const sendMessage = async (req: SendMessageRequest): Promise<Message> => {
    return await Fetch(`${API_BASE_URL}/api/chat/${req.chatId}/messages`, {
        method: 'POST',
        body: JSON.stringify({
            message: req.message
        }),
        headers: {
            'Content-Type': 'application/json'
        }
    });
}

export const getLatestMessage = async (chatId: number): Promise<Message> => {
    return await Fetch(`${API_BASE_URL}/api/chat/${chatId}/messages/latest`);
}

export const useLatestMessage = (chatId: number) => {
    return useQuery({
        queryKey: ['chat', chatId, 'messages', 'latest'],
        queryFn: () => getLatestMessage(chatId),
    })
}