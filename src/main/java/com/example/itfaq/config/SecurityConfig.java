package com.example.itfaq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        // Для админки — только роль ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Открываем доступ к статике и публичным страницам
                        .requestMatchers("/", "/login", "/register", "/css/**", "/styles/**", "/static/js/**", "/js/**", "/images/**", "/public/**", "/faq/**").permitAll()
                        // Все остальные GET-запросы разрешены (если нужно)
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
                        // Все остальные запросы требуют авторизации
                        .anyRequest().authenticated()
                )
                // Обработка ошибок авторизации для AJAX
                .exceptionHandling(exception -> exception
                        .defaultAuthenticationEntryPointFor(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                                request -> "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))
                        )
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}