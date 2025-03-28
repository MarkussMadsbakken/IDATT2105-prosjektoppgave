import type { User } from "./user";

export type Listing = {
    id: string,
    title: string;
    description?: string;
    user: User;
    price: number;
    image?: string | string[];
}