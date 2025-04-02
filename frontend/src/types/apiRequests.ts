import type { Category } from "./category";

/**
 * Type for sending a login request to the server
 */
export type LoginRequest = {
    username: string;
    password: string;
}

/**
 * Type for sending a register request to the server
 */
export type RegisterRequest = {
    username: string;
    password: string;
}

/**
 * Type for creating a listing
 */
export type CreateListingRequest = {
    name: string,
    price: number,
    description: string,
    category: string,
    postalCode: number,
    images: File[];
}

export type GetListingsRequest = {
    page: number;
    offset: number;
}

export type CreateCategoryRequest = Omit<Category, 'id'>;