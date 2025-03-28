package com.restaurante.pedido.domain.exceptions;

public class PedidoNaoEncontradoException extends Exception {

    // Construtor para receber Long (ID do pedido)
    public PedidoNaoEncontradoException(Long id) {
        super("Pedido com ID " + id + " não encontrado!");
    }

    // Construtor para receber String (mensagem personalizada)
    public PedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}