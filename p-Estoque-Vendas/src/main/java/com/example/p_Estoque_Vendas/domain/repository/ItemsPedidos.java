package com.example.p_Estoque_Vendas.domain.repository;


import com.example.p_Estoque_Vendas.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedidos extends JpaRepository<ItemPedido, Integer> {
}