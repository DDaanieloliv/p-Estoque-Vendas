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

}

