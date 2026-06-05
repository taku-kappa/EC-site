package backend.service;

import backend.dto.response.CartItemResponse;
import backend.dto.response.CartResponse;
import backend.entity.Cart;
import backend.mapper.CartItemMapper;
import backend.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartMapper cartMapper;

    private final CartItemMapper cartItemMapper;

    /**
     * ログインユーザーのカート取得
     *
     * @param userId ユーザーID
     * @return カート情報
     */
    public CartResponse getCart(Long userId) {

        Cart cart = cartMapper.findByUserId(userId);

        if (cart == null) {
            return createEmptyCart();
        }

        List<CartItemResponse> items =
                cartItemMapper.findCartItems(cart.getId());

        int totalPrice = items.stream()
                .mapToInt(item -> {
                    int subtotal =
                            item.getPrice() * item.getQuantity();

                    item.setSubtotal(subtotal);

                    return subtotal;
                })
                .sum();

        CartResponse response = new CartResponse();

        response.setCartId(cart.getId());
        response.setItems(items);
        response.setTotalPrice(totalPrice);

        return response;
    }

    /**
     * 空カート生成
     *
     * @return 空カート
     */
    private CartResponse createEmptyCart() {

        CartResponse response = new CartResponse();

        response.setTotalPrice(0);
        response.setItems(List.of());

        return response;
    }
}