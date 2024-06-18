package com.example.p_Estoque_Vendas.rest.controller;

import com.example.p_Estoque_Vendas.domain.repository.RoleRepository;
import com.example.p_Estoque_Vendas.domain.repository.UserRepository;
import com.example.p_Estoque_Vendas.rest.dto.CreateUserDTO;
import com.example.p_Estoque_Vendas.rest.dto.FeedDto;
import com.example.p_Estoque_Vendas.rest.dto.FeedItemDto;
import com.example.p_Estoque_Vendas.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/users")
    @Transactional
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDTO dto, @AuthenticationPrincipal Jwt jwt) {

        userService.optionCreation(dto, jwt);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<FeedDto> listUsers(@AuthenticationPrincipal Jwt jwt
             , @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int pageSize) {
        List<String> scopes = jwt.getClaimAsStringList("scope");

        if (scopes == null || !scopes.contains("admin")) {
            throw new AccessDeniedException("Acesso negado");
        }

        var users = userRepository
                .findAll(PageRequest.of(page, pageSize,
                        Sort.Direction.DESC, "creationTimeStamp"))
                .map(user -> new FeedItemDto(user.getUserId(), user.getUsername(),
                        user.getCreationTimeStamp(), user.getPassword(), user.getRoles()));
        return ResponseEntity.ok(new FeedDto( users.getContent()
                , page, pageSize, users.getTotalPages(), users.getTotalElements()));
    }


}



// Anotações:
//
// _@GetMapping("/users"):_ Define que este método trata solicitações GET para o endpoint
// /users.
//
//
// _@PreAuthorize("hasAuthority('SCOPE_admin')"):_ Especifica que apenas usuários com a
// autoridade SCOPE_admin têm permissão para acessar este endpoint. Isso é usado para
// controle de acesso baseado em autorização.
//
//
// _@SecurityRequirement(name = "bearerAuth"):_ Indica que este endpoint requer autenticação
// com o esquema de autenticação Bearer. Essa anotação faz parte da especificação OpenAPI
// (anteriormente conhecida como Swagger) e fornece informações sobre os requisitos de
// segurança da operação.
//
//
// Parâmetros:
//
// _@AuthenticationPrincipal Jwt jwt:_ Este parâmetro representa o token JWT (JSON Web
// Token) usado para autenticar a solicitação. A anotação @AuthenticationPrincipal indica
// que o objeto principal autenticado (nesse caso, o token JWT) deve ser injetado neste
// parâmetro.
//
//
// _@RequestParam(defaultValue = "0") int page:_ Este parâmetro indica o número da página
// que será retornada na resposta. O valor padrão é 0.
//
//
// _@RequestParam(defaultValue = "10") int pageSize:_ Este parâmetro indica o tamanho da
// página, ou seja, o número máximo de itens que serão retornados em uma página. O valor
// padrão é 10.
//
//
// Controle de Acesso:
//
// A lista de escopos do JWT é verificada para garantir que o usuário possua a autoridade
// SCOPE_admin. Se o usuário não tiver essa autoridade, uma exceção AccessDeniedException
// será lançada, indicando que o acesso foi negado.
//
//
// Consulta ao Banco de Dados:
//
// A consulta ao banco de dados é realizada utilizando o método userRepository.findAll().
// A paginação é configurada com base nos parâmetros page e pageSize, e os resultados são
// ordenados pelo carimbo de data/hora de criação (creationTimeStamp) em ordem decrescente.
//
// Os resultados são mapeados para objetos FeedItemDto, que contêm informações resumidas
// sobre os usuários (ID, nome de usuário, carimbo de data/hora de criação, senha e
// funções).
//
//
// Resposta:
//
// Os resultados paginados são encapsulados em um objeto FeedDto, que contém informações
// adicionais sobre a paginação (número da página atual, tamanho da página, total de
// páginas e total de elementos).
//
// A resposta é enviada como uma entidade ResponseEntity com status OK (200) e o corpo
//  contendo o objeto FeedDto.