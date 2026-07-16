package backend.service;

import backend.dto.request.CartItemRequest;
import backend.dto.request.CartQuantityUpdateRequest;
import backend.dto.response.CartItemResponse;
import backend.dto.response.CartResponse;
import backend.entity.Cart;
import backend.entity.CartItem;
import backend.entity.Product;
import backend.exception.BusinessException;
import backend.exception.ResourceNotFoundException;
import backend.mapper.CartItemMapper;
import backend.mapper.CartMapper;
import backend.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartMapper cartMapper;

    private final CartItemMapper cartItemMapper;

    private final ProductMapper productMapper;

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

    /**
     * ログインユーザーのカートに商品を追加します。
     *
     * カートが存在しない場合は新規作成し、
     * 既に同じ商品がカート内に存在する場合は数量を加算します。
     * 存在しない場合は新しいカート商品として登録します。
     *
     * @param userId ログインユーザーID
     * @param request カート追加リクエスト情報
     */
    @Transactional
    public void addCartItem(Long userId, CartItemRequest request) {

        Cart cart = cartMapper.findByUserId(userId);

        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cartMapper.insert(cart);
        }

        Product product = productMapper.findById(request.getProductId());

        if (product == null) {
            throw new ResourceNotFoundException("商品が存在しません。");
        }

        CartItem cartItem =
                cartItemMapper.findByCartIdAndProductId(
                        cart.getId(),
                        request.getProductId()
                );

        if (cartItem == null) {

            cartItem = new CartItem();
            cartItem.setCartId(cart.getId());
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());

            cartItemMapper.insert(cartItem);

        } else {

            cartItem.setQuantity(
                    cartItem.getQuantity() + request.getQuantity()
            );

            cartItemMapper.updateQuantity(cartItem);
        }
    }

    /**
     * ログインユーザーのカート内商品の数量を更新します。
     *
     * カート商品がログインユーザー自身のカートに属していることを確認した上で、
     * 指定された数量に更新します。
     *
     * @param userId ログインユーザーID
     * @param cartItemId カート商品ID
     * @param request 数量更新リクエスト情報
     */
    @Transactional
    public void updateCartItemQuantity(
            Long userId,
            Long cartItemId,
            CartQuantityUpdateRequest request) {

        // ログインユーザーのカートを取得
        Cart cart = cartMapper.findByUserId(userId);

        if (cart == null) {
            throw new ResourceNotFoundException("カートが存在しません。");
        }

        // 更新対象のカート商品を取得
        CartItem cartItem = cartItemMapper.findById(cartItemId);

        if (cartItem == null) {
            throw new ResourceNotFoundException("カート内の商品が存在しません。");
        }

        // 他ユーザーのカート商品を更新できないようチェック
        if (!cart.getId().equals(cartItem.getCartId())) {
            throw new BusinessException("更新権限がありません。");
        }

        // 数量を更新
        cartItem.setQuantity(request.getQuantity());

        // DBへ反映
        cartItemMapper.updateQuantity(cartItem);
    }

    /**
     * ログインユーザーのカート内の商品を削除します。
     *
     * カート商品がログインユーザー自身のカートに属していることを確認した上で、
     * 指定された商品をカートから削除します。
     *
     * @param userId ログインユーザーID
     * @param cartItemId カート商品ID
     */
    @Transactional
    public void deleteCartItem(
            Long userId,
            Long cartItemId) {

        // ログインユーザーのカートを取得
        Cart cart = cartMapper.findByUserId(userId);

        if (cart == null) {
            throw new ResourceNotFoundException("カートが存在しません。");
        }

        // 削除対象のカート商品を取得
        CartItem cartItem = cartItemMapper.findById(cartItemId);

        if (cartItem == null) {
            throw new ResourceNotFoundException("カート内の商品が存在しません。");
        }

        // 他ユーザーのカート商品を削除できないようチェック
        if (!cart.getId().equals(cartItem.getCartId())) {
            throw new BusinessException("削除権限がありません。");
        }

        // カート商品を削除
        cartItemMapper.deleteById(cartItemId);
    }

}