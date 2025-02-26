package com.surveymaster;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserService userService;

    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/banner/**", "/register/**",
                                "/save-registration", "participant-view/**", "/save-response/**",
                                "/save-chart-selection", "/report").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/save-chart-selection")) // CSRF für POST-Request ignorieren
                .sessionManagement(session -> session.sessionFixation().none()) // Session erhalten
                .formLogin(login -> login
                        .loginPage("/login")
                        .successForwardUrl("/survey-admin")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
