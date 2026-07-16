package backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 商品新規登録リクエストDTO
 */
@Data
public class ProductCreateRequest {

    // カテゴリID
    @NotNull(message = "カテゴリIDは必須です")
    private Long categoryId;

    // 商品名
    @NotBlank(message = "商品名は必須です")
    private String name;

    // 商品説明
    private String description;

    // 商品価格
    @NotNull(message = "価格は必須です")
    @Min(value = 0, message = "価格は0以上で入力してください")
    private Integer price;

    // 商品画像URL
    private String imageUrl;

    // 在庫数
    @NotNull(message = "在庫数は必須です")
    @Min(value = 0, message = "在庫数は0以上で入力してください")
    private Integer stockQuantity;
}