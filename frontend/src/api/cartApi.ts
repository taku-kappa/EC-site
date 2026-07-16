import axios from "axios";

import type { AddCartRequest } from "../types/cart";

import type { CartResponse } from "../types/cart";

import type { ApiResponse } from "../types/ApiResponse";

/**
 * APIベースURL
 */
const API_BASE_URL = "http://localhost:8081";

/**
 * カート追加
 */
export const addCart = async (

    request: AddCartRequest

): Promise<void> => {

    const token = localStorage.getItem("token");

    await axios.post<ApiResponse<void>>(

        `${API_BASE_URL}/api/cart/items`,

        request,

        {
            headers: {

                Authorization: `Bearer ${token}`

            }

        }

    );

};

/**
 * カート一覧取得
 */
export const getCart = async (): Promise<CartResponse> => {

    const token = localStorage.getItem("token");

    const response = await axios.get<ApiResponse<CartResponse>>(
        `${API_BASE_URL}/api/cart`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data.data;
};

/**
 * 数量変更
 */
export const updateCartQuantity = async (

    cartItemId: number,

    quantity: number

): Promise<void> => {

    const token = localStorage.getItem("token");

    await axios.put<ApiResponse<void>>(

        `${API_BASE_URL}/api/cart/items/${cartItemId}`,

        {
            quantity
        },

        {
            headers: {

                Authorization: `Bearer ${token}`

            }

        }

    );

};

/**
 * カート商品削除
 */
export const deleteCartItem = async (

    cartItemId: number

): Promise<void> => {

    const token = localStorage.getItem("token");

    await axios.delete<ApiResponse<void>>(

        `${API_BASE_URL}/api/cart/items/${cartItemId}`,

        {
            headers: {

                Authorization: `Bearer ${token}`

            }

        }

    );

};