package backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 商品在庫更新リクエストDTO
 */
@Data
public class ProductStockUpdateRequest {

    // 更新後在庫数
    @NotNull(message = "在庫数は必須です")
    @Min(value = 0, message = "在庫数は0以上で入力してください")
    private Integer stockQuantity;

    // 楽観ロック用Version
    @NotNull(message = "versionは必須です")
    private Integer version;
}