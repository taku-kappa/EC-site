package backend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 注文履歴詳細レスポンスDTO
 */
@Getter
@Setter
public class OrderHistoryDetailResponse {

    // 注文ID
    private Long orderId;

    // 合計金額
    private Integer totalPrice;

    // 注文ステータス
    private String status;

    // 注文日時
    private LocalDateTime orderedAt;

    // 注文商品一覧
    private List<OrderItemResponse> items;

}