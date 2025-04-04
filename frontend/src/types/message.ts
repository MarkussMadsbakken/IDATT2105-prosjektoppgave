import type { Listing } from "./listing";
import type { User } from "./user";

export type Message = {
    id: number;
    chatId: number;
    senderId: number;
    message: string;
    createdAt: string;
}

export type ChatWithUser = {
    id: number;
    buyer: User;
    seller: User;
    listing: Listing;
}

export type Chat = {
    chatId: number;
    buyerId: number;
    sellerId: number;
    listingId: string;
}
