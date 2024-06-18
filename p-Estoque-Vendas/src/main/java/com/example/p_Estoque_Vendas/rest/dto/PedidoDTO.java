package com.example.p_Estoque_Vendas.rest.dto;//CLASSE QUE IRÁ REPRESENTAR A REQUISIÇÃO//

// DTO (Data Transfer Object) é uma classe que geralmente é usada para transferir dados
// entre o frontend e o backend. Ela atua como um invólucro para os dados que serão
// transmitidos pela rede ou entre diferentes camadas de uma aplicação.


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



import com.example.p_Estoque_Vendas.validation.NotEmptyList;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoDTO {

//  {
//    "cliente" : 1,
//    "total" : 100,
//    "items" : [
//        {
//            "produto" : 1,
//            "quantidade" : 10
//        }
//    ]
//  }
//  JSON QUE IRÁ REPRESENTAR O CORPO NOSSA REQUISIÇÃO.

    //    @NotNull(message = "Informe o codigo do cliente.")
    @NotNull(message = "{campo.codigo-cliente.obrigatorio}")
    private Integer cliente;

    //    @NotNull(message = "Campo 'Total' do pedido é obrigatório.")
    @NotNull(message = "{campo.total-pedido.obrigatorio}")
    private BigDecimal total;

    //    @NotEmptyList(message = "Pedido não pode ser realizado sem itens.")
    @NotEmptyList(message = "{campo.items-pedido.obrigatorio}")
    private List<ItemPedidoDTO> items; //COLEÇÃO DEITENS PEDIDOS NO PEDIDO//


}


// O trecho {campo.cpf.obrigatorio} é uma chave que é usada para recuperar a mensagem
// correspondente do MessageSource configurado em sua aplicação Spring. A classe de
// configuração que você definiu anteriormente, provavelmente chamada
// InternacionalizacaoConfig, está configurando o messageSource para carregar mensagens de
// um arquivo de propriedades (como messages.properties) e definindo outras configurações,
// como o encoding e o locale padrão.
//
// Quando você usa essa chave em seu código, o sistema de mensagens (configurado pelo
// MessageSource) procura essa chave no arquivo de mensagens apropriado com base no locale
// configurado. Se o locale for, por exemplo, Português do Brasil, ele procurará em um
// arquivo como messages_pt_BR.properties.
//
// Então, a configuração que você fez em InternacionalizacaoConfig é crucial para permitir
// que o Spring localize e forneça as mensagens corretas com base na chave especificada em
// seu código.
