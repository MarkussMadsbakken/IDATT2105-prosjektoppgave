import type { User } from "./user";

export type Listing = {
    id: string,
    title: string;
    description?: string;
    seller: User;
    price: number;
    image?: string | string[];
}
