package backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * APIエラー発生時に返却する共通エラーレスポンス
 *
 * GlobalExceptionHandlerで発生した例外を
 * フロントエンドへ統一フォーマットで返却するために使用します。
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {

    /**
     * HTTPステータスコード
     * 例：400、401、403、404、500
     */
    private final int status;

    /**
     * エラーメッセージ
     */
    private final String message;

    /**
     * エラー発生日時
     */
    private final LocalDateTime timestamp;

}