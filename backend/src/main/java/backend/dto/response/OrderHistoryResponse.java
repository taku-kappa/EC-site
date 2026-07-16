package backend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 注文履歴一覧レスポンスDTO
 */
@Getter
@Setter
public class OrderHistoryResponse {

    // 注文ID
    private Long orderId;

    // 合計金額
    private Integer totalPrice;

    // 注文ステータス
    private String status;

    // 注文日時
    private LocalDateTime orderedAt;

}