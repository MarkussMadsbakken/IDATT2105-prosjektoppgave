export type User = {
    id: number;
    username: string;
    firstName: string;
    lastName: string;
    createdAt: Date;
    isAdmin: boolean;
    profileImage?: string;
}
