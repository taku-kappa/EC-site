package backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 商品在庫更新レスポンスDTO
 */
@Data
@AllArgsConstructor
public class ProductStockUpdateResponse {

    private Long productId;

    private Integer stockQuantity;

    private String message;
}