import type { Listing } from "./listing";
import type { User } from "./user";

export type Message = {
    id: number;
    content: string;
    sender: "buyer" | "seller";
    createdAt: Date;
}

export type Chat = {
    id: number;
    listing: Listing;
    buyer: User;
    seller: User;
    messages: Message[];
    yourRole: "buyer" | "seller";
}
