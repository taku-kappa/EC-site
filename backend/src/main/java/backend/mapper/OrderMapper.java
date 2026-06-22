package backend.mapper;

import backend.dto.response.OrderHistoryResponse;
import backend.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * ユーザーの注文履歴一覧取得
     *
     * @param userId ユーザーID
     * @return 注文履歴一覧
     */
    List<OrderHistoryResponse> findOrderHistory(Long userId);
}