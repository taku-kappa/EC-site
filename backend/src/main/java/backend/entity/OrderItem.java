package backend.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderItem {

    private Long id;

    private Order order;

    private Product product;

    private String productName; // 購入時の商品名（マスター変更対策）

    private Integer price; // 購入時の価格（価格改定対策）

    private Integer quantity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}