package com.example.p_Estoque_Vendas.config;

import com.example.p_Estoque_Vendas.domain.entity.Role;
import com.example.p_Estoque_Vendas.domain.entity.User;
import com.example.p_Estoque_Vendas.domain.repository.RoleRepository;
import com.example.p_Estoque_Vendas.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private JdbcTemplate jdbcTemplate; // Adicione o JdbcTemplate

    public AdminUserConfig(RoleRepository roleRepository,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           JdbcTemplate jdbcTemplate) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        jdbcTemplate.update("INSERT IGNORE INTO tb_roles (role_id, name) VALUES (1, 'admin');");
        jdbcTemplate.update("INSERT IGNORE INTO tb_roles (role_id, name) VALUES (2, 'basic');");

        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());

        var  userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                (user) -> {
                    System.out.println("admin ja existe");
                },
                () -> {
                    var user =  new User();
                    user.setUsername("admin");
                    user.setPassword(passwordEncoder.encode("123"));
                    userRepository.save(user);
                    user.setRoles(Set.of(roleAdmin));
                }
        );
    }
}


// _Declaração da Classe:_ AdminUserConfig é uma classe de configuração Spring.
//
// _Anotação @Configuration:_ Indica que esta classe é uma classe de configuração do Spring.
//
// _Implementação CommandLineRunner:_ Implementa CommandLineRunner, o que significa que o
// método run será executado quando a aplicação Spring for iniciada.
//
//
// Propriedades:
//
// _RoleRepository roleRepository:_ Instância do RoleRepository, para interagir com os
// dados dos papéis/roles.
//
// _UserRepository userRepository:_ Instância do UserRepository, para interagir com os
// dados dos usuários.
//
// _BCryptPasswordEncoder passwordEncoder:_ Instância do BCryptPasswordEncoder, usado para
// codificar senhas de forma segura.
//
// _JdbcTemplate jdbcTemplate:_ Instância do JdbcTemplate, usado para executar operações SQL.


// _jdbcTemplate.update("INSERT IGNORE INTO tb_roles (role_id, name) VALUES (1, 'admin');");:
//
// Esta linha executa uma operação SQL para inserir um registro na tabela tb_roles.
// O INSERT IGNORE evita que uma exceção seja lançada se um registro com a mesma chave
// primária já existir. Ele tentará inserir o registro, mas se um registro com a mesma chave
// primária já existir, ele não fará nada.
// [tabela essa que caso nao seja populada ao iniciar a aplicação em um novo BD não será
// possivel dar roles as os users]
//
//
// _jdbcTemplate.update("INSERT IGNORE INTO tb_roles (role_id, name) VALUES (2, 'basic');");:
//
// Similar à linha anterior, esta linha insere um segundo registro na tabela tb_roles.
// [tabela essa que caso nao seja populada ao iniciar a aplicação em um novo BD não será
// possivel dar roles as os users]
//
// _var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());:_ Aqui, estamos
// tentando encontrar o objeto Role com o nome "admin" no repositório de roles
// (roleRepository). Este objeto representa a função de administrador.
//
//
// _var userAdmin = userRepository.findByUsername("admin");:_ Estamos tentando encontrar
// um usuário com o nome de usuário "admin" no repositório de usuários (userRepository).
//
//
// _userAdmin.ifPresentOrElse(...):_ Este método verifica se um usuário com o nome "admin"
// foi encontrado. Se for encontrado, executa o bloco de código dentro do método ifPresent,
// caso contrário, executa o bloco de código dentro do método orElse.
//
//
// _user.setUsername("admin");:_ Define o nome de usuário como "admin".
//
//
// _user.setPassword(passwordEncoder.encode("123"));:_ Define a senha do usuário como "123",
// mas antes, a senha é codificada usando o passwordEncoder.
//
//
// _userRepository.save(user);:_ Salva o novo usuário no banco de dados.
//
//
// _user.setRoles(Set.of(roleAdmin));:_ Define o conjunto de funções do usuário como o
// conjunto que contém apenas a função de administrador.