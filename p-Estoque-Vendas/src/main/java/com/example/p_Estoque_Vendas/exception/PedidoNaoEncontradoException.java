package com.example.p_Estoque_Vendas.exception;


public class PedidoNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PedidoNaoEncontradoException() {
        super("Pedido não encontrado.");
    }

}


// Essa exceção é projetada para ser lançada quando um pedido não pode ser encontrado em
// determinada situação no código. Ao herdar de RuntimeException, ela se torna uma exceção
// não verificada, o que significa que não é necessário declarar explicitamente em cláusulas
// throws ou cercá-la em blocos try-catch (embora você possa fazer isso se quiser). A
// mensagem "Pedido não encontrado." será útil para fornecer informações adicionais sobre
// o motivo da exceção.
