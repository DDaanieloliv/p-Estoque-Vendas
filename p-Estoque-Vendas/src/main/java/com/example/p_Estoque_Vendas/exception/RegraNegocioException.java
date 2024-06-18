package com.example.p_Estoque_Vendas.exception;


public class RegraNegocioException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public RegraNegocioException(String message) {
        super(message);
    }
}


// --public class RegraNegocioException extends RuntimeException {:
//
//  Declaração da classe. A palavra-chave public indica que a classe é acessível de outros
//  pacotes. A classe estende RuntimeException, o que significa que RegraNegocioException é
//  uma exceção de tempo de execução. Isso indica que a classe não precisa ser
//  explicitamente declarada em cláusulas throws ou capturada em blocos try-catch (a menos
//  que desejado).
//
//
// --public RegraNegocioException(String message) {:
//
//  Construtor da classe. Este construtor aceita uma mensagem como parâmetro, que é a
//  mensagem de erro associada à exceção.
//
//
// --super(message);:
//  Chama o construtor da classe pai (RuntimeException) passando a mensagem fornecida como
//  argumento. Isso inicializa a mensagem de erro da exceção.
//
//
//  Essa classe é útil quando você deseja sinalizar que ocorreu uma exceção relacionada a
//  regras de negócio específicas na sua aplicação. O uso de uma exceção personalizada
//  permite um tratamento mais específico desses casos, tornando o código mais legível e
//  facilitando a identificação de problemas durante o desenvolvimento e depuração do
//  sistema.