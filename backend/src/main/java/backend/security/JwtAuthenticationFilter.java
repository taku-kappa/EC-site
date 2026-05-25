package backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT認証フィルター
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Bearer Prefix
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenProvider tokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * フィルター処理
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {

            // JWT取得
            String jwt = resolveToken(request);

            // JWT存在 + JWT有効 + 未認証
            if (StringUtils.hasText(jwt)
                    && tokenProvider.validateToken(jwt)
                    && SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {

                // JWTからEmail取得
                String email =
                        tokenProvider.getUsernameFromJWT(jwt);

                // ユーザー情報取得
                UserDetails userDetails =
                        userDetailsService
                                .loadUserByUsername(email);

                // 認証情報生成
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                // SecurityContextへセット
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);

                log.debug(
                        "Authenticated user: {}",
                        email
                );
            }

        } catch (Exception ex) {
            log.error(
                    "Could not set user authentication in security context",
                    ex
            );
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Authorization Header から JWT取得
     *
     * @param request HttpServletRequest
     * @return JWT
     */
    private String resolveToken(
            HttpServletRequest request
    ) {

        String bearerToken =
                request.getHeader("Authorization");

        // Bearer Token チェック
        if (StringUtils.hasText(bearerToken)
                && bearerToken.startsWith(BEARER_PREFIX)) {

            return bearerToken.substring(
                    BEARER_PREFIX.length()
            );
        }

        return null;
    }
}