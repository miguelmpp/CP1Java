package com.restaurante.pedido.application;

import com.restaurante.pedido.domain.exceptions.PedidoNaoEncontradoException;
import com.restaurante.pedido.domain.model.ItemPedido;
import com.restaurante.pedido.domain.model.Pedido;
import com.restaurante.pedido.domain.model.StatusPedido;
import com.restaurante.pedido.infrastructure.jdbc.PedidoDAO;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        // Configuração inicial
        PedidoDAO pedidoDAO = new PedidoDAO();
        PedidoService pedidoService = new PedidoService(pedidoDAO);

        // Criar um novo pedido
        Pedido pedido = new Pedido();
        pedido.setClienteId("CLIENTE_123");
        pedido.setStatus(StatusPedido.EM_PREPARO);

        // Adicionar itens ao pedido
        ItemPedido item1 = new ItemPedido();
        item1.setProdutoId("PROD_001");
        item1.setQuantidade(2);
        item1.setPrecoUnitario(new BigDecimal("25.50"));
        pedido.getItens().add(item1);

        // Registrar o pedido no banco
        pedidoService.registrarPedido(pedido);
        System.out.println("Pedido registrado! ID: " + pedido.getId());

        // Buscar o pedido pelo ID
        try {
            Pedido pedidoRecuperado = pedidoService.buscarPedidoPorId(pedido.getId());
            System.out.println("Pedido encontrado: " + pedidoRecuperado.getClienteId());
        } catch (PedidoNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        // Listar pedidos do cliente
        System.out.println("\nPedidos do cliente CLIENTE_123:");
        pedidoService.listarPedidosPorCliente("CLIENTE_123").forEach(p -> 
            System.out.println("- Pedido ID: " + p.getId())
        );
    }
}