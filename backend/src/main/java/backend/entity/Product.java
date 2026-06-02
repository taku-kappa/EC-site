package backend.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
public class Product {

    private Long id;

    private Category category;

    private String name;

    private String description;

    private Integer price;

    private String imageUrl;

    private Integer version = 0; // 商品情報の楽観ロック（必要に応じて使用）

    private Boolean deleted = false; // 論理削除フラグ

    private ProductStock productStock;

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