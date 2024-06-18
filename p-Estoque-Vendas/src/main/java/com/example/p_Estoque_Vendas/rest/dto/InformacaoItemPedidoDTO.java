package com.example.p_Estoque_Vendas.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacaoItemPedidoDTO {

    private String descricaoProduto;
    private BigDecimal precoUnitario;
    private Integer quantidade;

}

// Assim como o InformacoesPedidoDTO, este DTO é útil para encapsular informações
// específicas sobre os itens de um pedido, proporcionando uma maneira estruturada de
// transferir esses dados entre diferentes partes do sistema. O uso do Lombok reduz a
// quantidade de código boilerplate, já que ele gera automaticamente construtores, getters,
// setters e outros métodos comuns.
