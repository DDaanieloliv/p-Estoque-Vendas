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
  // @Column(name = "cliente_id")
  private Cliente cliente;
  // @JoinColum - Chave estrangeira da tabela 'Cliente'

  // Quando você define um relacionamento @ManyToOne, a anotação @JoinColumn é
  // geralmente
  // suficiente para mapear a coluna de chave estrangeira no lado "muitos" desse
  // relacionamento. A anotação @Column(name = "cliente_id") pode ser redundante e
  // causar
  // confusão no Hibernate/JPA.

  @Column(name = "data_pedido")
  private LocalDate data_pedido;

  @Column(name = "total", precision = 20, scale = 2)
  private BigDecimal total;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private StatusPedido status;

  // @Enumerated(EnumType.STRING): Essa anotação é usada para mapear uma
  // enumeração (enum)
  // em um banco de dados relacional. Ela especifica como os valores da enumeração
  // devem
  // ser armazenados no banco de dados. No caso de EnumType.STRING, os valores da
  // enumeração
  // serão armazenados como strings no banco de dados.
  //
  // @Column(name = "status"): Essa anotação é usada para personalizar as
  // propriedades da
  // coluna do banco de dados associada a um campo da entidade. Neste caso, a
  // propriedade
  // status será mapeada para uma coluna chamada "status". O parâmetro name
  // especifica o
  // nome da coluna no banco de dados.
  //
  // Então, a combinação dessas anotações está indicando que a propriedade status
  // de uma
  // entidade será mapeada para uma coluna chamada "status" no banco de dados, e
  // os
  // valores dessa propriedade (que é uma enumeração) serão armazenados como
  // strings.
  // Isso é útil quando você tem uma enumeração em Java representando diferentes
  // estados
  // (como status de um pedido) e deseja persistir esses estados no banco de dados
  // de uma
  // forma legível e compreensível.

  @OneToMany(mappedBy = "pedido")
  private List<ItemPedido> itens;

}
