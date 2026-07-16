package backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * カート数量更新リクエストDTO
 */
@Data
public class CartQuantityUpdateRequest {

    // 更新後の数量
    @NotNull(message = "数量は必須です。")
    @Min(value = 1, message = "数量は1以上を指定してください。")
    private Integer quantity;

}
