import type { Category } from "./category";
import type { Listing } from "./listing";

export type Page<T> = {
    content: T[];
    empty: boolean;
    first: boolean;
    last: boolean;
    number: number;
    numberOfElements: number;
    size: number;
    totalElements: number;
    totalPages: number;
    pageable: {
        offset: number;
        pageNumber: number;
        pageSize: number;
        paged: boolean;
    }
}

export const PAGE_SIZE = 12;

/**
 * Login response returned from the API
 */
export type LoginResponse = {
    token: string;
    message: string;
}

/**
 * Register response returned from the API
 */
export type RegisterResponse = {
    token: string;
    message: string;
}

/**
 * Response from the API when creating a listing
 */
export type CreateListingResponse = {
    listing: Listing;
}


/**
 * Response from the API when getting listings
 */
export type GetListingsResponse = Page<Listing>;

export type GetCategoriesResponse = Category[];

export type CreateCategoryResponse = Category;

/**
 * Error response returned from the API
 */
export class ApiError extends Error {
    constructor(public message: string) {
        super(message);
    }
}