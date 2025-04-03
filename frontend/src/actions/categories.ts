import { API_BASE_URL, type Category, type CreateCategoryRequest, type CreateCategoryResponse, type CreateSubCategoryRequest, type DefaultResponse, type GetCategoriesResponse } from "@/types"
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
        body: JSON.stringify(req),
        headers: {
            'Content-Type': 'application/json'
        }
    })
}

export const deleteCategory = async (id: number) => {
    return await Fetch(`${API_BASE_URL}/api/categories/${id}`, {
        method: 'DELETE'
    });
}

export const editCategory = async (req: Category): Promise<DefaultResponse> => {
    return await Fetch(`${API_BASE_URL}/api/categories`, {
        method: 'PUT',
        body: JSON.stringify(req),
        headers: {
            'Content-Type': 'application/json'
        }
    });
}

export const getSubCategories = async (id: number): Promise<GetCategoriesResponse> => {
    return await Fetch(`${API_BASE_URL}/api/categories/${id}/subcategories`);
}

export const useSubCategories = (id: number) => {
    return useQuery({
        queryKey: ["categories", id, "subcategories"],
        queryFn: () => getSubCategories(id),
    })
}

export const createSubCategory = async (req: CreateSubCategoryRequest): Promise<DefaultResponse> => {
    return await Fetch(`${API_BASE_URL}/api/categories/subcategories`, {
        method: 'POST',
        body: JSON.stringify(req),
        headers: {
            'Content-Type': 'application/json'
        }
    });
}

export const deleteSubCategory = async (id: number): Promise<DefaultResponse> => {
    return await Fetch(`${API_BASE_URL}/api/categories/subcategories/${id}`, {
        method: 'DELETE'
    });
}