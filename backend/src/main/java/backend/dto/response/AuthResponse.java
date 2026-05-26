package backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private boolean success;
    private AuthResponseData data;

//    // レスポンスのJSON構造（"data": { ... }）に合わせるための内部クラス
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class AuthData {
//        private Long userId;
//        private String token;
//    }
}
