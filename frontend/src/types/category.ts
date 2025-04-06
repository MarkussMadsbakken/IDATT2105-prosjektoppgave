import type { CategoryIcons } from "@/util/categoryIcons";

export type Category = {
    id: number;
    name: string;
    icon: keyof typeof CategoryIcons;
    description: string;
}

export type SubCategory = {
    id: number;
    name: string;
    description: string;
    parentId: number;
}