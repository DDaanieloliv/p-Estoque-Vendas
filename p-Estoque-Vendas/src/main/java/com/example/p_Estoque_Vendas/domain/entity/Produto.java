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
public class Produto /* extends RepresentationModel<Cliente> implements Serializable */ {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_product")
  private Integer id;

  @NotNull(message = "{campo.preco.obrigatorio}")
  private BigDecimal preco;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @CreationTimestamp
  @Column(name = "inserido")
  private Instant creationTimeStamp;

  @NotEmpty(message = "{campo.descricao.obrigatorio}")
  private String descricao;

}
