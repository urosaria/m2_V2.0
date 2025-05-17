package jungjin.config;

import jakarta.servlet.http.HttpServletResponse;
import jungjin.user.service.UserService;
import jungjin.user.service.UserV2Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfig {

    public static final String REMEMBER_ME_KEY = "REMEBMER_ME_KEY";
    public static final String REMEMBER_ME_COOKIE_NAME = "REMEMBER_ME_COOKIE";

    private final UserV2Service userDetailsService;

    public WebSecurityConfig(UserV2Service userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices services = new TokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService);
        services.setAlwaysRemember(true);
        services.setTokenValiditySeconds(2678400); // 31 days
        services.setCookieName(REMEMBER_ME_COOKIE_NAME);
        return services;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/**.html", "/user/findId", "/user/register",
//                                "/admin/login", "/js/**", "/css/**", "/images/**",
//                                "/resources/**", "/loginError", "/api/**", "/api/admin/**"
//                        ).permitAll()
//                        .anyRequest().authenticated()
//                )
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(new CustomAuthenticationSuccess())
                        .failureHandler(new CustomAuthenticationFailure())
                        .permitAll()
                )
                .rememberMe(remember -> remember
                        .key(REMEMBER_ME_KEY)
                        .rememberMeServices(rememberMeServices())
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .deleteCookies("JSESSIONID", REMEMBER_ME_COOKIE_NAME)
                        .invalidateHttpSession(true)
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/403")
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                        )
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        //configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        //configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}