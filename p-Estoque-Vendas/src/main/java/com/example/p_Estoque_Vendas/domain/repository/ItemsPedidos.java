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

import com.example.p_Estoque_Vendas.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedidos extends JpaRepository<ItemPedido, Integer> {
}