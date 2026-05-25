package backend.security;

import backend.entity.User;
import backend.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService 実装クラス
 *
 * Spring Security の認証時に
 * ユーザー情報を取得する
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    /**
     * メールアドレスから
     * ユーザー情報取得
     *
     * @param email メールアドレス
     * @return UserDetails
     * @throws UsernameNotFoundException ユーザー未存在
     */
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // ユーザー取得
        User user = userMapper.findByEmail(email);

        // ユーザー存在チェック
        if (user == null) {
            throw new UsernameNotFoundException(
                    "User not found with email: " + email
            );
        }

        // Spring Security 用 UserDetails へ変換
        return CustomUserDetails.create(user);
    }
}