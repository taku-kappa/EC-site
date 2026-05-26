package backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// レスポンスのJSON構造（"data": { ... }）に合わせるための内部クラス
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseData {
    private Long userId;
    private String token;
}
