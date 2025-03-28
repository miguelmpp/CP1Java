package com.restaurante.pedido.application;  

import com.restaurante.pedido.domain.model.Pedido;  
import com.restaurante.pedido.domain.model.ItemPedido;  
import com.restaurante.pedido.domain.repository.PedidoRepository;  
import com.restaurante.pedido.domain.exceptions.PedidoNaoEncontradoException;  
import java.math.BigDecimal;  
import java.util.List;  

public class PedidoService {  
    private final PedidoRepository pedidoRepository;  

    public PedidoService(PedidoRepository pedidoRepository) {  
        this.pedidoRepository = pedidoRepository;  
    }  

    public Pedido registrarPedido(Pedido pedido) {  
        // Validação 1: Lista de itens não pode ser vazia  
        if (pedido.getItens().isEmpty()) {  
            throw new IllegalArgumentException("O pedido deve ter pelo menos um item!");  
        }  

        // Validação 2: Status do pedido é obrigatório  
        if (pedido.getStatus() == null) {  
            throw new IllegalArgumentException("Status do pedido é obrigatório!");  
        }  

        // Validação 3: Preço unitário dos itens deve ser positivo  
        for (ItemPedido item : pedido.getItens()) {  
            if (item.getPrecoUnitario().compareTo(BigDecimal.ZERO) <= 0) {  
                throw new IllegalArgumentException("Preço unitário do item " + item.getProdutoId() + " deve ser positivo!");  
            }  
        }  

        return pedidoRepository.salvar(pedido);  
    }  

    public Pedido buscarPedidoPorId(Long id) throws PedidoNaoEncontradoException {  
        return pedidoRepository.buscarPorId(id);  
    }  

    public List<Pedido> listarPedidosPorCliente(String clienteId) {  
        return pedidoRepository.listarPorCliente(clienteId);  
    }  
}  