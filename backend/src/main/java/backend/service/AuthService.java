package backend.service;

import backend.dto.request.LoginRequest;
import backend.dto.request.RegisterRequest;
import backend.dto.response.AuthResponseData;
import backend.entity.User;
import backend.mapper.UserMapper;
import backend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 認証 Service
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    /**
     * ユーザー登録処理
     *
     * @param request ユーザー登録リクエスト
     * @return 登録ユーザーID
     */
    @Transactional
    public Long registerUser(RegisterRequest request) {

        // メールアドレス重複チェック
        if (userMapper.countByEmail(request.getEmail()) > 0) {
            throw new RuntimeException(
                    "Email is already in use."
            );
        }

        // ユーザー生成
        User user = new User();

        user.setName(request.getName());

        user.setEmail(request.getEmail());

        // パスワードをハッシュ化
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // 一般ユーザー権限
        user.setRole("ROLE_USER");

        // DB登録
        userMapper.insertUser(user);

        return user.getId();
    }

    /**
     * ログイン処理
     *
     * @param request ログインリクエスト
     * @return JWTトークン
     */
    public AuthResponseData authenticateUser(LoginRequest request) {

        // 認証実行
        Authentication authentication =
                authenticationManager.authenticate(
                        /**
                         * ーー 内部処理が含まれるため説明 ーー
                         * authenticationManager ➡︎ ProviderManager ➡︎
                         * DaoAuthenticationProvider ➡︎ UserDetailsServiceImplの順で処理が呼ばれる
                         * ① UserDetailsServiceImplのloadUserByUsernameメソッドでDBからユーザー情報を取得
                         * ② PasswordEncoder.matchesによりパスワード一致の検証(DaoAuthenticationProviderで呼び出し)
                         * ①,②の情報をProvider ➡︎ Managerへ渡す
                         */
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        // SecurityContextへセット
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        // JWT生成
        String token = tokenProvider.generateToken(authentication);

        // JWTトークンからユーザーID取得
        Long userId = tokenProvider.getUserIdFromJWT(token);

        AuthResponseData data =
                new AuthResponseData(userId, token);

        return data;
    }
}