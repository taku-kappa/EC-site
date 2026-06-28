import axios from "axios";
import type { Product } from "../types/product";

/**
 * APIベースURL
 */
const API_BASE_URL = "http://localhost:8081/api";

/**
 * 商品一覧取得
 */
export const getProducts = async (): Promise<Product[]> => {

    const token = localStorage.getItem("token");

    const response = await axios.get(
        `${API_BASE_URL}/products`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    // バックエンドのレスポンスで"data"配列の中に
    // 商品情報を格納して返しているためresponse.data.dataになっている。
    return response.data.data;

};