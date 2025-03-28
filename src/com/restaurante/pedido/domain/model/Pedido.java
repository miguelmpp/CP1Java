package com.restaurante.pedido.domain.model;  

import java.time.LocalDateTime;  
import java.util.ArrayList;  
import java.util.List;  

public class Pedido {  
    private Long id;  
    private String clienteId;  
    private LocalDateTime data;  
    private StatusPedido status;  
    private List<ItemPedido> itens = new ArrayList<>();  

    // Construtor  
    public Pedido() {}  

    // Getters e Setters (gerar via Eclipse: Source → Generate Getters and Setters)  
    public Long getId() { return id; }  
    public void setId(Long id) { this.id = id; }  
    public String getClienteId() { return clienteId; }  
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }  
    public LocalDateTime getData() { return data; }  
    public void setData(LocalDateTime data) { this.data = data; }  
    public StatusPedido getStatus() { return status; }  
    public void setStatus(StatusPedido status) { this.status = status; }  
    public List<ItemPedido> getItens() { return itens; }  
    public void setItens(List<ItemPedido> itens) { this.itens = itens; }  
}  