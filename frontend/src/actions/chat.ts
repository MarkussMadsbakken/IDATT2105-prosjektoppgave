import { API_BASE_URL, PAGE_SIZE, type Chat, type CreateChatReponse, type GetChatsResponse, type Message, type Page, type SendMessageRequest } from "@/types"
import Fetch from "@/util/fetch"
import { useInfiniteQuery, useQuery } from "@tanstack/vue-query";
import type { Ref } from "vue";

/**
 * Get all chats for the current user
 * @returns A list of chats
 */
export const getChats = async (): Promise<GetChatsResponse> => {
    return await Fetch(`${API_BASE_URL}/api/chat`)
}

/**
 * Hook to get all chats for the current user
 * @returns A query object containing the chats
 */
export const useChats = () => {
    return useQuery({
        queryKey: ['chats'],
        queryFn: getChats,
    })
}

/**
 * Creates a new chat for the given listing
 * @param listingId The id of the listing to create a chat for
 * @returns The id of the created chat
 */
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

/**
 * Gets all chat messages for the given chatId
 * @param chatId The id of the chat to get messages for
 * @returns A list of messages
 * @throws if the request fails and the messages could not be retrieved, 
 * if the chatId is invalid or the user does not have permission to view such messages
 */
export const getChatMessages = async (chatId: number): Promise<Message[]> => {
    return await Fetch(`${API_BASE_URL}/api/chat/${chatId}/messages`);
}

/**
 * Gets a single chat for the given chatId
 * @param chatId  The id of the chat to get
 * @returns  The chat object
 */
export const getChat = async (chatId: number): Promise<Chat> => {
    return await Fetch(`${API_BASE_URL}/api/chat/${chatId}`);
}

/**
 * Hook to get a single chat for the given chatId
 * @param chatId The id of the chat to get
 * @returns A query object containing the chat
 */
export const useChat = (chatId: Ref<number>) => {
    return useQuery({
        queryKey: ['chat', chatId],
        queryFn: () => getChat(chatId.value),
    });
}

/**
 * Hook to get all chat messages for the given chatId
 * @param chatId The id of the chat to get messages for
 * @returns A query object containing the messages
 */
export const useChatMessages = (chatId: Ref<number>) => {
    return useQuery({
        queryKey: ['chat', chatId, 'messages'],
        queryFn: () => getChatMessages(chatId.value),
    })
}

/**
 * Sends a message to the given chatId
 * @param req The request object containing the params for the message
 * @returns The message object
 * @throws if the request fails and the message could not be sent
 */
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

/**
 * Gets the latest message for a given chat
 * @param chatId The id of the chat to get the latest message for
 * @returns The latest message, if any
 * @throws if the request fails and the message could not be retrieved
 */
export const getLatestMessage = async (chatId: number): Promise<Message> => {
    return await Fetch(`${API_BASE_URL}/api/chat/${chatId}/messages/latest`);
}

/**
 * Hook to get the latest message for a given chat
 * @param chatId The id of the chat to get the latest message for
 * @returns A query object containing the latest message
 */
export const useLatestMessage = (chatId: number) => {
    return useQuery({
        queryKey: ['chat', chatId, 'messages', 'latest'],
        queryFn: () => getLatestMessage(chatId),
        retry: false,
    })
}