package backend.dto.response;

import lombok.Data;

// 商品一覧レスポンスDTO
@Data
public class ProductListResponse {

    // 商品ID
    private Long id;

    // 商品名
    private String name;

    // 商品説明
    private String description;

    // 商品価格
    private Integer price;


    // 商品画像URL
    private String imageUrl;

    // カテゴリID
    private Long categoryId;

    // 在庫数
    private Integer stockQuantity;
}