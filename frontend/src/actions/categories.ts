import { API_BASE_URL, type CreateCategoryRequest, type CreateCategoryResponse, type GetCategoriesResponse } from "@/types"
import Fetch from "@/util/fetch"
import { useMutation, useQuery } from "@tanstack/vue-query";

export const getCategories = async (): Promise<GetCategoriesResponse> => {
    return await Fetch(`${API_BASE_URL}/api/categories`);
}

export const useCategories = () => {
    return useQuery({
        queryKey: ['categories'],
        queryFn: getCategories
    })
}

export const createCategory = async (req: CreateCategoryRequest): Promise<CreateCategoryResponse> => {
    return await Fetch(`${API_BASE_URL}/api/categories`, {
        method: 'POST',
        body: JSON.stringify(req)
    })
}

export const useCreateCategory = (params?: { onSuccess?: () => void, }) => {
    return useMutation({
        mutationFn: createCategory,
        onSuccess: params?.onSuccess
    })
}