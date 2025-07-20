package az.the_best.onlinecourseplatform.controller;

import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.jwt.AuthRequest;
import az.the_best.onlinecourseplatform.jwt.RefreshTokenRequest;
import az.the_best.onlinecourseplatform.jwt.RegisterRequest;
import az.the_best.onlinecourseplatform.jwt.AuthResponse;

public interface IRestAuthController {

    BaseEntity<AuthResponse> register(RegisterRequest request);

    BaseEntity<AuthResponse> authenticate(AuthRequest request);

    BaseEntity<AuthResponse> refreshToken(RefreshTokenRequest request);
}
