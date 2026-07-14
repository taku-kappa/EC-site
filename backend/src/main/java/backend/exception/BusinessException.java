package backend.exception;

/**
 * 業務ルール違反が発生した場合に送出する例外クラス
 *
 * 在庫不足や注文不可など、
 * システムエラーではなく業務上のエラーを表します。
 */
public class BusinessException extends RuntimeException {

    /**
     * BusinessExceptionを生成します。
     *
     * @param message エラーメッセージ
     */
    public BusinessException(String message) {
        super(message);
    }

}