package backend.dto.response;

import lombok.Data;

@Data
public class ProductDetailResponse {

    // 商品ID
    private Long id;

    // 商品名
    private String name;

    // 商品説明
    private String description;

    // 商品画像URL
    private String imageUrl;

    // カテゴリID
    private Long categoryId;

    // カテゴリ名
    private String categoryName;

    // 在庫数
    private Integer stockQuantity;
}