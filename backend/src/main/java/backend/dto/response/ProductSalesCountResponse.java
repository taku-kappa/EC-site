package backend.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品別販売数レスポンスDTO
 */
@Getter
@Setter
public class ProductSalesCountResponse {

    // 商品ID
    private Long productId;

    // 商品名
    private String productName;

    // 販売
    private Long salesQuantity;
}
