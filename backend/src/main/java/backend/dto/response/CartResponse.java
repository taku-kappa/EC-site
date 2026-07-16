package backend.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CartResponse {

    private Long cartId;

    private Integer totalPrice;

    private List<CartItemResponse> items;

}