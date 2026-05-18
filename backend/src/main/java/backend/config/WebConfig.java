package backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            /**
             * CORS（クロスオリジン通信）に関する具体的なルールを、registry に定義
             *
             * @param registry
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // すべてのURL（API）に対してこのルールを適用する
                registry.addMapping("/**")
                        // 下記URL（オリジン）からのアクセスだけを信用して許可
                        .allowedOrigins("http://localhost:8080")
                        // 下記の通信方法（HTTPメソッド）を許可
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}
