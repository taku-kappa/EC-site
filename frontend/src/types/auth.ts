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

    // ユーザーID
    userId: number;

    // JWTトークン
    token: string;
}

// /**
//  * APIレスポンス
//  */
// export interface LoginApiResponse {

//     success: boolean;

//     data: LoginResponse;
// }