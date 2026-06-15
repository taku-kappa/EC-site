package backend.mapper;

import backend.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// 注文明細Mapper
@Mapper
public interface OrderItemMapper {

    /**
     * 注文明細を登録します。
     *
     * @param orderItem 注文明細
     */
    void insert(OrderItem orderItem);

    /**
     * 注文IDから注文明細一覧を取得します。
     *
     * @param orderId 注文ID
     * @return 注文明細一覧
     */
    List<OrderItem> findByOrderId(Long orderId);

}