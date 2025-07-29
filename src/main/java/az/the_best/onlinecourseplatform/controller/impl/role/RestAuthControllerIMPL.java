package az.the_best.onlinecourseplatform.controller.impl.role;

import az.the_best.onlinecourseplatform.controller.interfaces.role.IRestAuthController;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.jwt.AuthRequest;
import az.the_best.onlinecourseplatform.jwt.AuthResponse;
import az.the_best.onlinecourseplatform.jwt.RefreshTokenRequest;
import az.the_best.onlinecourseplatform.jwt.RegisterRequest;
import az.the_best.onlinecourseplatform.service.interfaces.role.IRestAuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/public")
public class RestAuthControllerIMPL implements IRestAuthController {

    private final IRestAuthService authService;

    public RestAuthControllerIMPL(IRestAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Override
    public BaseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/authenticate")
    @Override
    public BaseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return authService.authenticate(request);
    }

    @PostMapping("/refresh_token")
    @Override
    public BaseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }
}
