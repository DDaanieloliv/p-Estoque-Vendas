    package com.example.p_Estoque_Vendas.domain.entity;

    import com.example.p_Estoque_Vendas.rest.dto.LoginRequest;
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;
    import org.hibernate.annotations.CreationTimestamp;
    import org.springframework.security.crypto.password.PasswordEncoder;

    import java.time.Instant;
    import java.util.Set;
    import java.util.UUID;

    @Getter
    @Setter
    @Entity
    @Table(name = "tb_users")
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "user_id")
        private UUID userId;

        @Column(unique = true)
        private String username;

        @CreationTimestamp
        @Column(name = "instanciadoo")
        private Instant creationTimeStamp;

        private String password;

        @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JoinTable(
                name = "tb_user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id")
        )
        private Set<Role> roles;

        public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
            return passwordEncoder.matches(loginRequest.password(), this.password);
        }
    }


// _@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER):_ Esta anotação é usada
// para mapear o relacionamento muitos-para-muitos entre a entidade User e a entidade Role.
// Isso significa que um usuário pode ter várias funções e uma função pode ser atribuída
// a vários usuários.
//
//
// _cascade = CascadeType.ALL:_ Isso indica que todas as operações de persistência
// (por exemplo, salvar, atualizar, excluir) realizadas na entidade User também devem ser
// aplicadas às entidades relacionadas (Role). Isso garante que quando um usuário é salvo,
// suas funções também serão salvas.
//
//
// _fetch = FetchType.EAGER:_ Isso especifica que as funções de um usuário devem ser
// carregadas automaticamente sempre que um usuário for carregado. Ou seja, quando um
// usuário é recuperado do banco de dados, suas funções também serão recuperadas
// imediatamente.
//
//
// _@JoinTable:_ Esta anotação é usada para definir os detalhes da tabela de junção que
// será criada automaticamente pelo JPA para mapear o relacionamento muitos-para-muitos.
//
//
// _name = "tb_user_roles":_ Este é o nome da tabela de junção que será criada no banco de
// dados.
//
//
// _joinColumns:_ Especifica a coluna na tabela de junção que contém as chaves estrangeiras
// referentes à entidade User. No caso, a coluna user_id será usada para armazenar os IDs
// dos usuários.
//
//
// _inverseJoinColumns:_ Especifica a coluna na tabela de junção que contém as chaves
// estrangeiras referentes à entidade Role. No caso, a coluna role_id será usada para
// armazenar os IDs das funções.
//
//
// _private Set<Role> roles;:_ Esta linha declara um conjunto de objetos Role que
// representam as funções atribuídas a um usuário.
//
//
// _public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder)
// { ... }:_
// Este método verifica se as credenciais de login fornecidas no objeto LoginRequest
// correspondem às credenciais do usuário. Ele compara a senha fornecida com a senha
// armazenada no objeto User, após codificar a senha fornecida usando o passwordEncoder.
// Se as senhas corresponderem, o método retorna true, indicando que o login está correto.