package backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API共通レスポンス
 *
 * @param <T> レスポンスデータ型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    /**
     * 成功フラグ
     */
    private boolean success;

    /**
     * レスポンスデータ
     */
    private T data;

    /**
     * 成功レスポンス生成
     *
     * @param data レスポンスデータ
     * @return ApiResponse
     * @param <T> データ型
     */
    public static <T> ApiResponse<T> success(T data) {

        return new ApiResponse<>(
                true,
                data
        );
    }

    /**
     * 失敗レスポンス生成
     *
     * @param data エラーデータ
     * @return ApiResponse
     * @param <T> データ型
     */
    public static <T> ApiResponse<T> fail(T data) {

        return new ApiResponse<>(
                false,
                data
        );
    }
}