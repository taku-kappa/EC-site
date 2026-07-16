/**
 * バックエンド共通レスポンス
 *
 * Spring BootのApiResponse<T>と対応する型
 */
export interface ApiResponse<T> {

    /**
     * 処理成功可否
     */
    success: boolean;

    /**
     * レスポンスデータ
     */
    data: T;

}