package com.example.p_Estoque_Vendas.domain.repository;


import com.example.p_Estoque_Vendas.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}

