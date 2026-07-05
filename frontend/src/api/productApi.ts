import axios from "axios";

import type { Product } from "../types/product";

/**
 * APIベースURL
 */
const API_BASE_URL = "http://localhost:8081/api";

/**
 * JWT取得
 *
 * @returns Authorizationヘッダー
 */
const getAuthHeader = () => {

    const token = localStorage.getItem("token");

    return {
        Authorization: `Bearer ${token}`
    };

};

/**
 * 商品一覧取得
 *
 * @returns 商品一覧
 */
export const getProducts = async (): Promise<Product[]> => {

    const response = await axios.get<Product[]>(
        `${API_BASE_URL}/products`,
        {
            headers: getAuthHeader()
        }
    );

    return response.data;

};

/**
 * 商品詳細取得
 *
 * @param id 商品ID
 *
 * @returns 商品情報
 */
export const getProductById = async (
    id: number
): Promise<Product> => {

    const response = await axios.get<Product>(
        `${API_BASE_URL}/products/${id}`,
        {
            headers: getAuthHeader()
        }
    );

    return response.data;

};