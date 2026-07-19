import axios, { AxiosError } from "axios";
import type { ErrorResponse } from "../types/errorResponse";

/**
 * Axios共通インスタンス
 *
 * 全API通信で共通利用します。
 */
const apiClient = axios.create({

    /**
     * Spring Boot APIのURL
     */
    baseURL: "http://localhost:8081/api",

    /**
     * タイムアウト
     */
    timeout: 10000,

    /**
     * JSON形式
     */
    headers: {
        "Content-Type": "application/json",
    },
});

/**
 * リクエスト送信前処理
 *
 * JWTトークンが存在する場合はAuthorizationヘッダーへ設定します。
 */
apiClient.interceptors.request.use((config) => {

    const token = localStorage.getItem("token");

    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});

/**
 * レスポンス受信後処理
 *
 * バックエンドの共通エラーをここで処理します。
 */
apiClient.interceptors.response.use(

    /**
     * 正常終了
     */
    (response) => response,

    /**
     * 異常終了
     */
    (error: AxiosError<ErrorResponse>) => {

        if (error.response) {

            const errorResponse = error.response.data;

            switch (error.response.status) {

                case 400:
                    console.error(errorResponse.message);
                    break;

                case 404:
                    console.error(errorResponse.message);
                    break;

                case 500:
                    console.error("システムエラーが発生しました。");
                    break;

                default:
                    console.error("予期しないエラーが発生しました。");
            }
        }

        return Promise.reject(error);
    }
);

export default apiClient;