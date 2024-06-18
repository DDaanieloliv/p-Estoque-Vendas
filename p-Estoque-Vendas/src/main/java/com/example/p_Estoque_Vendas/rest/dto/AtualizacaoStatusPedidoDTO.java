package com.example.p_Estoque_Vendas.rest.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizacaoStatusPedidoDTO {

    private String novoStatus;

}


// Essa classe é usada como um DTO para atualizar o status de um pedido. O uso das anotações
// @Getter e @Setter do Lombok simplifica a criação desses métodos, reduzindo a quantidade
// de código boilerplate. Esse DTO é útil quando há a necessidade de receber dados de
// atualização de status de um pedido, geralmente em uma requisição HTTP.
