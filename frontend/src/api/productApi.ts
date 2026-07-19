import apiClient from "./apiClient";
import type { Product } from "../types/product";

/**
 * 商品一覧取得
 *
 * @returns 商品一覧
 */
export const getProducts = async (): Promise<Product[]> => {

    const response =
        await apiClient.get<Product[]>("/products");

    return response.data;

};

/**
 * 商品詳細取得
 *
 * @param id 商品ID
 * @returns 商品情報
 */
export const getProductById = async (
    id: number
): Promise<Product> => {

    const response =
        await apiClient.get<Product>(
            `/products/${id}`
        );

    return response.data;

};