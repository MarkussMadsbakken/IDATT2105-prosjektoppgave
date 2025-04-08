export type Listing = {
    uuid: string,
    name: string;
    description?: string;
    category: number;
    subcategory?: number;
    active: boolean;
    longitude: number;
    latitude: number;
    deleted: boolean;
    sold: false;
    ownerId: number,
    price: number;
}
