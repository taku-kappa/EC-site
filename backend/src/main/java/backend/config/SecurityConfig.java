package backend.config;

import backend.security.JwtAuthenticationEntryPoint;
import backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    /**
     * Password Encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig
    ) throws Exception {

        return authConfig.getAuthenticationManager();
    }

    /**
     * Security Filter
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http

                // CSRF無効
                .csrf(csrf -> csrf.disable())

                // CORS有効
                .cors(cors -> {
                })

                // Sessionを使用しない
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // 認証エラーハンドリング
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(
                                jwtAuthenticationEntryPoint
                        )
                )

                // 認可設定
                .authorizeHttpRequests(auth -> auth

                        // 認証不要
                        .requestMatchers(
                                "/api/auth/**"
                        ).permitAll()

                        // 商品閲覧
                        .requestMatchers(
                                HttpMethod.GET,
                                "/products/**"
                        ).permitAll()

                        // 管理者のみ
                        .requestMatchers(
                                HttpMethod.POST,
                                "/products/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/products/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/products/**"
                        ).hasRole("ADMIN")

                        // USER / ADMIN
                        .requestMatchers(
                                "/orders/**",
                                "/cart/**"
                        ).hasAnyRole("USER", "ADMIN")

                        // H2 Console（開発時）
                        .requestMatchers(
                                "/h2-console/**"
                        ).permitAll()

                        // その他
                        .anyRequest().authenticated()
                )

                // H2 Console用
                .headers(headers ->
                        headers.frameOptions(frame -> frame.disable())
                )

                // JWT Filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}