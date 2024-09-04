package br.com.hramos;

import br.com.hramos.dao.generic.jdbc.dao.IProdutoDAO;
import br.com.hramos.dao.generic.jdbc.dao.ProdutoDAO;
import br.com.hramos.domain.Produto;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoTest {
    private IProdutoDAO produtoDAO;

    @Test
    public void cadastrarTest() throws Exception {
        produtoDAO = new ProdutoDAO();

        Produto produto = new Produto();
        produto.setNome("Produto test");
        produto.setCodigo("10");
        produto.setValor(500000L);

        Integer countCadastrar = produtoDAO.cadastrar(produto);
        Assert.assertTrue(countCadastrar == 1);

        Produto produtoDB = produtoDAO.buscar(produto.getCodigo());
        Assert.assertEquals(produto.getCodigo(), produtoDB.getCodigo());
        Assert.assertEquals(produto.getNome(), produtoDB.getNome());

        Integer countDelete = produtoDAO.excluir(produtoDB);
        Assert.assertTrue(countDelete == 1);
    }

    @Test
    public void buscarTodosTest() throws Exception {
        produtoDAO = new ProdutoDAO();

        for (int i = 0; i < 3; i++) {
            Produto produto = new Produto();
            produto.setNome("NameTest" + i);
            produto.setCodigo("10" + i);
            produto.setValor((long) i);
            Integer countCadastrar = produtoDAO.cadastrar(produto);
            Assert.assertTrue(countCadastrar == 1);
        }

        List<Produto> produtos = produtoDAO.buscarTodos();
        Assert.assertNotNull(produtos);
        Assert.assertTrue(produtos.size() == 3);

        Integer countDelete = 0;
        for (Produto produto : produtos) {
            produtoDAO.excluir(produto);
            countDelete++;
        }

        Assert.assertTrue(produtos.size() == countDelete);

    }

    @Test
    public void atualizarTest() throws Exception {
        produtoDAO = new ProdutoDAO();

        Produto produto = new Produto();
        produto.setNome("Produto test");
        produto.setCodigo("10");
        produto.setValor(500000L);

        Integer countCadastrar = produtoDAO.cadastrar(produto);
        Assert.assertTrue(countCadastrar == 1);

        Produto produtoDB = produtoDAO.buscar(produto.getCodigo());
        produtoDB.setNome("New Produto Test");
        produtoDB.setCodigo("20");
        produtoDB.setValor(20000L);

        Integer countUpdate = produtoDAO.atualizar(produtoDB);
        Assert.assertTrue(countUpdate == 1);

        Produto produtoUpdate = produtoDAO.buscar(produtoDB.getCodigo());
        Assert.assertEquals(produtoDB.getId(), produtoUpdate.getId());

        Integer countDelete = produtoDAO.excluir(produtoDB);
        Assert.assertTrue(countDelete == 1);
    }
}
