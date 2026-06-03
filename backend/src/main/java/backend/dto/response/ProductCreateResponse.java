package backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 商品新規登録レスポンス
 */
@Data
@AllArgsConstructor
public class ProductCreateResponse {

    // 商品ID
    private Long productId;

    // メッセージ
    private String message;
}