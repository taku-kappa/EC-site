/**
 * バックエンドから返却されるエラーレスポンス
 *
 * GlobalExceptionHandler の ErrorResponse と対応しています。
 */
export interface ErrorResponse {

    /**
     * HTTPステータスコード
     *
     * 400：リクエストエラー
     * 404：リソースが存在しない
     * 500：システムエラー
     */
    status: number;

    /**
     * バックエンドから返却されるエラーメッセージ
     */
    message: string;

    /**
     * エラー発生日時
     */
    timestamp: string;
}