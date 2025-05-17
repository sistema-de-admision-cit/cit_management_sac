package cr.co.ctpcit.citsacbackend.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import cr.co.ctpcit.citsacbackend.logic.services.auth.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;
import org.thymeleaf.spring6.ISpringTemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class RestSecurityConfig implements WebMvcConfigurer {
  private final UserDetailsServiceImpl userDetailsService;
  private final PasswordEncoder passwordEncoder;
  @Value("${jwt.public.key}")
  RSAPublicKey key;
  @Value("${jwt.private.key}")
  RSAPrivateKey priv;

  @Bean
  @Order(0)
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // @formatter:off
    http
        .securityMatcher("/api/**") // Aplica a lo que no esté en la cadena de Order(1)
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/api/inscription/**").permitAll()
            .anyRequest().authenticated()
        )
        .cors((cors) -> cors
            .configurationSource(apiConfigurationSource()))
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(Customizer.withDefaults())
        .oauth2ResourceServer(auth -> auth.jwt(Customizer.withDefaults()))
        .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling((exceptions) -> exceptions
            .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
            .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
        );
    // @formatter:on
    return http.build();
  }
    /**
     * Configuración de seguridad para el acceso a recursos estáticos.
     * Permite el acceso a archivos estáticos y desactiva CSRF.
     *
     * @param http la configuración de seguridad HTTP
     * @return la cadena de filtros de seguridad configurada
     * @throws Exception si ocurre un error durante la configuración
     */
  @Bean
  @Order(1) // Se evalúa después de la cadena de API (Order 0)
  public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .securityMatcher("/**") // Aplica a lo que no esté en la cadena de Order(0)
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/", "/index.html", "/static/**", "/*.js", "/*.css", "/*.ico", "/assets/**").permitAll()
            .anyRequest().permitAll()
        )
        .csrf(AbstractHttpConfigurer::disable);

    return http.build();
  }

  private UrlBasedCorsConfigurationSource apiConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("*"));
    configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("authorization", "Content-Type"));
    configuration.setExposedHeaders(List.of("location"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(this.key).build();
  }

  @Bean
  JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }

  @Bean
  public DaoAuthenticationProviderCstm daoProvider() {
    DaoAuthenticationProviderCstm authProvider =
        new DaoAuthenticationProviderCstm(passwordEncoder, userDetailsService);
    System.out.println();
    return authProvider;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    this.serveDirectory(registry, "/", "classpath:/static/");
  }

  private void serveDirectory(ResourceHandlerRegistry registry, String endpoint, String location) {
    String[] endpointPatterns = endpoint.endsWith("/")
        ? new String[]{endpoint.substring(0, endpoint.length() - 1), endpoint, endpoint + "**"}
        : new String[]{endpoint, endpoint + "/", endpoint + "/**"};
    registry
        .addResourceHandler(endpointPatterns)
        .addResourceLocations(location.endsWith("/") ? location : location + "/")
        .resourceChain(false)
        .addResolver(new PathResourceResolver() {
          @Override
          public Resource resolveResource(HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {
            Resource resource = super.resolveResource(request, requestPath, locations, chain);
            if (nonNull(resource)) {
              return resource;
            }
            return super.resolveResource(request, "/index.html", locations, chain);
          }
        });
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOriginPatterns("*");
  }

  @Bean
  public ViewResolver viewResolver(ISpringTemplateEngine templateEngine) {
    ThymeleafViewResolver resolver = new ThymeleafViewResolver();
    resolver.setTemplateEngine(templateEngine);
    resolver.setCharacterEncoding("UTF-8");
    return resolver;
  }

  @Bean
  public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
    SpringTemplateEngine engine = new SpringTemplateEngine();
    engine.setEnableSpringELCompiler(true);
    engine.setTemplateResolver(templateResolver);
    return engine;
  }

  @Bean
  public SpringResourceTemplateResolver templateResolver() {
    SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
    resolver.setPrefix("classpath:/static/");
    resolver.setSuffix(".html");
    resolver.setTemplateMode(TemplateMode.HTML);
    return resolver;
  }
}

