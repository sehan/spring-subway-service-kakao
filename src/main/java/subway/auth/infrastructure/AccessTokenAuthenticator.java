package subway.auth.infrastructure;

import org.springframework.web.servlet.HandlerInterceptor;
import subway.auth.application.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class AccessTokenAuthenticator implements HandlerInterceptor {

    private final AuthService authService;

    public AccessTokenAuthenticator(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = AuthorizationExtractor.extract(request);
        if(Objects.isNull(token)) return false;

        authService.validateToken(token);
        return true;
    }
}
