import { API_BASE_URL, type Category, type CreateCategoryRequest, type CreateCategoryResponse, type CreateSubCategoryRequest, type DefaultResponse, type GetCategoriesResponse, type getSubCategoriesResponse } from "@/types"
import Fetch from "@/util/fetch"
import { useMutation, useQuery } from "@tanstack/vue-query";

/**
 * Gets all categories
 * @returns A list of categories
 */
export const getCategories = async (): Promise<GetCategoriesResponse> => {
    return await Fetch(`${API_BASE_URL}/api/categories`);
}

/**
 * Hook to get all categories
 * @returns A query object containing the categories
 */
export const useCategories = () => {
    return useQuery({
        queryKey: ['categories'],
        queryFn: getCategories
    })
}

/**
 * Creates a new category with the given params
 * @param req The request object containing the params for the new category
 * @returns The created category
 */
export const createCategory = async (req: CreateCategoryRequest): Promise<CreateCategoryResponse> => {
    return await Fetch(`${API_BASE_URL}/api/categories`, {
        method: 'POST',
        body: JSON.stringify(req),
        headers: {
            'Content-Type': 'application/json'
        }
    })
}

/**
 * Deletes the category with the given id
 * @param id The id of the category to delete
 * @throws if the request fails and the category could not be deleted
 */
export const deleteCategory = async (id: number) => {
    return await Fetch(`${API_BASE_URL}/api/categories/${id}`, {
        method: 'DELETE'
    });
}

/**
 * Edits a category with the given params
 * @param req  The request object containing the params for the category
 * @returns A response object containing a message
 */
export const editCategory = async (req: Category): Promise<DefaultResponse> => {
    return await Fetch(`${API_BASE_URL}/api/categories`, {
        method: 'PUT',
        body: JSON.stringify(req),
        headers: {
            'Content-Type': 'application/json'
        }
    });
}

/**
 * Gets all subcategories for a given category
 * @param id The id of the category to get subcategories for
 * @returns  A list of subcategories
 * @throws if the request fails and the subcategories could not be retrieved
 */
export const getSubCategories = async (id: number): Promise<getSubCategoriesResponse> => {
    return await Fetch(`${API_BASE_URL}/api/categories/${id}/subcategories`);
}

/**
 * Hook to get all subcategories for a given category
 * @param id The id of the category to get subcategories for
 * @returns A query object containing the subcategories
 */
export const useSubCategories = (id: number) => {
    return useQuery({
        queryKey: ["categories", id, "subcategories"],
        queryFn: () => getSubCategories(id),
    })
}

/**
 * Creates a new subcategory with the given params
 * @param req The request object containing the params for the new subcategory
 * @returns A response object containing a message with the server response
 * @throws if the request fails and the subcategory could not be created
 */
export const createSubCategory = async (req: CreateSubCategoryRequest): Promise<DefaultResponse> => {
    return await Fetch(`${API_BASE_URL}/api/categories/subcategories`, {
        method: 'POST',
        body: JSON.stringify(req),
        headers: {
            'Content-Type': 'application/json'
        }
    });
}

/**
 * Deletes the subcategory with the given id
 * @param id The id of the subcategory to delete
 * @returns A response object containing a message with the server response
 * @throws if the request fails and the subcategory could not be deleted
 */
export const deleteSubCategory = async (id: number): Promise<DefaultResponse> => {
    return await Fetch(`${API_BASE_URL}/api/categories/subcategories/${id}`, {
        method: 'DELETE'
    });
}