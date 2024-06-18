package com.example.p_Estoque_Vendas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacoesPedidoDTO {

    private Integer codigo;
    private String cpf;
    private String nomeCliente;
    private BigDecimal total;
    private String dataPedido;
    private String status;
    private List<InformacaoItemPedidoDTO> items;

}


// Esse DTO é útil para encapsular informações sobre um pedido e seus itens, fornecendo
// uma maneira estruturada de transferir esses dados entre diferentes partes do sistema,
// como o serviço que manipula a lógica de negócios e o controlador que lida com as
// requisições HTTP. O uso do Lombok ajuda a reduzir a verbosidade do código, evitando a
// escrita manual de getters, setters e outros métodos comuns.
