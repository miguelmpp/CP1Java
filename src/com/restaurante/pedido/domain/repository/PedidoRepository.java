package com.restaurante.pedido.domain.repository;  

import com.restaurante.pedido.domain.model.Pedido;  
import com.restaurante.pedido.domain.exceptions.PedidoNaoEncontradoException;  
import java.util.List;  

public interface PedidoRepository {  
    Pedido salvar(Pedido pedido);  
    Pedido buscarPorId(Long id) throws PedidoNaoEncontradoException;  
    List<Pedido> listarPorCliente(String clienteId);  
}  