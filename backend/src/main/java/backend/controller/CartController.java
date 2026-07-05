package backend.controller;

import backend.dto.request.CartItemRequest;
import backend.dto.request.CartQuantityUpdateRequest;
import backend.dto.response.ApiResponse;
import backend.dto.response.CartResponse;
import backend.security.CustomUserDetails;
import backend.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * ログインユーザーのカート取得
     *
     * @param userDetails 認証ユーザー
     * @return カート情報
     */
    @GetMapping("/cart")
    public ApiResponse<CartResponse> getCart(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        CartResponse response =
                cartService.getCart(userDetails.getId());

        return ApiResponse.success(response);
    }

    /**
     * ログインユーザーのカートに商品を追加します。
     *
     * JWT認証情報からログインユーザーIDを取得し、
     * 指定された商品をカートへ追加します。
     * 既に同じ商品が存在する場合は数量を加算します。
     *
     * @param userDetails ログインユーザー情報
     * @param request カート追加リクエスト情報
     * @return カート追加結果
     */
    @PostMapping("/cart/items")
    public ApiResponse<String> addCartItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CartItemRequest request) {

        cartService.addCartItem(userDetails.getId(), request);

        return ApiResponse.success("商品をカートに追加しました。");
    }

    /**
     * ログインユーザーのカート内商品の数量を更新します。
     *
     * JWT認証情報からログインユーザーIDを取得し、
     * 指定されたカート商品の数量を更新します。
     *
     * @param userDetails ログインユーザー情報
     * @param cartItemId カート商品ID
     * @param request 数量更新リクエスト情報
     * @return 更新結果
     */
    @PutMapping("/cart/items/{id}")
    public ApiResponse<String> updateCartItemQuantity(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("id") Long cartItemId,
            @Valid @RequestBody CartQuantityUpdateRequest request) {

        // カート商品の数量を更新
        cartService.updateCartItemQuantity(
                userDetails.getId(),
                cartItemId,
                request
        );

        // 成功レスポンスを返却
        return ApiResponse.success("カート内商品の数量を更新しました。");
    }

    /**
     * ログインユーザーのカート内の商品を削除します。
     *
     * JWT認証情報からログインユーザーIDを取得し、
     * 指定されたカート商品を削除します。
     *
     * @param userDetails ログインユーザー情報
     * @param cartItemId カート商品ID
     * @return 削除結果
     */
    @DeleteMapping("/cart/items/{id}")
    public ApiResponse<String> deleteCartItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("id") Long cartItemId) {

        // カート商品を削除
        cartService.deleteCartItem(
                userDetails.getId(),
                cartItemId
        );

        // 成功レスポンスを返却
        return ApiResponse.success("商品をカートから削除しました。");
    }
}