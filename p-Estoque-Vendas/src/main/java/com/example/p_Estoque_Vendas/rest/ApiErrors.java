package com.example.p_Estoque_Vendas.rest;


import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;


@Getter
@Setter
public class ApiErrors {



    private final List<String> errors;

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }

    public ApiErrors(String mensagemErro) {
        this.errors = Arrays.asList(mensagemErro);
    }
}



// ou seja se os metodos salvar e converterItems retornarem alguma
// Exception(RegraNegocioException)  a mensagem passada como parametro em
// RegraNegocioException. Em seguida, o Spring, ao identificar que ocorreu uma exceção do
// tipo RegraNegocioException, delegará o tratamento dessa exceção para o método
// handleRegraNegocioException na classe ApplicationControllerAdvice. Nesse método, a
// mensagem de erro associada à exceção será recuperada e utilizada para criar um objeto
// ApiErrors, que encapsula a mensagem de erro de forma estruturada.
//
// O ApiErrors então será retornado como resposta, fornecendo informações sobre o erro ao
// cliente. Isso é uma prática comum em aplicações Spring, onde o tratamento centralizado
// de exceções em um componente global (ControllerAdvice) permite um gerenciamento
// consistente e estruturado dos erros.