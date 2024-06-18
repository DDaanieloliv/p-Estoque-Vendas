package com.example.p_Estoque_Vendas.rest.dto;

// DTO (Data Transfer Object) é uma classe que geralmente é usada para transferir dados
// entre o frontend e o backend. Ela atua como um invólucro para os dados que serão
// transmitidos pela rede ou entre diferentes camadas de uma aplicação.


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemPedidoDTO {

    private Integer produto;
    private Integer quantidade;

}

