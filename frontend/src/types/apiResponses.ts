import type { Category, SubCategory } from "./category";
import type { Listing } from "./listing";
import type { Chat, Message } from "./message";
import type { User } from "./user.ts";

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

export const EMPTY_PAGE: Page<any> = {
    content: [],
    empty: true,
    first: true,
    last: true,
    number: 0,
    numberOfElements: 0,
    size: 0,
    totalElements: 0,
    totalPages: 0,
    pageable: {
        offset: 0,
        pageNumber: 0,
        pageSize: 0,
        paged: false
    }
}

export const PAGE_SIZE = 6;

export type GetChatsResponse = Chat[];

export type CreateChatReponse = DefaultResponse & {
    chatId: number;
};

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
export type CreateListingResponse = Listing;


/**
 * Response from the API when getting listings
 */
export type GetListingsResponse = Page<Listing>;

export type GetCategoriesResponse = Category[];
export type getSubCategoriesResponse = SubCategory[];
export type CreateCategoryResponse = Category;

export type GetUserResponse = User;

export type GetListingBookmarksResponse = {
    bookMarkCount: number;
}

/**
 * Default response returned from the API
 */
export type DefaultResponse = {
    message: string;
    shortMessage: string;
};

export type ReservationResponse = {
    id: number;
    listingID: string,
    userId: number,
    createdAt: string
};

export type ChangeCredentialsResponse = {
    message: string;
    token: string;
}

/**
 * Error response returned from the API
 */
export class ApiError extends Error {
    constructor(public message: string) {
        super(message);
    }
}
