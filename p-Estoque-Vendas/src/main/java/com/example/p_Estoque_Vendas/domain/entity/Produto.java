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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.hateoas.RepresentationModel;


import java.math.BigDecimal;
import java.time.Instant;


//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "produto")
public class Produto /*extends RepresentationModel<Cliente> implements Serializable */{



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Integer id;


//    @NotNull(message = "Campo preço é obrigatório.")
    @NotNull(message = "{campo.preco.obrigatorio}")
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(name = "inserido")
    private Instant creationTimeStamp;


//    @NotEmpty(message = "Campo Descrição é obrigatório.")
    @NotEmpty(message = "{campo.descricao.obrigatorio}")
    private String descricao;






//
//    public Integer getId() {
//        return id;
//    }
//
//    public String getDescricao() {
//        return descricao;
//    }
//
//    public void setDescricao(String descricao) {
//        this.descricao = descricao;
//    }
//
//    public BigDecimal getPreco() {
//        return preco;
//    }
//
//    public void setPreco(BigDecimal preco) {
//        this.preco = preco;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }


}


//@Entity e @Table(name = "produto"):
//
//  - '@Entity':
//      Indica que a classe Produto é uma entidade JPA, ou seja, será mapeada para uma
//      tabela no banco de dados.
//
//  - '@Table(name = "produto")':
//      Especifica o nome da tabela no banco de dados associada a esta entidade. Neste caso,
//      a tabela se chamará "produto".
//

//@Id, @GeneratedValue(strategy = GenerationType.IDENTITY) e @Column(name = "id_product"):
//
//  - '@Id':
//  Indica que o campo id é a chave primária da tabela.
//
//  - '@GeneratedValue(strategy = GenerationType.IDENTITY)':
//      Essa estratégia indica que a geração de valores de chave primária deve ser delegada
//      ao banco de dados. Geralmente, ela é usada quando o banco de dados suporta a
//      identidade automática, como no caso de colunas autoincrementadas. Quando uma nova
//      linha é inserida, o banco de dados automaticamente atribui um valor único à
//      coluna de chave primária.
//
//      @Id marca o campo id como a chave primária da entidade, e
//      @GeneratedValue(strategy = GenerationType.IDENTITY) especifica que os valores para
//      essa chave primária serão gerados pelo banco de dados usando a identidade
//      automática.
//
//  - '@Column(name = "id_product")':
//      Especifica o nome da coluna na tabela do banco de dados que será mapeada para o
//      atributo id na classe Produto.
//

//@Column(name = "preco_unitario"):
//
//  - '@Column(name = "preco_unitario")':
//      Mapeia o atributo preco para a coluna preco_unitario no banco de dados. Este atributo
//      representa o preço unitário do produto e é do tipo BigDecimal, que é comumente usado
//      para representar valores monetários de alta precisão.
//

//@Column(name = "descricao"):
//
//  - '@Column(name = "descricao")':
//      Mapeia o atributo descricao para a coluna descricao no banco de dados. Este atributo
//      representa a descrição do produto, que é uma String contendo informações sobre o
//      produto.
//

//Métodos Getters e Setters:
//
//  A classe possui métodos getters e setters para acessar e modificar os atributos id,
//  preco e descricao.
//
//  Essa classe representa um produto em um sistema de vendas, mantendo informações como
//  id, preço unitário e descrição. O uso das anotações JPA facilita o mapeamento da classe
//  para uma tabela no banco de dados e o relacionamento com outras entidades, como visto
//  nos exemplos anteriores.


