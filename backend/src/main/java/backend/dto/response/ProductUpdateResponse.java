package backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 商品更新レスポンスDTO
 */
@Data
@AllArgsConstructor
public class ProductUpdateResponse {

    private Long productId;

    private String message;
}