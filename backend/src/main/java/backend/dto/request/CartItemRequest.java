package backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * カート追加リクエストDTO
 */
@Data
public class CartItemRequest {

    // 商品ID
    @NotNull(message = "商品IDは必須です。")
    private Long productId;

    // 数量
    @NotNull(message = "数量は必須です。")
    @Min(value = 1, message = "数量は1以上を指定してください。")
    private Integer quantity;
}
