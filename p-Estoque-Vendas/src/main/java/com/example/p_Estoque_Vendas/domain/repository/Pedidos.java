package com.example.p_Estoque_Vendas.domain.repository;



import com.example.p_Estoque_Vendas.domain.entity.Cliente;
import com.example.p_Estoque_Vendas.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface Pedidos extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

    @Query("select p from Pedido p left join fetch p.itens where p.id =:id ")
    Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);
}

//  -'interface Pedidos':
//      Esta é uma interface Java. No contexto do Spring Data JPA, a declaração de
//      interfaces estendendo JpaRepository indica que você deseja herdar funcionalidades
//      básicas de um repositório JPA para a entidade Pedido.
//
//  - 'extends JpaRepository<Pedido, Integer>':
//      Isso indica que a interface Pedidos está estendendo a interface JpaRepository e está
//      sendo parametrizada com dois tipos:
//
//  - 'Pedido':
//      Indica a entidade que este repositório manipula (nesse caso, a entidade Pedido).
//
//  - 'Integer':
//      Indica o tipo do identificador da entidade (Pedido). Neste caso, parece que o
//      identificador é do tipo Integer.
//
//  - 'List<Pedido> findByCliente(Cliente cliente)':
//      Este é um método de consulta derivado do nome. O Spring Data JPA gera
//      automaticamente a implementação desse método com base no nome do método. Ele
//      retorna uma lista de pedidos associados a um cliente específico.
//
//  - 'findByCliente':
//      O Spring Data JPA interpreta esse método para buscar todos os pedidos onde o
//      atributo cliente seja igual ao parâmetro fornecido.
//
//  Essa interface, quando estendida, fornece um conjunto de métodos prontos para uso,
//  como métodos de CRUD (create, read, update, delete), bem como a capacidade de definir
//  consultas personalizadas usando convenções de nomenclatura. No caso, o método
//  findByCliente é um exemplo dessa capacidade de consulta derivada do nome. Ele permite
//  encontrar pedidos associados a um cliente específico sem a necessidade de escrever
//  explicitamente a consulta JPQL (Java Persistence Query Language) correspondente.
//  O Spring Data JPA gera a consulta automaticamente com base no nome do método.

