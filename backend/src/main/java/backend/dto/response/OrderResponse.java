package backend.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 注文確定レスポンスDTO
 */
@Getter
@Setter
public class OrderResponse {

    // 注文ID
    private Long orderId;

    // 注文合計金額
    private Integer totalPrice;

    // 注文ステータス
    private String status;

}
