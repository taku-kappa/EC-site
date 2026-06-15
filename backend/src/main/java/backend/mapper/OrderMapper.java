package backend.mapper;

import backend.entity.Order;
import org.apache.ibatis.annotations.Mapper;

// 注文Mapper
@Mapper
public interface OrderMapper {

    /**
     * 注文情報を登録します。
     *
     * @param order 注文情報
     */
    void insert(Order order);

    /**
     * 注文IDから注文情報を取得します。
     *
     * @param id 注文ID
     * @return 注文情報
     */
    Order findById(Long id);

}