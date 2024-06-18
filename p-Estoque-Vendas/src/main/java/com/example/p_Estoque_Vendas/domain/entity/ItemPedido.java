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


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "item_pedido")
public class ItemPedido /*extends RepresentationModel<Cliente> implements Serializable */{



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_itemPed")
    private Integer id;



    @ManyToOne
    @JoinColumn(name = "pedido_id")
//    @Column(name = "pedido_id")
    private Pedido pedido;
    // @JoinColum -  Chave estrangeira da tabela 'Pedido'.


    @ManyToOne
    @JoinColumn(name = "produto_id")
//    @Column(name = "produto_id")
    private Produto produto;
    // @JoinColum - Chave estrangeira da tabela 'Produto'.

    /*
     * MUITOS ITEMS DE PEDIDO PODEM ESTAR DENTRO DE UM PEDIDO E
     * UM ITEM DE PEDIDO PODE SER APENAS UM PRODUTO (OU SEJA
     * MUITOS ITEMS DE PEDIDO PODEM SER APENAS UM PRODUTO ).
     */

    @Column(name = "quantidade")
    private Integer quantidade;




//
//    public Pedido getPedido() {
//        return pedido;
//    }
//
//    public void setPedido(Pedido pedido) {
//        this.pedido = pedido;
//    }
//
//    public Produto getProduto() {
//        return produto;
//    }
//
//    public void setProduto(Produto produto) {
//        this.produto = produto;
//    }
//
//

//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//
//    public Integer getQuantidade() {
//        return quantidade;
//    }
//
//    public void setQuantidade(Integer quantidade) {
//        this.quantidade = quantidade;
//    }
}

//@Entity e @Table(name = "item_pedido"):
//
//  - '@Entity':
//      Indica que a classe ItemPedido é uma entidade JPA, ou seja, ela será mapeada para
//      uma tabela no banco de dados.
//
//  - '@Table(name = "item_pedido")':
//      Especifica o nome da tabela no banco de dados associada a esta entidade. Neste caso
//      , a tabela se chamará "item_pedido".
//


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


// Relacionamentos @ManyToOne e @JoinColumn:
//
//  - '@ManyToOne':
//      Indica um relacionamento muitos para um entre a entidade ItemPedido e as entidades
//      Pedido e Produto. Isso significa que vários itens de pedido podem estar associados a
//      um único pedido ou produto.
//
//  - '@JoinColumn(name = "pedido_id") e @JoinColumn(name = "produto_id")':
//      Especificam as colunas nas tabelas do banco de dados que serão usadas como chaves
//      estrangeiras para representar os relacionamentos. No caso, as colunas pedido_id e
//      produto_id são usadas para referenciar as chaves primárias nas tabelas Pedido e
//      Produto, respectivamente.
//
//
//  - '@ManyToOne':
//      Essa anotação indica que existe um relacionamento muitos-para-um entre a entidade
//      ItemPedido e a entidade Pedido. Isso significa que vários itens de pedido podem
//      estar associados a um único pedido.
//
//  - '@JoinColumn(name = "pedido_id")':
//      Essa anotação é usada para especificar a coluna na tabela do banco de dados que
//      será usada para armazenar a chave estrangeira que faz referência à entidade Pedido.
//      Neste caso, a coluna se chama "pedido_id". Isso significa que na tabela do banco de
//      dados que armazena os itens do pedido, haverá uma coluna chamada "pedido_id" que
//      armazenará as chaves estrangeiras referentes aos pedidos associados a esses itens.
//
//  - 'private Pedido pedido;':
//      Este é o campo que representa a associação com a entidade Pedido. Cada instância de
//      ItemPedido está associada a um único Pedido.
//
//  Resumindo, esse trecho de código modela a relação em que vários itens de pedido
//  (ItemPedido) podem estar associados a um único pedido (Pedido). A chave estrangeira é
//  representada pela coluna "pedido_id" na tabela dos itens de pedido. Isso é útil para
//  representar casos em que um pedido pode conter vários itens e cada item está associado
//  a um pedido específico.

// Relacionamentos @ManyToOne e @JoinColumn:
//
//  - '@ManyToOne':
//      Essa anotação indica que existe um relacionamento muitos-para-um entre a entidade
//      ItemPedido e a entidade Produto. Isso significa que vários itens de pedido podem
//      estar associados a um único produto.
//
//  - '@JoinColumn(name = "produto_id")':
//      Essa anotação é usada para especificar a coluna na tabela do banco de dados que
//      será usada para armazenar a chave estrangeira que faz referência à entidade
//      Produto. Neste caso, a coluna se chama "produto_id". Isso significa que na tabela
//      do banco de dados que armazena os itens do pedido, haverá uma coluna chamada
//      "produto_id" que armazenará as chaves estrangeiras referentes aos produtos
//      associados a esses itens.
//
//  - 'private Produto produto;':
//      Este é o campo que representa a associação com a entidade Produto. Cada instância
//      de ItemPedido está associada a um único Produto.
//
//  Resumindo, esse trecho de código modela a relação em que vários itens de pedido
//  (ItemPedido) podem estar associados a um único produto (Produto). A chave estrangeira
//  é representada pela coluna "produto_id" na tabela dos itens de pedido. Isso é útil
//  para representar casos em que um pedido pode conter vários itens e cada item está
//  associado a um produto específico.


//Atributos e Métodos Getters e Setters:
//
//  A classe possui um atributo quantidade que representa a quantidade do produto no item
//  do pedido.
//
//  Métodos getters e setters são fornecidos para acessar e modificar os atributos da classe.
//
// Essa classe, portanto, representa um item de pedido em um sistema de vendas, mantendo
// informações sobre o produto associado, a quantidade e a referência ao pedido ao qual
// o item pertence. Os relacionamentos são modelados usando as anotações JPA para
// representar o mapeamento objeto-relacional.

