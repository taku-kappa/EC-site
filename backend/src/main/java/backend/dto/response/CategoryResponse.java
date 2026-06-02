package backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * カテゴリ一覧取得APIレスポンスDTO
 */
@Data
@AllArgsConstructor
public class CategoryResponse {

    // カテゴリID
    private Long id;

    // カテゴリ名
    private String name;
}