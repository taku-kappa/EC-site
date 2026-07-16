package backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Order {

    // 注文ID
    private Long id;

    // 注文ユーザーID
    private Long userId;

    // 注文合計金額
    private Integer totalPrice;

    // 注文ステータス （例：COMPLETED）
    private String status;

    // 注文日時
    private LocalDateTime orderedAt;

    // 作成日時
    private LocalDateTime createdAt;

    // 更新日時
    private LocalDateTime updatedAt;

}