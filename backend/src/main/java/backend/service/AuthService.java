package backend.service;

import backend.dto.request.LoginRequest;
import backend.dto.request.RegisterRequest;
import backend.entity.User;
import backend.repository.UserRepository;
import backend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    /**
     * ユーザー登録処理
     * @return 登録されたユーザーのID
     */
    @Transactional
    public Long registerUser(RegisterRequest request) {
        // 1. メールアドレスの重複チェック
        if (userRepository.existsByEmail(request.getEmail())) {
            // ※本来は専用のカスタム例外を投げ、GlobalExceptionHandlerで処理するのがベストです
            throw new RuntimeException("Email is already in use.");
        }

        // 2. ユーザーエンティティの生成と値のセット
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // ハッシュ化
        user.setRole("ROLE_USER"); // 新規登録は一般ユーザー権限

        // 3. DBへ保存
        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    /**
     * ログイン処理
     * @return 生成されたJWTトークン
     */
    public String authenticateUser(LoginRequest request) {
        // 1. 入力されたEmailとPasswordで認証を試みる (UserDetailsServiceImplが呼ばれます)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. 認証情報をSecurityContextにセット
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. 認証情報をもとにJWTを生成して返す
        return tokenProvider.generateToken(authentication);
    }
}