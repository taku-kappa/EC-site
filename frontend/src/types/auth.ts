/**
 * ログインAPIリクエスト
 */
export interface LoginRequest {

    // メールアドレス
    email: string;

    // パスワード
    password: string;
}

/**
 * ログインAPIレスポンス
 */
export interface LoginResponse {

    // JWTトークン
    token: string;
}