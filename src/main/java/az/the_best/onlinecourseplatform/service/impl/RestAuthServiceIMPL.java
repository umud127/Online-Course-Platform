package az.the_best.onlinecourseplatform.service.impl;

import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.entities.roles.User;
import az.the_best.onlinecourseplatform.exception.MessageType;
import az.the_best.onlinecourseplatform.jwt.*;
import az.the_best.onlinecourseplatform.repo.RestUserRepo;
import az.the_best.onlinecourseplatform.security.RoleName;
import az.the_best.onlinecourseplatform.service.IRestAuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RestAuthServiceIMPL implements IRestAuthService {

    private final JWTService jwtService;
    private final RestUserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public RestAuthServiceIMPL(JWTService jWTService, RestUserRepo restUserRepo, BCryptPasswordEncoder passwordEncoder) {
        this.jwtService = jWTService;
        this.userRepo = restUserRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public BaseEntity<AuthResponse> register(RegisterRequest request) {
        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(RoleName.ROLE_USER);

        userRepo.save(user);

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return BaseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    @Override
    public BaseEntity<AuthResponse> authenticate(AuthRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return BaseEntity.notOk(MessageType.USER_NOT_FOUND, "Email tapılmadı");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return BaseEntity.notOk(MessageType.INVALID_PASSWORD, "Şifrə yalnışdır");
        }

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        AuthResponse authResponse = new AuthResponse(accessToken, refreshToken);

        return BaseEntity.ok(authResponse);
    }

    @Override
    public BaseEntity<AuthResponse> refreshToken(RefreshTokenRequest request) {

        String refreshToken = request.getRefreshToken();

        if (refreshToken == null || refreshToken.isEmpty()) {
            return BaseEntity.notOk(MessageType.INVALID_REQUEST, "Refresh token boşdur");
        }

        boolean isTokenExpired = jwtService.isTokenExpired(refreshToken);

        if (isTokenExpired) {
            return BaseEntity.notOk(MessageType.INVALID_TOKEN, "Refresh token vaxtı bitib");
        }

        String username;
        try {
            username = jwtService.extractUsername(refreshToken);
        } catch (Exception e) {
            return BaseEntity.notOk(MessageType.INVALID_TOKEN, "Refresh token etibarsızdır");
        }

        User user = userRepo.findByEmail(username)
                .orElse(null);

        if (user == null) {
            return BaseEntity.notOk(MessageType.USER_NOT_FOUND, "İstifadəçi tapılmadı");
        }

        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        AuthResponse authResponse = new AuthResponse(newAccessToken, newRefreshToken);

        return BaseEntity.ok(authResponse);
    }
}
