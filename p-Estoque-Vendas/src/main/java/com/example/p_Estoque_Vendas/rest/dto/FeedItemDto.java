package com.example.p_Estoque_Vendas.rest.dto;

import com.example.p_Estoque_Vendas.domain.entity.Role;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record FeedItemDto(
        UUID userId,
        String username,
        Instant creatianTimeStamp,
        String password,
        Set<Role> roles) {
}
