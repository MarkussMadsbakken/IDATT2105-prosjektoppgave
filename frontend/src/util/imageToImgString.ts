import type { Image } from "@/types";

export const toImgString = (image: Image): string => {
    return `data:${image.fileType};base64,${image.base64Image}`;
}