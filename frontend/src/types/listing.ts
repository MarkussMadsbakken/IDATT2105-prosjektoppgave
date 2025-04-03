export type Listing = {
    uuid: string,
    name: string;
    description?: string;
    category: string;
    subCategories: string[];
    active: boolean;
    postalCode: number;
    delteted: boolean;
    sold: false;
    ownerId: number,
    price: number;
}
