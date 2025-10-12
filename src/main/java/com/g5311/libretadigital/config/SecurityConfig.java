package com.g5311.libretadigital.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        @Value("${auth0.domain}")
        private String issuer;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/public/**").permitAll()
                                                .requestMatchers("/h2-console/**").permitAll()
                                                .requestMatchers("/api/codes/validate/**").hasRole("PROFESOR")
                                                .anyRequest().authenticated())
                                .csrf(csrf -> csrf
                                                .ignoringRequestMatchers("/h2-console/**") // desactiva CSRF solo para
                                                                                           // H2
                                )
                                .headers(headers -> headers
                                                .frameOptions(frame -> frame.disable()) // permite iframes (necesario
                                                                                        // para H2 console)
                                )
                                .oauth2ResourceServer(oauth2 -> oauth2
                                                .jwt(jwt -> jwt.jwtAuthenticationConverter(
                                                                jwtAuthenticationConverter())));

                return http.build();
        }

        @Bean
        public JwtAuthenticationConverter jwtAuthenticationConverter() {
                JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
                authoritiesConverter.setAuthorityPrefix("ROLE_"); // necesario para hasRole
                authoritiesConverter.setAuthoritiesClaimName("https://sirca.com/roles"); // tu namespace de Auth0

                JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
                converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
                return converter;
        }

        @Bean
        public JwtDecoder jwtDecoder() {

                NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer + '/');

                // ✅ Crear un validador con tolerancia de 5 minutos
                OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer + '/');
                OAuth2TokenValidator<Jwt> withClockSkew = new DelegatingOAuth2TokenValidator<>(
                                new JwtTimestampValidator(Duration.ofMinutes(5)), // tolerancia al desfase
                                withIssuer);

                jwtDecoder.setJwtValidator(withClockSkew);

                return jwtDecoder;
        }

}
