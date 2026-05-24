package backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private boolean success;
    private AuthData data;

    // カスタムコンストラクタ
    public AuthResponse(boolean success, String token, Long userId, String email, String role) {
        this.success = success;
        this.data = new AuthData(token, userId, email, role);
    }

    // レスポンスのJSON構造（"data": { ... }）に合わせるための内部クラス
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthData {

        private String token;
        private Long userId;
        private String email;
        private String role;
    }
}
