import type { Category, SubCategory } from "./category";

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
    category: number,
    postalCode: number,
    images: File[];
}

export type GetListingsRequest = {
    page: number;
    offset: number;
}
export type EditUserInfo = {
    username: string;
    firstName: string;
    lastName: string;
    imageURL?: string;
}

export type CreateCategoryRequest = Omit<Category, 'id'>;

export type EditCategoryRequest = Category;

export type CreateSubCategoryRequest = Omit<SubCategory, 'id'>;
