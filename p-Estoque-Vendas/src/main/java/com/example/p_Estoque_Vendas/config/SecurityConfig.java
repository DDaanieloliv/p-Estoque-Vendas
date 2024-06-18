package com.example.p_Estoque_Vendas.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig  {

    @Value("${jwt.public_key.pem}")
    private RSAPublicKey publicKey;

    @Value("${jwt.private_key.pem}")
    private RSAPrivateKey privateKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-ui.html"
                                ).permitAll()
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(privateKey).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }



}


// _@EnableWebSecurity_
//
// Indica ao Spring para habilitar a configuração de segurança da web.
//
//
// _@Configuration_
//
// Indica que esta classe é uma classe de configuração do Spring.
//
//
// _@EnableMethodSecurity_
//
// Habilita o suporte para anotações de segurança em métodos
// (como @PreAuthorize, @Secured, etc.).
//
//
// _Injeção de Dependência @Value_
//
// As variáveis publicKey e privateKey são injetadas a partir de propriedades do arquivo
// de configuração (application.properties ou application.yml).
//
// @Value("${jwt.public_key.pem}") injeta o valor da propriedade jwt.public_key.pem como
// RSAPublicKey.
//
// @Value("${jwt.private_key.pem}") injeta o valor da propriedade jwt.private_key.pem como
// RSAPrivateKey.
//
//
// _Método securityFilterChain_
//
// Este método cria a configuração de segurança para a aplicação.
// Define as regras de autorização para os endpoints da API:
//
//
// _Alguns endpoints são permitidos sem autenticação (permitAll()):
//  POST /users
//  POST /login
//
// Alguns endpoints do Swagger para acesso à documentação.
//
// Todos os outros endpoints precisam de autenticação (authenticated()).
//
// Desabilita o CSRF (Cross-Site Request Forgery).
//
// Configura o servidor de recursos OAuth2 JWT.
//
//
// _Método jwtEncoder_
//
// Cria um encoder JWT para codificar tokens JWT.
//
// Utiliza a chave pública (RSAPublicKey) e a chave privada (RSAPrivateKey) para assinar
// os tokens.
//
//
// _Método jwtDecoder_
//
// Cria um decoder JWT para decodificar tokens JWT.
// Utiliza a chave pública (RSAPublicKey) para validar e decodificar os tokens.
//
//
// _Método bCryptPasswordEncoder_
//
// Cria um BCryptPasswordEncoder para codificar senhas de forma segura.
//
//
//Resumo
//Este código Java configura a segurança da aplicação Spring com Spring Security e OAuth2 JWT.
//Define regras de autorização para os endpoints da API.
//Utiliza chaves RSA para codificar e decodificar tokens JWT.
//Desabilita o CSRF e configura a criação de sessão como STATELESS para a aplicação.