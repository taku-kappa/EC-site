import axios from "axios";

import type { ApiResponse } from "../types/ApiResponse";

import type {
    OrderResponse,
    OrderHistoryResponse,
    OrderHistoryDetailResponse
} from "../types/order";

const API_BASE_URL = "http://localhost:8081/api";

const getAuthHeader = () => {

    const token = localStorage.getItem("token");

    return {

        Authorization: `Bearer ${token}`

    };

};

/**
 * 注文確定
 */
export const createOrder = async (): Promise<OrderResponse> => {

    const response =
        await axios.post<ApiResponse<OrderResponse>>(
            `${API_BASE_URL}/orders`,
            {},
            {
                headers: getAuthHeader()
            }
        );

    return response.data.data;

};

/**
 * 注文履歴一覧
 */
export const getOrderHistory =
    async (): Promise<OrderHistoryResponse[]> => {

        const response =
            await axios.get<ApiResponse<OrderHistoryResponse[]>>(
                `${API_BASE_URL}/orders`,
                {
                    headers: getAuthHeader()
                }
            );

        return response.data.data;

    };

/**
 * 注文履歴詳細
 */
export const getOrderDetail = async (
    orderId: number
): Promise<OrderHistoryDetailResponse> => {

    const response =
        await axios.get<ApiResponse<OrderHistoryDetailResponse>>(
            `${API_BASE_URL}/orders/${orderId}`,
            {
                headers: getAuthHeader()
            }
        );

    return response.data.data;

};