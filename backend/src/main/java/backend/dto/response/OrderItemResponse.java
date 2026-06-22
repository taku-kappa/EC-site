package backend.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 注文商品レスポンスDTO
 */
@Getter
@Setter
public class OrderItemResponse {

    // 商品ID
    private Long productId;

    // 商品名
    private String productName;

    // 購入時価格
    private Integer price;

    // 購入数量
    private Integer quantity;

    // 小計
    private Integer subtotal;

}