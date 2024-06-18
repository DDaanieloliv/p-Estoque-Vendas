package com.example.p_Estoque_Vendas.domain.entity;

/*
Essas anotações estão associadas ao projeto Lombok, que é uma biblioteca do Java
utilizada para reduzir a verbosidade do código, eliminando a necessidade de escrever
determinados métodos e construtores padrão. Vamos entender cada uma delas:

@NoArgsConstructor:

    Esta anotação gera automaticamente um construtor sem argumentos (construtor padrão)
    para a classe. Um construtor sem argumentos é útil em situações em que você precisa
    criar instâncias da classe sem fornecer valores iniciais para os campos.

@AllArgsConstructor:

    Essa anotação gera automaticamente um construtor com todos os argumentos para a
    classe. Isso significa que um construtor será gerado com um parâmetro para cada
    campo na classe. Isso é útil quando você deseja inicializar todos os campos ao criar
    uma instância da classe.

@Data:

    Esta anotação é um atalho fornecido pelo Lombok que combina várias outras anotações,
    incluindo @ToString, @EqualsAndHashCode, @Getter, @Setter, e @RequiredArgsConstructor.
    O @Data automaticamente gera métodos como toString(), equals(), hashCode(), bem como
    os métodos getter e setter para todos os campos da classe.
*/

import com.example.p_Estoque_Vendas.domain.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "pedido")
public class Pedido extends RepresentationModel<Cliente> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(name = "efetuado")
    private Instant creationTimeStamp;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
//    @Column(name = "cliente_id")
    private Cliente cliente ;
    // @JoinColum - Chave estrangeira da tabela 'Cliente'

//  Quando você define um relacionamento @ManyToOne, a anotação @JoinColumn é geralmente
//  suficiente para mapear a coluna de chave estrangeira no lado "muitos" desse
//  relacionamento. A anotação @Column(name = "cliente_id") pode ser redundante e causar
//  confusão no Hibernate/JPA.

    @Column(name = "data_pedido")
    private LocalDate data_pedido;

    @Column(name = "total", precision = 20, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedido status;

    // @Enumerated(EnumType.STRING): Essa anotação é usada para mapear uma enumeração (enum)
    // em um banco de dados relacional. Ela especifica como os valores da enumeração devem
    // ser armazenados no banco de dados. No caso de EnumType.STRING, os valores da enumeração
    // serão armazenados como strings no banco de dados.
    //
    // @Column(name = "status"): Essa anotação é usada para personalizar as propriedades da
    // coluna do banco de dados associada a um campo da entidade. Neste caso, a propriedade
    // status será mapeada para uma coluna chamada "status". O parâmetro name especifica o
    // nome da coluna no banco de dados.
    //
    // Então, a combinação dessas anotações está indicando que a propriedade status de uma
    // entidade será mapeada para uma coluna chamada "status" no banco de dados, e os
    // valores dessa propriedade (que é uma enumeração) serão armazenados como strings.
    // Isso é útil quando você tem uma enumeração em Java representando diferentes estados
    // (como status de um pedido) e deseja persistir esses estados no banco de dados de uma
    // forma legível e compreensível.

    @OneToMany( mappedBy = "pedido")
    private List<ItemPedido> itens;


//    public List<ItemPedido> getItens() {
//        return itens;
//    }
//
//    public void setItens(List<ItemPedido> itens) {
//        this.itens = itens;
//    }




//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Cliente getCliente() {
//        return cliente;
//    }
//
//    public void setCliente(Cliente cliente) {
//        this.cliente = cliente;
//    }
//
//    public LocalDate getData_pedido() {
//        return data_pedido;
//    }
//
//    public void setData_pedido(LocalDate data_pedido) {
//        this.data_pedido = data_pedido;
//    }
//
//    public BigDecimal getTotal() {
//        return total;
//    }
//
//    public void setTotal(BigDecimal total) {
//        this.total = total;
//    }
//
//
//    @Override
//    public String toString() {
//        return "Pedido{" +
//                "id=" + id +
//                ", data_Pedido=" + data_pedido +
//                ", total=" + total +
//                '}';
//    }
}


//@Entity e @Table(name = "pedido"):
//
//  - '@Entity':
//      Indica que a classe Pedido é uma entidade JPA, ou seja, será mapeada para uma tabela
//      no banco de dados.
//
//  - '@Table(name = "pedido")':
//      Especifica o nome da tabela no banco de dados associada a esta entidade. Neste caso,
//      a tabela se chamará "pedido".


//@Id e @GeneratedValue(strategy = GenerationType.IDENTITY):
//
//  - '@Id':
//      Indica que o campo id é a chave primária da tabela.
//
//  - '@GeneratedValue(strategy = GenerationType.IDENTITY)':
//      Essa estratégia indica que a geração de valores de chave primária deve ser delegada
//      ao banco de dados. Geralmente, ela é usada quando o banco de dados suporta a
//      identidade automática, como no caso de colunas autoincrementadas. Quando uma nova
//      linha é inserida, o banco de dados automaticamente atribui um valor único à coluna
//      de chave primária.
//
//      @Id marca o campo id como a chave primária da entidade, e
//      @GeneratedValue(strategy = GenerationType.IDENTITY) especifica que os valores para
//      essa chave primária serão gerados pelo banco de dados usando a identidade
//      automática.
//


//Relacionamento @ManyToOne e @JoinColumn:
//
//  - '@ManyToOne':
//      Indica um relacionamento muitos para um entre a entidade Pedido e a entidade
//      Cliente. Isso significa que muitos pedidos podem estar associados a um único
//      cliente.
//
//  - '@JoinColumn(name = "cliente_id")':
//      Especifica a coluna na tabela do banco de dados que será usada como chave
//      estrangeira para representar o relacionamento. No caso, a coluna cliente_id é usada
//      para referenciar a chave primária na tabela Cliente.
//
//  - '@ManyToOne':
//      Esta anotação é usada para mapear um relacionamento muitos-para-um no banco de
//      dados. Isso significa que muitos registros da entidade atual (Cliente neste caso)
//      podem se relacionar com um único registro da entidade referenciada (vamos chamar
//      de Pedido aqui, pois geralmente é assim que se usa em sistemas de pedidos).
//
//  - '@JoinColumn(name = "cliente_id")':
//      Esta anotação é usada para especificar a coluna que será usada como chave
//      estrangeira na tabela da entidade atual (Pedido, por exemplo). Isso implica que
//      haverá uma coluna chamada cliente_id na tabela Pedido que armazenará as chaves
//      estrangeiras, referenciando a tabela Cliente. A coluna cliente_id na tabela Pedido
//      indica qual cliente está associado a esse pedido.
//
//  - 'private Cliente cliente;':
//      Este é o campo que representa o lado "muitos" do relacionamento. Cada instância de
//      Pedido terá uma referência a um objeto Cliente. Isso é feito através da associação
//      com o campo cliente. Por exemplo, se você recuperar um pedido do banco de dados,
//      poderá acessar o cliente associado a esse pedido usando pedido.getCliente().
//
//
//      No geral, este trecho de código modela um relacionamento muitos-para-um entre as
//      entidades Pedido e Cliente no contexto de um sistema onde um cliente pode ter
//      vários pedidos, mas um pedido está associado a apenas um cliente. O campo cliente_id
//      é a chave estrangeira que estabelece essa associação no banco de dados.


//Atributos:
//
//  A classe possui atributos para representar o id do pedido, o cliente associado ao
//  pedido, a data_pedido que indica quando o pedido foi feito, o total do pedido e uma
//  lista de itens associados ao pedido.
//


//Mapeamento de Relacionamento @OneToMany:
//
//  - '@OneToMany(mappedBy = "pedido")':
//      Indica um relacionamento um para muitos entre a entidade Pedido e a entidade
//      ItemPedido. Isso significa que um pedido pode ter vários itens associados.
//
//  - 'mappedBy = "pedido"':
//      Especifica o nome do atributo na classe ItemPedido que mantém o relacionamento
//      inverso. Neste caso, é o atributo pedido na classe ItemPedido.
//
//
//  - '@OneToMany(mappedBy = "pedido")':
//      Essa anotação indica que existe uma relação de um-para-muitos entre a entidade
//      Pedido e a entidade ItemPedido. Isso significa que um pedido pode ter vários
//      itens associados a ele. O parâmetro mappedBy = "pedido" especifica que o
//      mapeamento inverso desse relacionamento é mantido pelo campo "pedido" na entidade
//      ItemPedido.
//
//  - 'private List<ItemPedido> itens;':
//      Este é o campo que representa a coleção de itens associados a um pedido. Pode ser
//      uma lista, conjunto (Set), ou outra coleção dependendo da necessidade. Neste caso,
//      é uma lista de objetos ItemPedido.
//
//  - 'public List<ItemPedido> getItens()':
//      Um método getter que retorna a lista de itens associados a esse pedido.
//
//  - 'public void setItens(List<ItemPedido> itens)':
//      Um método setter que permite definir a lista de itens associados a esse pedido.
//
//  Resumindo, esse trecho de código permite modelar a relação entre um pedido (Pedido)
//  e seus itens (ItemPedido). Cada instância de Pedido terá uma lista de itens associados
//  a ela, e essa associação é mantida pelo campo "pedido" nas instâncias correspondentes
//  de ItemPedido. Isso é útil para representar casos em que um pedido pode conter vários
//  itens.


//Atributo itens e Métodos Getters e Setters:
//
//  A classe possui um atributo itens que representa a lista de itens associados a um pedido.
//
//  Métodos getters e setters são fornecidos para acessar e modificar os atributos da
//  classe.
//

//Método toString():
//
//  Um método toString() é sobrescrito para fornecer uma representação de string
//  significativa da instância da classe. Este método retorna uma string formatada
//  contendo o id, data do pedido e total do pedido.
//
//  Essa classe representa um pedido em um sistema de vendas, mantendo informações sobre o
//  cliente associado, a data do pedido, o total e a lista de itens associados ao pedido.
//  Os relacionamentos são modelados usando as anotações JPA para representar o
//  mapeamento objeto-relacional.

