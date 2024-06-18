package com.example.p_Estoque_Vendas.service.impl;


import com.example.p_Estoque_Vendas.domain.entity.Role;
import com.example.p_Estoque_Vendas.domain.entity.User;
import com.example.p_Estoque_Vendas.domain.repository.RoleRepository;
import com.example.p_Estoque_Vendas.domain.repository.UserRepository;
import com.example.p_Estoque_Vendas.rest.dto.CreateUserDTO;
import com.example.p_Estoque_Vendas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.p_Estoque_Vendas.rest.dto.CreateUserDTO;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceimpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public void optionCreation(CreateUserDTO dto, Jwt jwt) {

        // Verifica se o token possui o escopo 'admin'
        if (jwt.getClaimAsStringList("scope").contains("admin")) {
            Integer roleNumber = dto.getRole_number();
            if (roleNumber == null || roleNumber != 1) {
                // Caso o role_number seja null ou 0, atribuir um papel padrão
                var basicRole = roleRepository.findByName(Role.Values.BASIC.name());
                var userFromDb = userRepository.findByUsername(dto.getUsername());
                if (userFromDb.isPresent()) {
                    throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
                }
                var user = new User();
                user.setUsername(dto.getUsername());
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
                user.setRoles(Set.of(basicRole));
                userRepository.save(user);
            } else if (roleNumber == 1) {
                var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());
                var userFromDb = userRepository.findByUsername(dto.getUsername());
                if (userFromDb.isPresent()) {
                    throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
                }
                var user = new User();
                user.setUsername(dto.getUsername());
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
                user.setRoles(Set.of(roleAdmin));
                userRepository.save(user);
            }

        } else {

            var basicRole = roleRepository.findByName(Role.Values.BASIC.name());

            var userFromDb = userRepository.findByUsername(dto.getUsername());
            if (userFromDb.isPresent()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
            }

            var user = new User();
            user.setUsername(dto.getUsername());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setRoles(Set.of(basicRole));

            userRepository.save(user);

        }

    }
}

//  Declaração da Classe: UserServiceimpl é a classe de serviço que implementa a
//  interface UserService.
//
// Anotações:
//
// @Service: Indica que esta classe é um componente de serviço Spring.
//
// @RequiredArgsConstructor: Anotação do Lombok que gera automaticamente um construtor com todos os campos marcados como final.
//
//
// Propriedades:
//
// userRepository: Instância do UserRepository, que será usada para interagir com os
// dados dos usuários.
//
// roleRepository: Instância do RoleRepository, para interagir com os dados dos
// papéis/roles.
//
// passwordEncoder: Instância do BCryptPasswordEncoder, usado para codificar senhas de
// forma segura.

//  Aqui está o método optionCreation, que é a parte principal deste serviço.
//
// Parâmetros:
//
// # CreateUserDTO dto: É um objeto que contém os dados necessários para criar um novo
// usuário, como nome de usuário e senha.
//
// # Jwt jwt: É o token JWT que contém informações sobre o usuário que está fazendo a
// requisição.
//
//
// Lógica do Método:
//
//
// # Verificação de Escopo Admin:
//
//  [O método começa verificando se o token JWT possui o escopo "admin".]
//
//  [Se sim, ele prossegue para verificar o roleNumber do DTO.]
//
//  [Se roleNumber for null ou diferente de 1, ele atribui um papel básico ao usuário e
//  salva no banco de dados.]
//
//  [Se roleNumber for 1, ele atribui o papel de administrador ao usuário e também salva
//  no banco de dados.]
//
// # Caso Contrário (Não Admin):
//
//  [Se o token não tiver o escopo "admin", ele simplesmente atribui um papel básico
//  ao usuário e salva no banco de dados.]
//
// # Verificação de Usuário Existente:
//
//  [Antes de criar um novo usuário, ele verifica se já existe um usuário com o mesmo nome
// de usuário (username).]
//
//  [Se existir, lança uma ResponseStatusException com código
//  HttpStatus.UNPROCESSABLE_ENTITY, indicando que a entidade não pode ser processada.]
//
// # Criação do Novo Usuário:
//
//  [Se tudo estiver correto, um novo objeto User é criado.]
//  [O nome de usuário e senha do DTO são definidos no novo usuário.]
//  [A senha é criptografada usando o BCryptPasswordEncoder.]
//  [O papel correspondente é atribuído ao usuário com base no roleNumber ou padrão.]
//
//Resumo
//Este serviço UserServiceimpl é responsável pela criação de novos usuários em um sistema.
//Ele verifica se o usuário que está fazendo a requisição tem o escopo de "admin" no token JWT.
//Com base nisso, decide se o novo usuário será um administrador ou terá um papel básico.
//Também verifica se já existe um usuário com o mesmo nome de usuário antes de criar um novo.
//As senhas são armazenadas no banco de dados após serem criptografadas com BCrypt.