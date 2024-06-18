package com.example.p_Estoque_Vendas.rest.controller;

import com.example.p_Estoque_Vendas.domain.entity.Role;
import com.example.p_Estoque_Vendas.domain.repository.UserRepository;
import com.example.p_Estoque_Vendas.rest.dto.LoginRequest;
import com.example.p_Estoque_Vendas.rest.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class TokenController {

    private final JwtEncoder jwtEncoder;

    private final UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    public TokenController(JwtEncoder jwtEncoder,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        var user = userRepository.findByUsername(loginRequest.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder) ){
            throw new BadCredentialsException("user or password is invalid!");
        }

        var issuedAt = Instant.now();
        var expiresIn = 300L; // tempo de expiração em segundos

        var scopes = user.get().getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.get().getUserId().toString())
                .issuedAt(issuedAt) // Define o momento de emissão do token
                .expiresAt(issuedAt.plusSeconds(expiresIn)) // Define o momento de expiração do token
                .claim("scope",scopes)
                .build();



        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok( new LoginResponse(jwtValue, expiresIn));
    }

}


// _Anotação @PostMapping("/login"):_
//
// Esta anotação indica que este método trata solicitações POST para o endpoint /login.
// Ou seja, este método é responsável por lidar com as solicitações de login dos usuários.
//
//
// _Parâmetro loginRequest:_
//
// Este parâmetro é anotado com @RequestBody, o que significa que os dados da solicitação
// HTTP POST serão convertidos automaticamente para o objeto LoginRequest. O LoginRequest
// contém informações fornecidas pelo usuário durante o processo de login, como nome de
// usuário e senha.
//
//
// _Validação das credenciais:_
//
// O método verifica se as credenciais fornecidas são válidas. Ele tenta encontrar um
// usuário com o nome de usuário fornecido na solicitação usando o método findByUsername do
// UserRepository. Se não encontrar nenhum usuário ou se as credenciais estiverem
// incorretas, ele lança uma exceção BadCredentialsException, indicando que as
// credenciais são inválidas.
//
//
// _Geração do token JWT:_
//
// Se as credenciais forem válidas, o método prossegue com a geração do token JWT.
// Ele obtém a lista de escopos (roles) do usuário e as concatena em uma única string
// usando Collectors.joining(" ").
//
// Em seguida, constrói o conjunto de afirmações (JwtClaimsSet) do token JWT. As afirmações
// incluem o emissor do token (issuer), o assunto do token (subject), o momento de emissão
// do token (issuedAt), o momento de expiração do token (expiresAt) e os escopos (scope).
//
// O token JWT é então gerado usando o jwtEncoder, que foi injetado no construtor do
// controlador. O tempo de expiração do token é definido como 300 segundos (5 minutos).
//
// Por fim, o token JWT gerado é encapsulado em um objeto LoginResponse, juntamente com o
//  tempo de expiração, e retornado na resposta da solicitação POST.

