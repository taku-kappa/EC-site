import apiClient from "./apiClient";

import type {
    AddCartRequest,
    CartResponse
} from "../types/cart";

import type { ApiResponse } from "../types/ApiResponse";

/**
 * カート追加
 *
 * @param request カート追加情報
 */
export const addCart = async (
    request: AddCartRequest
): Promise<void> => {

    await apiClient.post<ApiResponse<void>>(
        "/cart/items",
        request
    );

};

/**
 * カート一覧取得
 *
 * @returns カート情報
 */
export const getCart = async (): Promise<CartResponse> => {

    const response =
        await apiClient.get<ApiResponse<CartResponse>>(
            "/cart"
        );

    return response.data.data;

};

/**
 * カート商品の数量変更
 *
 * @param cartItemId カート商品ID
 * @param quantity 数量
 */
export const updateCartQuantity = async (
    cartItemId: number,
    quantity: number
): Promise<void> => {

    await apiClient.put<ApiResponse<void>>(
        `/cart/items/${cartItemId}`,
        {
            quantity
        }
    );

};

/**
 * カート商品削除
 *
 * @param cartItemId カート商品ID
 */
export const deleteCartItem = async (
    cartItemId: number
): Promise<void> => {

    await apiClient.delete<ApiResponse<void>>(
        `/cart/items/${cartItemId}`
    );

};