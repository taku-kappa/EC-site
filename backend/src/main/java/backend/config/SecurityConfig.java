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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

/**
 * Spring Security 設定クラス
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // JWT認証フィルター
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 認証エントリポイント
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    /**
     * PasswordEncoder
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager
     *
     * @param authConfig AuthenticationConfiguration
     * @return AuthenticationManager
     * @throws Exception 例外
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig
    ) throws Exception {

        return authConfig.getAuthenticationManager();
    }

    /**
     * CORS設定
     *
     * React(8080) からのアクセス許可
     *
     * @return CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:8080")
        );

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE")
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }

    /**
     * Security Filter Chain
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception 例外
     */
    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http
    ) throws Exception {

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
                                "/api/products/**"
                        ).permitAll()

                        // カテゴリー一覧取得
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/categories/**"
                        ).permitAll()

                        // 管理者のみ
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/products/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/products/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/products/**"
                        ).hasRole("ADMIN")

                        // USER / ADMIN
                        .requestMatchers(
                                "/api/orders/**",
                                "/api/cart/**"
                        ).hasAnyRole(
                                "USER",
                                "ADMIN"
                        )

                        // その他
                        .anyRequest().authenticated()
                )

                // JWT Filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}