package backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "メールアドレスは必須入力です")
    @Email(message = "正しいメールアドレスの形式で入力してください")
    private String email;

    @NotBlank(message = "パスワードは必須入力です")
    private String password;
}