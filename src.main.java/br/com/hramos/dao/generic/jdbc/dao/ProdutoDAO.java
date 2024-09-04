package br.com.hramos.dao.generic.jdbc.dao;

import br.com.hramos.dao.generic.jdbc.ConnectionFactory;
import br.com.hramos.domain.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO implements IProdutoDAO{

    private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) throws SQLException {
        if (rs != null && !rs.isClosed()) {
            rs.close();
        }
        if (stm != null && !stm.isClosed()) {
            stm.close();
        }
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Override
    public Integer cadastrar(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = sqlInsertString();
            stm = connection.prepareStatement(sql);
            stmSetValuesInsert(stm, produto);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    private void stmSetValuesInsert(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setString(1, produto.getNome());
        stm.setString(2, produto.getCodigo());
        stm.setLong(3, produto.getValor());
    }

    private String sqlInsertString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO TB_PRODUTO_2 (ID, NOME, CODIGO, VALOR) ");
        stringBuilder.append("VALUES (nextval('sq_produto_2'), ?, ?, ?)");
        return stringBuilder.toString();
    }

    @Override
    public Integer atualizar(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = sqlUpdateString();
            stm = connection.prepareStatement(sql);
            stmSetValuesUpdate(stm, produto);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    private void stmSetValuesUpdate(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setString(1, produto.getNome());
        stm.setString(2, produto.getCodigo());
        stm.setLong(3, produto.getValor());
        stm.setLong(4, produto.getId());
    }

    private String sqlUpdateString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE TB_PRODUTO_2 ");
        stringBuilder.append("SET NOME = ?, CODIGO = ?, VALOR = ? ");
        stringBuilder.append("WHERE ID = ?");
        return stringBuilder.toString();
    }

    @Override
    public Produto buscar(String codigo) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Produto produto = null;

        try {
            connection = ConnectionFactory.getConnection();
            stm = connection.prepareStatement("SELECT * FROM TB_PRODUTO_2 WHERE CODIGO = ?");
            stmSetValuesSelect(stm, codigo);
            rs = stm.executeQuery();
            if (rs.next()) {
                produto = new Produto();
                produto.setId(rs.getLong(1));
                produto.setNome(rs.getString(2));
                produto.setCodigo(rs.getString(3));
                produto.setValor(rs.getLong(4));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, rs);
        }
        return produto;
    }

    private void stmSetValuesSelect(PreparedStatement stm, String codigo) throws SQLException {
        stm.setString(1, codigo);
    }

    @Override
    public List<Produto> buscarTodos() throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Produto> produtos = new ArrayList<Produto>();

        try {
            connection = ConnectionFactory.getConnection();
            stm = connection.prepareStatement("SELECT * FROM TB_PRODUTO_2");
            rs = stm.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getLong(1));
                produto.setNome(rs.getString(2));
                produto.setCodigo(rs.getString(3));
                produto.setValor(rs.getLong(4));

                produtos.add(produto);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, rs);
        }
        return produtos;
    }

    @Override
    public Integer excluir(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;

        try {
            connection = ConnectionFactory.getConnection();
            stm = connection.prepareStatement("DELETE FROM TB_PRODUTO_2 WHERE ID = ?");
            stmSetValuesDelete(stm, produto);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    private void stmSetValuesDelete(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setLong(1, produto.getId());
    }
}
