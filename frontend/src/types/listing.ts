export type Listing = {
    uuid: string,
    name: string;
    description?: string;
    category: number;
    subCategories: string[];
    active: boolean;
    postalCode: number;
    deleted: boolean;
    sold: false;
    ownerId: number,
    price: number;
}
