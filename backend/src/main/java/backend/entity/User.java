package backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * ユーザー Entity
 */
@Getter
@Setter
@NoArgsConstructor
public class User {

    // ユーザーID
    private Long id;

    // ユーザー名
    private String name;

    // メールアドレス
    private String email;

    // パスワード
    private String password;

    //権限(ROLE_USER / ROLE_ADMIN)
    private String role;

    // 作成日時
    private LocalDateTime createdAt;

    // 更新日時
    private LocalDateTime updatedAt;

    /**
     * Spring Security 用 権限取得
     *
     * @return 権限情報
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(
                new SimpleGrantedAuthority(role)
        );
    }
}