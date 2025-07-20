package az.the_best.onlinecourseplatform.service;

import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.jwt.AuthRequest;
import az.the_best.onlinecourseplatform.jwt.AuthResponse;
import az.the_best.onlinecourseplatform.jwt.RefreshTokenRequest;
import az.the_best.onlinecourseplatform.jwt.RegisterRequest;

public interface IRestAuthService {

    BaseEntity<AuthResponse> register(RegisterRequest request);

    BaseEntity<AuthResponse> authenticate(AuthRequest request);

    BaseEntity<AuthResponse> refreshToken(RefreshTokenRequest request);
}
