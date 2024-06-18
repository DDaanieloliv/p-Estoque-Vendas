package com.example.p_Estoque_Vendas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUserDTO {

    private String username;
    private String password;
    private Integer role_number;

}
