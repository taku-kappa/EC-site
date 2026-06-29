import axios from "axios";
import type { Product } from "../types/product";

/**
 * APIベースURL
 */
const API_BASE_URL = "http://localhost:8081/api";

/**
 * 商品一覧取得
 *
 * @returns 商品一覧
 */
export const getProducts = async (): Promise<Product[]> => {

    /**
     * JWT取得
     */
    const token = localStorage.getItem("token");

    /**
     * 商品一覧取得API呼び出し
     */
    const response = await axios.get<Product[]>(
        `${API_BASE_URL}/products`,
        {
            // HTTPリクエストのヘッダーにJWTを設定
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    /**
     * 商品一覧を返却
     */
    return response.data;

};