import type { Listing } from "./listing";
import type { User } from "./user";

export type Message = {
    id: number;
    listingId: number;
    buyerId: number;
    sellerId: number;
    message: string;
    createdAt: string;
    sentByBuyer: boolean;
}


export type Chat = {
    id: number;
    listing: Listing;
    buyer: User;
    seller: User;
    messages: Message[];
    yourRole: "buyer" | "seller";
}
