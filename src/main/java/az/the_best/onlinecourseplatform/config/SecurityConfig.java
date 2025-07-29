package az.the_best.onlinecourseplatform.config;

import az.the_best.onlinecourseplatform.jwt.AuthEntryPoint;
import az.the_best.onlinecourseplatform.jwt.JWTAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final String PUBLIC = "/public/*";
    private final String COURSE_PUBLIC = "/course/*";
    private final String OTP = "/otp/*";
    private final String EMAIL = "/email/*";
    private final String USER = "/user/*";
    private final String TEACHER = "/teacher/*";
    private final String TEACHER_REQUEST = "/teacher_request/*";
    private final String ADMIN_RESPONSE = "/admin_response/*";

    private final String HTML = "/*.html";
    private final String CSS = "/css/*";
    private final String JS = "/js/*";
    private final String RESOURCE = "/resource/*";

    private final String WEB_MANIFEST = "/*.webmanifest";
    private final String PNG = "/*.png";

    private final AuthEntryPoint authEntryPoint;
    private final AuthenticationProvider authProvider;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(AuthEntryPoint authEntryPoint, AuthenticationProvider authProvider, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.authEntryPoint = authEntryPoint;
        this.authProvider = authProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizeRequests ->
                                authorizeRequests.requestMatchers(
                                        PUBLIC, USER, OTP, EMAIL,
                                        TEACHER,
                                        COURSE_PUBLIC,
                                        TEACHER_REQUEST,ADMIN_RESPONSE,

                                        HTML,CSS,JS,RESOURCE,
                                        WEB_MANIFEST, PNG
                                        )
                                        .permitAll()
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated()
                )
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(authEntryPoint)
                )
                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
