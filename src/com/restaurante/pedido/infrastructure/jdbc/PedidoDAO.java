package com.restaurante.pedido.infrastructure.jdbc;

import com.restaurante.pedido.domain.model.ItemPedido;
import com.restaurante.pedido.domain.model.Pedido;
import com.restaurante.pedido.domain.model.StatusPedido;
import com.restaurante.pedido.domain.repository.PedidoRepository;
import com.restaurante.pedido.domain.exceptions.PedidoNaoEncontradoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO implements PedidoRepository {
    // Configurações de conexão
    private static final String URL = "jdbc:oracle:thin:@ORACLE.FIAP.COM.BR:1521:ORCL";
    private static final String USER = "RM553988";
    private static final String PASS = "041005";

    @Override
    public Pedido salvar(Pedido pedido) {
        String sqlPedido = "INSERT INTO pedidos (cliente_id, status) VALUES (?, ?)";
        String sqlItem = "INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            conn.setAutoCommit(false);

            try (PreparedStatement psPedido = conn.prepareStatement(sqlPedido, new String[]{"id"});
                 PreparedStatement psItem = conn.prepareStatement(sqlItem)) {

                // Salva o pedido
                psPedido.setString(1, pedido.getClienteId());
                psPedido.setString(2, pedido.getStatus().name());
                psPedido.executeUpdate();

                // Recupera o ID gerado
                try (ResultSet rs = psPedido.getGeneratedKeys()) {
                    if (rs.next()) {
                        pedido.setId(rs.getLong(1));
                    }
                }

                // Salva os itens em lote
                for (ItemPedido item : pedido.getItens()) {
                    psItem.setLong(1, pedido.getId());
                    psItem.setString(2, item.getProdutoId());
                    psItem.setInt(3, item.getQuantidade());
                    psItem.setBigDecimal(4, item.getPrecoUnitario());
                    psItem.addBatch();
                }
                psItem.executeBatch();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar pedido: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return pedido;
    }

    @Override
    public Pedido buscarPorId(Long id) throws PedidoNaoEncontradoException {
        String sqlPedido = "SELECT * FROM pedidos WHERE id = ?";
        String sqlItens = "SELECT * FROM itens_pedido WHERE pedido_id = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement psPedido = conn.prepareStatement(sqlPedido)) {

            psPedido.setLong(1, id);
            ResultSet rsPedido = psPedido.executeQuery();

            if (rsPedido.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rsPedido.getLong("id"));
                pedido.setClienteId(rsPedido.getString("cliente_id"));
                
                try {
                    pedido.setStatus(StatusPedido.valueOf(rsPedido.getString("status")));
                } catch (IllegalArgumentException e) {
                    throw new PedidoNaoEncontradoException("Status inválido para o pedido " + id);
                }

                // Busca os itens
                try (PreparedStatement psItens = conn.prepareStatement(sqlItens)) {
                    psItens.setLong(1, id);
                    ResultSet rsItens = psItens.executeQuery();
                    List<ItemPedido> itens = new ArrayList<>();

                    while (rsItens.next()) {
                        ItemPedido item = new ItemPedido();
                        item.setProdutoId(rsItens.getString("produto_id"));
                        item.setQuantidade(rsItens.getInt("quantidade"));
                        item.setPrecoUnitario(rsItens.getBigDecimal("preco_unitario"));
                        itens.add(item);
                    }
                    pedido.setItens(itens);
                }

                return pedido;
            } else {
                throw new PedidoNaoEncontradoException(id);
            }
        } catch (SQLException e) {
            throw new PedidoNaoEncontradoException(id);
        }
    }

    @Override
    public List<Pedido> listarPorCliente(String clienteId) {
        List<Pedido> pedidos = new ArrayList<>();
        String sqlPedidos = "SELECT * FROM pedidos WHERE cliente_id = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement psPedidos = conn.prepareStatement(sqlPedidos)) {

            psPedidos.setString(1, clienteId);
            ResultSet rsPedidos = psPedidos.executeQuery();

            while (rsPedidos.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rsPedidos.getLong("id"));
                pedido.setClienteId(rsPedidos.getString("cliente_id"));
                
                try {
                    pedido.setStatus(StatusPedido.valueOf(rsPedidos.getString("status")));
                } catch (IllegalArgumentException e) {
                    continue; // Ignora pedidos com status inválido
                }

                pedido.setItens(buscarItensDoPedido(conn, pedido.getId()));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    private List<ItemPedido> buscarItensDoPedido(Connection conn, Long pedidoId) throws SQLException {
        List<ItemPedido> itens = new ArrayList<>();
        String sqlItens = "SELECT * FROM itens_pedido WHERE pedido_id = ?";
        
        try (PreparedStatement psItens = conn.prepareStatement(sqlItens)) {
            psItens.setLong(1, pedidoId);
            ResultSet rsItens = psItens.executeQuery();

            while (rsItens.next()) {
                ItemPedido item = new ItemPedido();
                item.setProdutoId(rsItens.getString("produto_id"));
                item.setQuantidade(rsItens.getInt("quantidade"));
                item.setPrecoUnitario(rsItens.getBigDecimal("preco_unitario"));
                itens.add(item);
            }
        }
        return itens;
    }
}