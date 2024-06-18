package com.example.p_Estoque_Vendas.service;

import com.example.p_Estoque_Vendas.rest.dto.CreateUserDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

public interface UserService {

    void optionCreation(CreateUserDTO dto, Jwt jwt);

}
