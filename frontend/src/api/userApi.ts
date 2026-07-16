import axios from "axios";

import type {
    LoginRequest,
    LoginResponse,
} from "../types/auth";

import type { ApiResponse } from "../types/ApiResponse";

/**
 * APIベースURL
 */
const API_BASE_URL = "http://localhost:8081";

/**
 * ログインAPI
 *
 * @param request ログイン情報
 * @returns ログイン情報
 */
export const login = async (
    request: LoginRequest
): Promise<LoginResponse> => {

    const response = await axios.post<ApiResponse<LoginResponse>>(
        `${API_BASE_URL}/api/auth/login`,
        request
    );

    return response.data.data;

};