import type { Listing } from "./listing";

export type Page<T> = {
    listings: T[];
    cursor: number;
}

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
export type GetListingsResponse = {
    listings: Listing[];
}

/**
 * Error response returned from the API
 */
export class ApiError extends Error {
    constructor(public message: string) {
        super(message);
    }
}