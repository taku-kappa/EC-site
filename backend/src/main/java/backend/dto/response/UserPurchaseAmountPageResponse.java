package backend.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UserPurchaseAmountPageResponse {

    // 取得したユーザー別購入金額一覧
    private List<UserPurchaseAmountResponse> userPurchaseAmount;

    // 現在のページ番号
    private Integer page;

    // 1ページあたりの取得件数
    private Integer size;

    // 総件数
    private Long totalElements;

    // 総ページ数
    private Integer totalPages;

}
