package backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CartItem {

    private Long id;

    private Long cartId;

    private Long productId;

    private Integer quantity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}