import type { Listing } from "./listing";

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

export type CreateListingResponse = {
    listing: Listing;
}

/**
 * Error response returned from the API
 */
export class ApiError extends Error {
    constructor(public message: string) {
        super(message);
    }
}