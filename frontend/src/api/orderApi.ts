import apiClient from "./apiClient";

import type { ApiResponse } from "../types/ApiResponse";

import type {
    OrderResponse,
    OrderHistoryResponse,
    OrderHistoryDetailResponse
} from "../types/order";

/**
 * 注文確定
 *
 * @returns 注文結果
 */
export const createOrder = async (): Promise<OrderResponse> => {

    const response =
        await apiClient.post<ApiResponse<OrderResponse>>(
            "/orders",
            {}
        );

    return response.data.data;

};

/**
 * 注文履歴一覧取得
 *
 * @returns 注文履歴一覧
 */
export const getOrderHistory =
    async (): Promise<OrderHistoryResponse[]> => {

        const response =
            await apiClient.get<ApiResponse<OrderHistoryResponse[]>>(
                "/orders"
            );

        return response.data.data;

    };

/**
 * 注文履歴詳細取得
 *
 * @param orderId 注文ID
 * @returns 注文履歴詳細
 */
export const getOrderDetail = async (
    orderId: number
): Promise<OrderHistoryDetailResponse> => {

    const response =
        await apiClient.get<ApiResponse<OrderHistoryDetailResponse>>(
            `/orders/${orderId}`
        );

    return response.data.data;

};