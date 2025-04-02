import type { User } from "./user";

export type Listing = {
    uuid: string,
    title: string;
    description?: string;
    seller: User;
    price: number;
    image?: string | string[];
}
