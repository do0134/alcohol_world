package com.example.user_service.utils.config;

import com.example.user_service.utils.filter.JwtTokenFilter;
import com.example.user_service.service.UserService;
import com.example.user_service.utils.error.CustomAuthenticationEntryPoint;
import com.example.user_service.utils.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${jwt.secret-key}")
    private String secretKey;


    /**
     * Spring security Filter
     * 6.1부터 현재 형태로 개발하는 것이 권장됨
     * @param httpSecurity -> 기본 객체
     * @return none
     * @throws Exception -> 내부 exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        System.out.println("ㅎㅇㅎㅇ");
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/api/v1/auth/signup").permitAll()
                            .requestMatchers("/api/v1/internal/**").permitAll()
                            .requestMatchers("/api/v1/auth/login").permitAll()
                            .requestMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                            .requestMatchers("/v2/api-docs").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()
                            .requestMatchers("/admin/**").hasRole("ADMIN");
                    authorize.anyRequest().permitAll();
                })
                .exceptionHandling(exceptionHandler -> exceptionHandler
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .logout((logout) -> {

                        }
                )
                .addFilterBefore(new JwtTokenFilter(secretKey, userService,redisTemplate), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Cors 설정을 해줄 Bean
     * Cors Configuration을 등록해서, Cors설정을 바꿔준다.
     * @return CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");

        corsSource.registerCorsConfiguration("/**",config);
        return corsSource;
    }
}
