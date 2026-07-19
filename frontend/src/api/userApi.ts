import apiClient from "./apiClient";

import type {
    LoginRequest,
    LoginResponse,
} from "../types/auth";

import type { ApiResponse } from "../types/ApiResponse";

/**
 * ログインAPI
 *
 * @param request ログイン情報
 * @returns ログイン情報
 */
export const login = async (
    request: LoginRequest
): Promise<LoginResponse> => {

    const response =
        await apiClient.post<ApiResponse<LoginResponse>>(
            "/auth/login",
            request
        );

    return response.data.data;

};