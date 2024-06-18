package com.example.p_Estoque_Vendas.rest.controller;

import com.example.p_Estoque_Vendas.exception.PedidoNaoEncontradoException;
import com.example.p_Estoque_Vendas.exception.RegraNegocioException;
import com.example.p_Estoque_Vendas.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ApplicationControllerAdvice /*extends ResponseEntityExceptionHandler*/ {

    // Quando você estende ResponseEntityExceptionHandler, você está usando uma classe
    // fornecida pelo Spring que já possui métodos para lidar com várias exceções comuns,
    // incluindo MethodArgumentNotValidException, que é lançada quando ocorre uma
    // validação de argumento de método falha.
    //
    // No entanto, se você remove a extensão de ResponseEntityExceptionHandler, você deve
    // lidar manualmente com essas exceções no seu código, incluindo a definição de
    // métodos anotados com @ExceptionHandler para tratar casos específicos. A remoção
    // dessa extensão significa que você está assumindo a responsabilidade de lidar com
    // exceções manualmente, sem depender da implementação padrão fornecida por
    // ResponseEntityExceptionHandler.
    //
    // Ao fazer essa modificação, você ganha mais controle sobre o tratamento de exceções,
    // mas também é necessário lidar explicitamente com cada tipo de exceção que deseja
    // capturar. Se você se sente confortável gerenciando manualmente esses casos, a
    // abordagem sem estender ResponseEntityExceptionHandler pode ser apropriada. No
    // entanto, é importante garantir que todos os casos relevantes sejam tratados
    // adequadamente em seu código.
    //
    // Essa é a razão pela qual a remoção da extensão resolveu o problema de ambiguidade
    // nos métodos de exceção. O Spring não precisa mais escolher entre a implementação
    // padrão em ResponseEntityExceptionHandler e a sua implementação personalizada em
    // ApplicationControllerAdvice.


    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex) {
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException( PedidoNaoEncontradoException ex ) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleValidationException(MethodArgumentNotValidException  exp) {
        List<String> errors = exp.getBindingResult()
                .getAllErrors()
                .stream()
                .map( erro -> erro.getDefaultMessage()).collect(Collectors.toList());
        return new ApiErrors(errors);
    }



}


// @ControllerAdvice: Esta anotação é usada para marcar a classe como um componente que
// fornece aconselhamento (advice) para todos os controllers. Ele é usado principalmente
// para manipulação global de exceções.
//
// extends ResponseEntityExceptionHandler: Essa classe estende
// ResponseEntityExceptionHandler, que é uma classe base fornecida pelo Spring que oferece
// manipulação de exceções específicas do Spring para ResponseEntity.


// Presumivelmente, a classe ApiErrors é usada para encapsular informações de erro que
// serão enviadas como resposta em caso de exceção. Como não forneceu o código dessa
// classe, não posso detalhar sua implementação específica. Geralmente, ela conterá
// detalhes sobre os erros ocorridos, talvez mensagens, códigos de erro, etc.




//@ExceptionHandler(RegraNegocioException.class):
//
//  Esta anotação indica que o método handleRegraNegocioException será invocado quando
//  ocorrer uma exceção do tipo RegraNegocioException. É uma forma de configurar o
//  tratamento de exceções em um controlador específico.
//
//@ResponseStatus(BAD_REQUEST):
//
//  Essa anotação define o código de status HTTP que será retornado na resposta. Neste
//  caso, BAD_REQUEST indica que a requisição do cliente foi inválida de alguma forma.
//
//
//public ApiErrors handleRegraNegocioException(RegraNegocioException ex):
//
//  Este é o método que será chamado para lidar com a exceção. Ele recebe como parâmetro
//  uma instância de RegraNegocioException, que contém informações sobre a exceção que
//  ocorreu.
//
//
//String mensagemErro = ex.getMessage();:
//
//  Obtém a mensagem associada à exceção. A mensagem geralmente é definida ao criar uma
//  instância de RegraNegocioException e é uma descrição do erro que ocorreu.
//
//
//return new ApiErrors(mensagemErro);:
//
//  Cria uma instância de ApiErrors para encapsular as informações de erro. A classe
//  ApiErrors (não fornecida no seu código) provavelmente contém atributos para armazenar
//  informações detalhadas sobre o erro, como mensagens, códigos de erro, etc. Aqui, a
//  mensagem de erro da exceção é utilizada para construir esse objeto.
//
//
// Em resumo, quando ocorre uma exceção do tipo RegraNegocioException, este método é
// acionado. Ele extrai a mensagem de erro da exceção e a utiliza para criar um objeto
// ApiErrors, que provavelmente será enviado como resposta para o cliente da aplicação.
// Esse processo ajuda a fornecer informações claras sobre o motivo da falha na requisição.



//@ExceptionHandler(PedidoNaoEncontradoException.class):
//
//  Esta anotação indica que o método handlePedidoNotFoundException será invocado quando
//  ocorrer uma exceção do tipo PedidoNaoEncontradoException. Assim como expliquei
//  anteriormente, essa anotação é uma forma de configurar o tratamento de exceções em um
//  controlador específico.
//
//
//@ResponseStatus(HttpStatus.NOT_FOUND):
//
//  Essa anotação define o código de status HTTP que será retornado na resposta. Neste
//  caso, NOT_FOUND indica que o recurso solicitado não foi encontrado no servidor.
//
//
//public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException ex):
//
//  Este é o método que será chamado para lidar com a exceção. Ele recebe como parâmetro
//  uma instância de PedidoNaoEncontradoException, que contém informações sobre a exceção
//  que ocorreu.
//
//
//return new ApiErrors(ex.getMessage());:
//
//  Cria uma instância de ApiErrors para encapsular as informações de erro. Assim como no
//  exemplo anterior, a mensagem de erro da exceção é utilizada para construir esse objeto.
//
//
// Em resumo, quando ocorre uma exceção do tipo PedidoNaoEncontradoException, este método
// é acionado. Ele extrai a mensagem de erro da exceção e a utiliza para criar um objeto
// ApiErrors, que provavelmente será enviado como resposta para o cliente da aplicação.
// Esse processo ajuda a fornecer informações claras sobre o motivo da falha na
// requisição, neste caso indicando que o pedido não foi encontrado.