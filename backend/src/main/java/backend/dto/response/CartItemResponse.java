package backend.dto.response;

import lombok.Data;

@Data
public class CartItemResponse {

    private Long cartItemId;

    private Long productId;

    private String productName;

    private Integer price;

    private Integer quantity;

    private Integer subtotal;

    private String imageUrl;

}