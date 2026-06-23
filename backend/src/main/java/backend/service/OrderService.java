package backend.service;

import backend.dto.response.OrderHistoryDetailResponse;
import backend.dto.response.OrderHistoryResponse;
import backend.dto.response.OrderItemResponse;
import backend.dto.response.OrderResponse;
import backend.entity.Cart;
import backend.entity.CartItem;
import backend.entity.Order;
import backend.entity.OrderItem;
import backend.entity.Product;
import backend.entity.ProductStock;
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
import java.util.List;

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
            throw new RuntimeException("カートが存在しません。");
        }

        // カート内商品取得（product_id昇順）
        List<CartItem> cartItems =
                cartItemMapper.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("カート内に商品が存在しません。");
        }

        int totalPrice = 0;

        // -----------------------------
        // 合計金額計算・在庫チェック
        // -----------------------------
        for (CartItem cartItem : cartItems) {

            Product product =
                    productMapper.findById(cartItem.getProductId());

            if (product == null || Boolean.TRUE.equals(product.getDeleted())) {
                throw new RuntimeException("商品が存在しません。");
            }

            // 悲観ロックで在庫取得
            ProductStock stock =
                    productStockMapper.findByProductIdForUpdate(
                            product.getId()
                    );

            if (stock == null) {
                throw new RuntimeException("在庫情報が存在しません。");
            }

            if (stock.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException(
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
                throw new RuntimeException(
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
            throw new RuntimeException("注文が存在しません。");
        }

        // 他ユーザーの注文参照防止
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("参照権限がありません。");
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

}