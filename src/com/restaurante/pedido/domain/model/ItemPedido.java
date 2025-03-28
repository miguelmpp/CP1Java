package com.restaurante.pedido.domain.model;  

import java.math.BigDecimal;  

public class ItemPedido {  
    private String produtoId;  
    private int quantidade;  
    private BigDecimal precoUnitario;  

    // Getters e Setters  
    public String getProdutoId() { return produtoId; }  
    public void setProdutoId(String produtoId) { this.produtoId = produtoId; }  
    public int getQuantidade() { return quantidade; }  
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }  
    public BigDecimal getPrecoUnitario() { return precoUnitario; }  
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }  
}  