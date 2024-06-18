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

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table( name = "cliente" )
public class Cliente extends RepresentationModel<Cliente> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id //PRIMARY KEY//
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO_INCREMENT//
    @Column(name = "id_client")    //MAPPING PARA A COLUNA "id_client"//
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(name = "instanciado")
    private Instant creationTimeStamp;

    @Column(name = "cpf", length = 11)
//    @NotEmpty(message = "Campo CPF é obrigatório.")
    @NotEmpty(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")
    private String cpf;


    @Column(name = "nome", length = 100)
//    @NotEmpty(message = "Campo nome é obrigatório.")
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private String nome;

    @JsonIgnore //NÃO PERMITIRA APARECER O JSON VAZIO DA ENTIDADE PEDIDO//
    @OneToMany( mappedBy = "cliente", fetch = FetchType.LAZY) //EAGER
    private Set<Pedido> pedidos;
    // indica uma relação entre entidades, mas não cria uma coluna diretamente na tabela
    // da entidade onde ela está sendo usada. No exemplo que você forneceu.
    // A coluna que estabelece a relação (a chave estrangeira)(relação entre 'Cliente' e
    // 'Pedido') será criada na tabela associada (Pedido). Portanto, a tabela Pedido terá
    // uma coluna que referencia a tabela Cliente. Não haverá uma coluna diretamente na
    // tabela Cliente para representar essa relação.

//    public Set<Pedido> getPedidos(){
//        return pedidos;
//    }
//
//    public void setPedidos(Set<Pedido> pedidos) {
//        this.pedidos = pedidos;
//    }



    // * Set em Java é uma coleção que não permite elementos duplicados. Ele é mais
    // adequado quando você precisa garantir que cada elemento seja único na coleção.
    // No exemplo que você forneceu, private Set<Pedido> pedidos;, está sendo utilizado
    // um Set para representar uma coleção de objetos do tipo Pedido. Isso significa
    // que, para uma instância dessa classe, os objetos Pedido associados a ela devem
    // ser únicos, e não haverá repetições na coleção.

//    public Cliente() {
//    }

    public Cliente(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Cliente(String nome) {
        this.nome = nome;
    }
//
//
//
//
//    public String getNome() {
//        return nome;
//    }
//
//    public void setNome(String nome) {
//        this.nome = nome;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getCpf() {
//        return cpf;
//    }
//
//    public void setCpf(String cpf) {
//        this.cpf = cpf;
//    }
//
//    @Override
//    public String toString() {
//        return "Cliente{" +
//                "id=" + id +
//                ", nome='" + nome + '\'' +
//                '}';
//    }
}

//*@Entity e @Table(name = "cliente"):
//
//  - '@Entity':
//      Essa anotação indica que a classe Cliente é uma entidade JPA. Entidades JPA são
//      objetos que são mapeados para tabelas em um banco de dados relacional.
//
//  - '@Table(name = "cliente")':
//      Define o nome da tabela no banco de dados associada a esta entidade. Neste caso,
//      a tabela se chamará "cliente".
//


//*@Id e @GeneratedValue(strategy = GenerationType.IDENTITY):
//
//  - '@Id':
//      Indica que o campo id é a chave primária da tabela.
//
//  - 'GenerationType.IDENTITY':
//      Essa estratégia indica que a geração de valores de chave primária deve ser
//      delegada ao banco de dados. Geralmente, ela é usada quando o banco de dados
//      suporta a identidade automática, como no caso de colunas autoincrementadas.
//      Quando uma nova linha é inserida, o banco de dados automaticamente atribui um
//      valor único à coluna de chave primária.
//
//      @Id marca o campo id como a chave primária da entidade, e
//      @GeneratedValue(strategy = GenerationType.IDENTITY) especifica que os valores
//      para essa chave primária serão gerados pelo banco de dados usando a identidade
//      automática.
//
//      Essa estratégia é comumente usada em sistemas que suportam colunas de identidade
//      ou autoincremento em bancos de dados relacionais, como MySQL e PostgreSQL.


//*@Column(name = "nome", length = 100):
//
//  - '@Column':
//      Essa anotação é usada para mapear o campo nome para uma coluna no banco de dados.
//
//  - 'name = "nome"':
//      Indica que o campo nome será mapeado para uma coluna chamada "nome".
//
//  - 'length = 100':
//      Especifica o comprimento máximo da coluna nome no banco de dados.
//


// - @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY):
//
//      Esta anotação faz parte do mapeamento de relacionamentos em um contexto de
//      mapeamento objeto-relacional (ORM), geralmente utilizado em frameworks como o
//      Hibernate para mapear objetos Java para tabelas em um banco de dados. Essa
//      anotação indica que a relação entre a classe atual (suponhamos que seja uma
//      entidade Cliente) e a classe Pedido é de "um para muitos". Ou seja, um cliente
//      pode ter vários pedidos associados a ele. O mappedBy = "cliente" especifica o
//      nome do atributo na classe Pedido que faz referência ao cliente.
//      fetch = FetchType.LAZY indica que os pedidos não devem ser carregados a
//      utomaticamente quando um cliente é carregado, mas sim quando você explicitamente
//      acessar a propriedade pedidos.
//
//  - 'private Set<Pedido> pedidos;':
//      Aqui, você está declarando um atributo chamado pedidos, que é do tipo
//      Set<Pedido>. Isso significa que cada instância de Cliente pode ter uma coleção
//      de pedidos associada a ela. Lembre-se de que, como é um Set, cada Pedido na
//      coleção deve ser único.
//
//  - 'public Set<Pedido> getPedidos() { return pedidos; }':
//      Este é um método getter para obter a coleção de pedidos associada a um cliente.
//      Ele permite que você obtenha a referência para a coleção de pedidos.
//
//  - 'public void setPedidos(Set<Pedido> pedidos) { this.pedidos = pedidos; }':
//      Este é um método setter que permite definir a coleção de pedidos associada a um
//      cliente. Isso é útil ao criar ou atualizar um cliente com uma nova lista de
//      pedidos.
//
//
//  Em resumo, este trecho de código está relacionado ao mapeamento de uma relação "um
//  para muitos" entre a classe Cliente e a classe Pedido, usando a anotação @OneToMany.
//  A coleção de pedidos associada a um cliente é representada como um Set, garantindo
//  que cada pedido seja único para aquele cliente. Os métodos getter e setter são
//  fornecidos para acessar e modificar essa coleção.
//

//  - 'fetch = FetchType.LAZY':
//      Define que a recuperação dos pedidos associados a um cliente deve ser feita de
//      forma preguiçosa, o que significa que os pedidos não serão carregados automaticamente
//      a menos que explicitamente solicitado.
//


//*Construtores, Getters e Setters:
//
//  A classe possui um construtor padrão vazio (public Cliente() {}) e dois construtores
//  adicionais. Um construtor que aceita um id e um nome e outro construtor que aceita
//  apenas um nome.
//
//  Métodos getters e setters são fornecidos para acessar e modificar os atributos da
//  classe.
//
//  - '@Override toString():
//      Sobrescreve o método toString(). O método retorna uma representação de string do
//      objeto, que inclui o id e o nome do cliente.
//
//  Essa classe, portanto, representa a entidade Cliente no contexto de uma aplicação
//  Spring Boot com JPA. Quando a aplicação é executada, o framework JPA cuida automaticamente
//  da criação da tabela associada a esta entidade no banco de dados, além de gerenciar
//  os relacionamentos e operações de persistência.


