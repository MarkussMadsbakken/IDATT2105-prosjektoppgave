import type { CategoryIcons } from "@/util/categoryIcons";

export type Category = {
    id: number;
    name: string;
    icon: keyof typeof CategoryIcons;
}