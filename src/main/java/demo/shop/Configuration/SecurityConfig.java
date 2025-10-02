package demo.shop.Configuration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private static final String[] PUBLIC_URLS = {
      "/api/authentication/login/**",
      "/api/authentication/introspect/**",
      "/api/users/register/**",
      "/api/authentication/introspect/**",
      "/api/authentication/logout/**",
      "/api/authentication/refreshToken/**",
      "/api/category/createCategory/**",
      "/api/category/deleteCategory/{id}/**",
  };

  private static final String[] GET_URLS = {
      "/api/products/**",
      "/api/products/getProducts/**",
  };

  @Value("${jwt.SIGNER_KEY}")
  private String signerKey;

  @Autowired
  private CustomeJwtDecoder customeJwtDecoder; // de xac thuc token

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeHttpRequests(request -> request
            .requestMatchers(HttpMethod.POST, PUBLIC_URLS).permitAll()
            .requestMatchers(HttpMethod.GET, GET_URLS).permitAll()
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow preflight
            .anyRequest().authenticated()
        );

    httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(customeJwtDecoder)
        .jwtAuthenticationConverter(jwtConverter()))
        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
    );

    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource())); // Fix này
    return httpSecurity.build();
  }

  JwtAuthenticationConverter jwtConverter(){
    JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
    converter.setAuthorityPrefix("ROLE_");
    log.warn(converter.toString());
    JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
    jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
    return jwtConverter;
  }

  @Bean
  PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder(10);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() { // Thêm method này
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOriginPattern("http://localhost:3000"); // Thay đổi này
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
