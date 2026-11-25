package backend.dto.response;

import lombok.Data;

@Data
public class UserPurchaseAmountResponse {

    // ユーザーID
    private Long userId;

    // ユーザー名
    private String userName;

    // 合計購入金額
    private Long totalPurchaseAmount;
}
