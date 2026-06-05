package backend.mapper;

import backend.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CartMapper {

    /**
     * カート取得（ユーザーIDから）
     *
     * @param userId ユーザーID
     * @return Cart
     */
    Cart findByUserId(Long userId);

}