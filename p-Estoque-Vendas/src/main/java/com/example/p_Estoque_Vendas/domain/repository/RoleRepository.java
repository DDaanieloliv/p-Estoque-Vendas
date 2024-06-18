package com.example.p_Estoque_Vendas.domain.repository;

import com.example.p_Estoque_Vendas.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName( String name );
}
