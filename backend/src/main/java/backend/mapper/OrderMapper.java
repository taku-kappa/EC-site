package backend.mapper;

import backend.dto.response.MonthlySalesResponse;
import backend.dto.response.OrderHistoryResponse;
import backend.dto.response.UserPurchaseAmountResponse;
import backend.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.YearMonth;
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
     * ユーザーの注文履歴一覧を取得します。
     *
     * @param userId ユーザーID
     * @return 注文履歴一覧
     */
    List<OrderHistoryResponse> findOrderHistory(Long userId);

    /**
     * ユーザー別購入金額一覧を取得します。
     *
     * @param size 1ページあたりの取得件数
     * @param offset 取得開始位置
     * @return ユーザー別購入金額一覧
     */
    List<UserPurchaseAmountResponse> findUserPurchaseAmount(
            @Param("size") Integer size,
            @Param("offset") Integer offset
    );

    /**
     * ユーザー別購入金額総件数を取得します。
     *
     * @return ユーザー別購入金額総件数
     */
    Long countUserPurchaseAmount();

    /**
     * 月別売上金額を取得します。
     *
     * @param start 集計対象の開始月（YYYY-MM）
     * @param end 集計対象の終了月（YYYY-MM）
     * @return 月別売上金額の一覧
     */
    List<MonthlySalesResponse> findMonthlySales (
            @Param("start") YearMonth start,
            @Param("end") YearMonth end
    );
}