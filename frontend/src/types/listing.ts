export type Listing = {
    uuid: string,
    name: string;
    description?: string;
    category: number;
    subcategory?: number;
    active: boolean;
    postalCode: number;
    deleted: boolean;
    sold: false;
    ownerId: number,
    price: number;
}
