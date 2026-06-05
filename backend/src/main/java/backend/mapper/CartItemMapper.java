package backend.mapper;

import backend.dto.response.CartItemResponse;
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

}