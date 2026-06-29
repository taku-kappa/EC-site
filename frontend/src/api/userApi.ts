import axios from "axios";
import type { LoginRequest, LoginResponse } from "../types/auth";

/**
 * APIベースURL
 */
const API_BASE_URL = "http://localhost:8081";

/**
 * ログインAPI
 *
 * @param request ログイン情報
 * @returns JWTトークン
 */
export const login = async (
    request: LoginRequest
): Promise<LoginResponse> => {

    // axios.post<型>（ジェネリクスをしてすることでpostのレスポンスの型を指定できる）
    const response = await axios.post<LoginResponse>(
        `${API_BASE_URL}/api/auth/login`,
        request
    );

    return response.data;
};