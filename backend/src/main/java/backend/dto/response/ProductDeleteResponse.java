package backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 商品削除レスポンスDTO
 */
@Data
@AllArgsConstructor
public class ProductDeleteResponse {

    // 商品ID
    private Long productId;

    // メッセージ
    private String message;
}