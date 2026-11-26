package backend.service;

import backend.dto.response.MonthlySalesListResponse;
import backend.dto.response.MonthlySalesResponse;
import backend.dto.response.OrderHistoryDetailResponse;
import backend.dto.response.OrderHistoryResponse;
import backend.dto.response.OrderItemResponse;
import backend.dto.response.OrderResponse;
import backend.dto.response.ProductSalesCountResponse;
import backend.dto.response.UserPurchaseAmountPageResponse;
import backend.dto.response.UserPurchaseAmountResponse;
import backend.entity.Cart;
import backend.entity.CartItem;
import backend.entity.Order;
import backend.entity.OrderItem;
import backend.entity.Product;
import backend.entity.ProductStock;
import backend.exception.BusinessException;
import backend.exception.ResourceNotFoundException;
import backend.mapper.CartItemMapper;
import backend.mapper.CartMapper;
import backend.mapper.OrderItemMapper;
import backend.mapper.OrderMapper;
import backend.mapper.ProductMapper;
import backend.mapper.ProductStockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartMapper cartMapper;

    private final CartItemMapper cartItemMapper;

    private final ProductMapper productMapper;

    private final ProductStockMapper productStockMapper;

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    /**
     * 注文を確定します。
     *
     * カート内の商品をもとに注文情報・注文明細を作成し、
     * 在庫を減算した後、カート内の商品を削除します。
     *
     * @param userId ログインユーザーID
     * @return 注文結果
     */
    @Transactional
    public OrderResponse createOrder(Long userId) {

        // ユーザーのカート取得
        Cart cart = cartMapper.findByUserId(userId);

        if (cart == null) {
            throw new ResourceNotFoundException("カートが存在しません。");
        }

        // カート内商品取得（product_id昇順）
        List<CartItem> cartItems =
                cartItemMapper.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new BusinessException("カート内に商品が存在しません。");
        }

        int totalPrice = 0;

        // -----------------------------
        // 合計金額計算・在庫チェック
        // -----------------------------
        for (CartItem cartItem : cartItems) {

            Product product =
                    productMapper.findById(cartItem.getProductId());

            if (product == null || Boolean.TRUE.equals(product.getDeleted())) {
                throw new ResourceNotFoundException("商品が存在しません。");
            }

            // 悲観ロックで在庫取得
            ProductStock stock =
                    productStockMapper.findByProductIdForUpdate(
                            product.getId()
                    );

            if (stock == null) {
                throw new ResourceNotFoundException("在庫情報が存在しません。");
            }

            if (stock.getStockQuantity() < cartItem.getQuantity()) {
                throw new BusinessException(
                        product.getName() + " の在庫が不足しています。"
                );
            }

            totalPrice +=
                    product.getPrice() * cartItem.getQuantity();
        }

        // -----------------------------
        // 注文情報登録
        // -----------------------------
        Order order = new Order();

        order.setUserId(userId);
        order.setTotalPrice(totalPrice);
        order.setStatus("COMPLETED");
        order.setOrderedAt(LocalDateTime.now());

        orderMapper.insert(order);

        // -----------------------------
        // 在庫減算・注文明細登録
        // -----------------------------
        for (CartItem cartItem : cartItems) {

            Product product =
                    productMapper.findById(cartItem.getProductId());

            ProductStock stock =
                    productStockMapper.findByProductIdForUpdate(
                            product.getId()
                    );

            // 在庫減算
            stock.setStockQuantity(
                    stock.getStockQuantity()
                            - cartItem.getQuantity()
            );

            int updateCount =
                    productStockMapper.update(stock);

            if (updateCount == 0) {
                throw new BusinessException(
                        "在庫更新に失敗しました。"
                );
            }

            // 注文明細登録
            OrderItem orderItem = new OrderItem();

            orderItem.setOrderId(order.getId());
            orderItem.setProductId(product.getId());

            // スナップショット保存
            orderItem.setProductName(product.getName());
            orderItem.setPrice(product.getPrice());

            orderItem.setQuantity(cartItem.getQuantity());

            orderItemMapper.insert(orderItem);
        }

        // -----------------------------
        // カート内商品削除
        // -----------------------------
        cartItemMapper.deleteByCartId(cart.getId());

        // -----------------------------
        // レスポンス生成
        // -----------------------------
        OrderResponse response =
                new OrderResponse();

        response.setOrderId(order.getId());
        response.setTotalPrice(totalPrice);
        response.setStatus(order.getStatus());

        return response;
    }

    /**
     * ログインユーザーの注文履歴一覧を取得します。
     *
     * @param userId ユーザーID
     * @return 注文履歴一覧
     */
    public List<OrderHistoryResponse> getOrderHistory(
            Long userId) {

        return orderMapper.findOrderHistory(userId);
    }

    /**
     * 注文履歴詳細を取得します。
     *
     * 指定された注文がログインユーザー自身の注文であることを確認した上で、
     * 注文情報と注文明細を返却します。
     *
     * @param userId ユーザーID
     * @param orderId 注文ID
     * @return 注文詳細
     */
    public OrderHistoryDetailResponse getOrderDetail(
            Long userId,
            Long orderId) {

        // 注文取得
        Order order = orderMapper.findById(orderId);

        if (order == null) {
            throw new ResourceNotFoundException("注文が存在しません。");
        }

        // 他ユーザーの注文参照防止
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("参照権限がありません。");
        }

        // 注文商品取得
        List<OrderItemResponse> items =
                orderItemMapper.findOrderItems(orderId);

        // 小計計算
        items.forEach(item ->
                item.setSubtotal(
                        item.getPrice() * item.getQuantity()
                )
        );

        // レスポンス生成
        OrderHistoryDetailResponse response =
                new OrderHistoryDetailResponse();

        response.setOrderId(order.getId());
        response.setTotalPrice(order.getTotalPrice());
        response.setStatus(order.getStatus());
        response.setOrderedAt(order.getOrderedAt());
        response.setItems(items);

        return response;
    }

    /**
     * 商品販売数一覧を取得します。
     *
     * @return 商品別販売数一覧
     */
    public List<ProductSalesCountResponse> getProductSalesCounts() {
        List<ProductSalesCountResponse> responses =
                orderItemMapper.findProductSalesCounts();

        return responses;
    }

    /**
     * ユーザー別購入金額を取得します。
     *
     * @param page ページ番号（省略時は0）
     * @return ユーザー別購入金額一覧とページ情報
     */
    public UserPurchaseAmountPageResponse getUserPurchaseAmount(Integer page) {
        // １ページあたりの取得件数
        Integer size = 20;

        // pageが渡されなかった場合は0を設定
        if(page == null) {
            page = 0;
        }

        // pageが負数の場合は例外をスローする
        if(page < 0) {
            throw new IllegalArgumentException("pageは0以上を指定してください。");
        }

        Integer offset = page * size;

        List<UserPurchaseAmountResponse> userPurchaseAmount =
                orderMapper.findUserPurchaseAmount(size, offset);

        Long countUserPurchaseAmount = orderMapper.countUserPurchaseAmount();

        Integer totalPages = Math.toIntExact((countUserPurchaseAmount + size - 1) / size);

        // レスポンス生成
        UserPurchaseAmountPageResponse response = new UserPurchaseAmountPageResponse();

        response.setUserPurchaseAmount(userPurchaseAmount);
        response.setPage(page);
        response.setSize(size);
        response.setTotalElements(countUserPurchaseAmount);
        response.setTotalPages(totalPages);

        return response;
    }

    /**
     * 月別売上金額を取得します。
     *
     * @param startMonth 集計対象の開始月（YYYY-MM）
     * @param endMonth 集計対象の終了月（YYYY-MM）
     * @return 月別売上金額の一覧と対象月（始め・終わり）
     */
    public MonthlySalesListResponse getMonthlySalesList(
            String startMonth,
            String endMonth) {

        // 開始月・終了月を年月型に変換して入力値を検証
        YearMonth start = validateYearMonth(startMonth);
        YearMonth end = validateYearMonth(endMonth);

        // 開始月が終了月より後の場合はエラー
        if (start.isAfter(end)) {
            throw new IllegalArgumentException(
                    "開始月は終了月以前の日付を指定してください。");
        }

        // Mapperから月別売上金額を取得
        List<MonthlySalesResponse> responses =
                orderMapper.findMonthlySales(start, end);

        // 取得した売上データを年月をキーとするMapに変換
        Map<String, Long> salesMap = new HashMap<>();

        for (MonthlySalesResponse monthlySales : responses) {
            salesMap.put(
                    monthlySales.getYearMonth(),
                    monthlySales.getTotalSalesAmount()
            );
        }

        // 開始月から終了月までの月別売上データを作成
        List<MonthlySalesResponse> monthlySalesList = new ArrayList<>();

        for (YearMonth month = start;
             !month.isAfter(end);
             month = month.plusMonths(1)) {

            // 月別売上情報を格納するDTOを生成
            MonthlySalesResponse monthlySales = new MonthlySalesResponse();

            // 対象年月を設定
            monthlySales.setYearMonth(month.toString());

            // 売上が存在しない月は0円を設定
            monthlySales.setTotalSalesAmount(
                    salesMap.getOrDefault(month.toString(), 0L)
            );

            monthlySalesList.add(monthlySales);
        }

        // レスポンスDTOを生成
        MonthlySalesListResponse response = new MonthlySalesListResponse();

        response.setMonthlySales(monthlySalesList);
        response.setStartMonth(startMonth);
        response.setEndMonth(endMonth);

        return response;
    }

    /**
     * 年月の入力値を検証してYearMonth型に変換します。
     *
     * @param month 検証対象の年月（YYYY-MM）
     * @return 検証済みの年月
     * @throws IllegalArgumentException 年月の形式が不正な場合
     */
    private YearMonth validateYearMonth(String month) {

        // nullまたは空文字の場合はエラー
        if (month == null || month.isBlank()) {
            throw new IllegalArgumentException(
                    "年月を指定してください。");
        }

        try {
            // 年月形式を検証してYearMonth型に変換
            return YearMonth.parse(month);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "年月はYYYY-MM形式で指定してください。", e);
        }
    }

}





//import java.util.function.Consumer;
//
//public class Main {
//
//    // 「関数（Consumer）」を引数として受け取るメソッド
//    public static void doSomethingAsync(Consumer<String> resolve) {
//        // 引数として受け取った「resolve」は関数なので、ここで実行（.accept）できる！
//        // このとき、"ハロー" という文字列を引数として渡している
//        resolve.accept("ハロー");
//    }
//
//    public static void main(String[] args) {
//        // doSomethingAsync を呼び出す際に、「実行してほしい処理（関数）」を直接渡す
//        doSomethingAsync((data) -> {
//            System.out.println("結果を表示: " + data);
//        });
//    }
//
//    public void  data() {
//
//    }
//}