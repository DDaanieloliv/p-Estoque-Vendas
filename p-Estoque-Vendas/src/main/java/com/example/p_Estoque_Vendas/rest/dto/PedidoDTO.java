package com.example.p_Estoque_Vendas.rest.dto;//CLASSE QUE IRÁ REPRESENTAR A REQUISIÇÃO//

// DTO (Data Transfer Object) é uma classe que geralmente é usada para transferir dados
// entre o frontend e o backend. Ela atua como um invólucro para os dados que serão
// transmitidos pela rede ou entre diferentes camadas de uma aplicação.





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
