export type User = {
    id: number;
    username: string;
    firstName?: string;
    lastName?: string;
    createdAt: number;
    isAdmin: boolean;
    imageUrl?: string;
}
