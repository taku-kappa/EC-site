package backend.controller;

import backend.dto.response.ApiResponse;
import backend.dto.response.OrderHistoryDetailResponse;
import backend.dto.response.OrderHistoryResponse;
import backend.dto.response.OrderResponse;
import backend.security.CustomUserDetails;
import backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    /**
     * 注文サービス
     */
    private final OrderService orderService;

    /**
     * ログインユーザーの注文を確定します。
     *
     * JWT認証情報からログインユーザーIDを取得し、
     * カート内の商品を注文として確定します。
     * 注文確定後は在庫を更新し、カート内の商品を削除します。
     *
     * @param userDetails ログインユーザー情報
     * @return 注文結果
     */
    @PostMapping("/orders")
    public ApiResponse<OrderResponse> createOrder(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // 注文確定処理を実行
        OrderResponse response =
                orderService.createOrder(userDetails.getId());

        // 成功レスポンスを返却
        return ApiResponse.success(response);
    }

    /**
     * ログインユーザーの注文履歴一覧を取得します。
     *
     * @param userDetails ログインユーザー情報
     * @return 注文履歴一覧
     */
    @GetMapping("/orders")
    public ApiResponse<List<OrderHistoryResponse>> getOrderHistory(
            @AuthenticationPrincipal
            CustomUserDetails userDetails) {

        List<OrderHistoryResponse> response =
                orderService.getOrderHistory(
                        userDetails.getId()
                );

        return ApiResponse.success(response);
    }

    /**
     * 注文履歴詳細を取得します。
     *
     * @param userDetails ログインユーザー情報
     * @param orderId 注文ID
     * @return 注文履歴詳細
     */
    @GetMapping("/orders/{id}")
    public ApiResponse<OrderHistoryDetailResponse> getOrderDetail(
            @AuthenticationPrincipal
            CustomUserDetails userDetails,
            @PathVariable("id")
            Long orderId) {

        OrderHistoryDetailResponse response =
                orderService.getOrderDetail(
                        userDetails.getId(),
                        orderId
                );

        return ApiResponse.success(response);
    }

}