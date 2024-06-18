package com.example.p_Estoque_Vendas.service;

import com.example.p_Estoque_Vendas.domain.entity.Pedido;
import com.example.p_Estoque_Vendas.domain.enums.StatusPedido;
import com.example.p_Estoque_Vendas.rest.dto.PedidoDTO;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto, JwtAuthenticationToken token);
    //'PedidoServiceimpl' IRÁ SATISFAZER O CONTRATO DESTA INTERFACE//
    //E CRIAR O A IMPLEMENTAÇÃO DO METODO 'salvar'//


    Optional<Pedido> obterPedidoCompleto (Integer id);

    void atualizacaoStatus(Integer id, StatusPedido statusPedido);
}
