package backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderItem {

    // 注文明細ID
    private Long id;

    // 注文ID
    private Long orderId;

    // 商品ID
    private Long productId;

    // 注文時点の商品名（スナップショット）
    private String productName;

    // 注文時点の商品価格（スナップショット）
    private Integer price;

    // 注文数量
    private Integer quantity;

    // 作成日時
    private LocalDateTime createdAt;

    // 更新日時
    private LocalDateTime updatedAt;

}