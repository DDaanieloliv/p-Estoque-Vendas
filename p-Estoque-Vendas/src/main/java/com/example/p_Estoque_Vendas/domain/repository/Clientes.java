package com.example.p_Estoque_Vendas.domain.repository;

/*
A decisão de criar métodos em interfaces JPA Repository ou em serviços depende da
responsabilidade e do propósito desses métodos.

Métodos em Interfaces JPA Repository:

    * Acesso a Dados:
        Interfaces JPA Repository são frequentemente usadas para definir métodos que
        fornecem acesso direto a operações de consulta no banco de dados.

    * Consultas Simples:
        Se o método envolve uma consulta simples baseada em atributos da entidade, e não
        requer lógica de negócios adicional, ele pode ser adequado para estar na interface
         JPA Repository.

    * Aproveitamento de Convenções de Nomenclatura:
        O Spring Data JPA segue convenções de nomenclatura e pode inferir consultas
        automaticamente com base no nome do método.


Métodos em Serviços:

    * Lógica de Negócios:
        Se o método envolve lógica de negócios significativa, como validações,
        transformações de dados, ou processamento complexo, é mais apropriado colocá-lo no
         serviço.

    * Orquestração de Operações:
        Serviços muitas vezes agem como uma camada de orquestração, chamando métodos do
        repositório e aplicando lógica de negócios para garantir a coesão e reutilização.

    * Encapsulamento de Complexidade:
        Se a operação é complexa e envolve várias chamadas ao repositório ou outras
        operações, é melhor encapsular essa complexidade em um serviço.


Quando Combinar:

    * Abstração Adequada:
        Considere se o método representa uma operação de nível mais alto, relacionada à
        lógica de negócios, ou se é uma operação de baixo nível, relacionada à manipulação
         direta dos dados.

    * Separação de Responsabilidades:
        Mantenha a separação de responsabilidades. Os serviços geralmente são mais
        apropriados para implementar a lógica de negócios, enquanto os repositórios são
        para acesso a dados.


Exemplo Prático:

    * Se você tem uma consulta simples que retorna entidades por um determinado critério,
     isso pode ser colocado diretamente na interface JPA Repository.

    * Se você tem uma operação que envolve a criação de várias entidades, aplicação de
    lógica de negócios complexa e interação com outros serviços, essa operação é mais
    adequada para um método no serviço.

Em geral, a ideia é manter a separação de responsabilidades e garantir que cada componente
em sua arquitetura (repositórios, serviços, controladores) cumpra seu papel de forma clara
 e concisa.
*/

import com.example.p_Estoque_Vendas.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository //Como 'Cliente' e 'Produto' são Entidades relativamente simples n teram 'serice'.
public interface Clientes extends JpaRepository<Cliente, Integer> {

    //Query methods.
    @Query(value = " select * from Cliente c where c.nome like '%:nome%' ",
            nativeQuery = true)
    List<Cliente> encontarPorNome(@Param("nome") String nome);

    @Query(value = " delete from Cliente c where c.nome = :nome ", nativeQuery = true)
    @Modifying
    void deleteByNome(@Param("nome") String nome);

    boolean existsByNome(String nome);

    @Query(" select c from Cliente c left join fetch c.pedidos where c.id = :id ")
    Cliente findClienteFetchPedido(@Param("id") Integer id );

    Optional<Cliente> findByCpf(String cpf);


// public interface Clientes extends JpaRepository<Cliente, Integer>:
//
//  Clientes é uma interface que estende JpaRepository. JpaRepository é uma interface
//  fornecida pelo Spring Data JPA que fornece métodos CRUD (Create, Read, Update, Delete)
//  padrão para a entidade Cliente. O primeiro parâmetro genérico (Cliente) é o tipo da
//  entidade e o segundo (Integer) é o tipo da chave primária.
//

// Query methods:
//
//  - '@Query':
//      Anotação usada para definir consultas personalizadas no banco de dados.
//
//  - 'encontrarPorNome(@Param("nome") String nome)':
//      Método que retorna uma lista de clientes cujo nome contenha a substring
//      especificada. Usa uma consulta nativa (nativeQuery = true) e recebe um parâmetro
//      nomeado (:nome) que é substituído pelo valor real no momento da execução.
//
//  - 'deleteByNome(@Param("nome") String nome)':
//      Método que exclui clientes com o nome especificado. Também usa uma consulta nativa
//      e recebe um parâmetro nomeado.
//
//  - 'existsByNome(String nome)':
//      Método que verifica se existe algum cliente com o nome especificado.
//

// @Modifying:
//
//  Anotação indicando que a consulta modifica o estado no banco de dados (operação de
//  exclusão neste caso).
//

// @Query("select c from Cliente c left join fetch c.pedidos where c.id = :id"):
//
//  Método que retorna um cliente com seus pedidos associados, utilizando uma junção à
//  esquerda (left join fetch). Isso é útil para carregar os pedidos associados a um
//  cliente na mesma consulta, evitando consultas adicionais.
//
//  Essa interface fornece métodos de consulta personalizados e aproveita os recursos do
//  Spring Data JPA, como a geração automática de consultas com base nos nomes dos métodos
//  e a capacidade de definir consultas personalizadas quando necessário.





}

