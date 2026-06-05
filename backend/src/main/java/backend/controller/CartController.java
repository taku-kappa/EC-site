package backend.controller;

import backend.dto.response.ApiResponse;
import backend.dto.response.CartResponse;
import backend.security.CustomUserDetails;
import backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
}