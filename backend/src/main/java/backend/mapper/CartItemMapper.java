package backend.mapper;

import backend.dto.response.CartItemResponse;
import backend.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartItemMapper {

    /**
     * カート内の商品一覧取得
     *
     * @param cartId カートID
     * @return 商品一覧
     */
    List<CartItemResponse> findCartItems(Long cartId);

    /**
     * カートID・商品IDからカート商品取得
     *
     * @param cartId カートID
     * @param productId 商品ID
     * @return カート商品
     */
    CartItem findByCartIdAndProductId(Long cartId, Long productId);

    /**
     * カート商品追加
     *
     * @param cartItem カート商品
     */
    void insert(CartItem cartItem);

    /**
     * 数量更新
     *
     * @param cartItem カート商品
     */
    void updateQuantity(CartItem cartItem);

    /**
     * カート商品IDからカート商品を取得
     *
     * @param id カート商品ID
     * @return カート商品情報
     */
    CartItem findById(Long id);

    /**
     * カート商品削除
     *
     * @param id カート商品ID
     */
    void deleteById(Long id);

    /**
     * カートIDからカート一覧商品取得
     *
     * @param cartId カートID
     * @return カート商品一覧
     */
    List<CartItem> findByCartId(Long cartId);

    /**
     * カート内商品をすべて削除
     *
     * @param cartId カートID
     */
    void deleteByCartId(Long cartId);

}