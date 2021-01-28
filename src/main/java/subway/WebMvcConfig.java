package subway;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import subway.auth.application.AuthService;
import subway.auth.infrastructure.AccessTokenAuthenticator;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private AuthService authService;

    public WebMvcConfig(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessTokenAuthenticator(authService))
                .addPathPatterns("/members/me");
    }
}
