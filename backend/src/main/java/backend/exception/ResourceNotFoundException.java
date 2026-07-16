package backend.exception;

/**
 * データが存在しない場合に送出する例外クラス
 *
 * 商品・ユーザー・注文など、
 * 指定されたデータが存在しない場合に使用します。
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * ResourceNotFoundExceptionを生成します。
     *
     * @param message エラーメッセージ
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

}