package backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "ユーザー名は必須入力です")
    @Size(max = 100, message = "ユーザー名は100文字以内で入力してください")
    private String name;

    @NotBlank(message = "メールアドレスは必須入力です")
    @Email(message = "正しいメールアドレスの形式で入力してください")
    @Size(max = 255, message = "メールアドレスは255文字以内で入力してください")
    private String email;

    @NotBlank(message = "パスワードは必須入力です")
    @Size(min = 6, max = 255, message = "パスワードは6文字以上255文字以内で入力してください")
    private String password;
}