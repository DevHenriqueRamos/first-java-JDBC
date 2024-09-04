package br.com.hramos;

import br.com.hramos.dao.generic.jdbc.dao.ClienteDAO;
import br.com.hramos.dao.generic.jdbc.dao.IClienteDAO;
import br.com.hramos.domain.Cliente;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ClienteTest {

    private IClienteDAO clienteDAO;


    @Test
    public void cadastrarTest() throws Exception {
        clienteDAO = new ClienteDAO();

        Cliente cliente = new Cliente();
        cliente.setCodigo("10");
        cliente.setNome("Henrique Ramos");
        Integer countCadastro = clienteDAO.cadastrar(cliente);
        Assert.assertTrue(countCadastro == 1);

        Cliente clienteDB = clienteDAO.buscar("10");
        Assert.assertNotNull(clienteDB);
        Assert.assertEquals(cliente.getCodigo(), clienteDB.getCodigo());
        Assert.assertEquals(cliente.getNome(), clienteDB.getNome());

        Integer countDel = clienteDAO.excluir(clienteDB);
        Assert.assertTrue(countDel == 1);
    }

    @Test
    public void buscarTodosTest() throws Exception {
        clienteDAO = new ClienteDAO();

        Cliente cliente1 = new Cliente();
        cliente1.setCodigo("10");
        cliente1.setNome("Henrique Ramos");
        Integer countCadastro1 = clienteDAO.cadastrar(cliente1);
        Assert.assertTrue(countCadastro1 == 1);

        Cliente cliente2 = new Cliente();
        cliente2.setCodigo("20");
        cliente2.setNome("Jhon Doe");
        Integer countCadastro2 = clienteDAO.cadastrar(cliente2);
        Assert.assertTrue(countCadastro2 == 1);

        List<Cliente> list = clienteDAO.buscarTodos();
        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        int countDel = 0;
        for (Cliente cliente : list) {
            clienteDAO.excluir(cliente);
            countDel++;
        }
        Assert.assertEquals(list.size(), countDel);

        list = clienteDAO.buscarTodos();
        Assert.assertEquals(list.size(), 0);
    }

    @Test
    public void atualizarTest() throws Exception {
        clienteDAO = new ClienteDAO();

        Cliente cliente = new Cliente();
        cliente.setCodigo("10");
        cliente.setNome("Henrique Ramos");
        Integer countCadastro = clienteDAO.cadastrar(cliente);
        Assert.assertTrue(countCadastro == 1);

        Cliente clienteDB = clienteDAO.buscar("10");
        Assert.assertNotNull(clienteDB);
        Assert.assertEquals(cliente.getCodigo(), clienteDB.getCodigo());
        Assert.assertEquals(cliente.getNome(), clienteDB.getNome());

        clienteDB.setCodigo("20");
        clienteDB.setNome("John Doe");
        Integer countUpdate = clienteDAO.atualizar(clienteDB);
        Assert.assertTrue(countUpdate == 1);

        Cliente clienteUpdated = clienteDAO.buscar("20");
        Assert.assertNotNull(clienteDB);
        Assert.assertEquals(clienteUpdated.getCodigo(), "20");
        Assert.assertEquals(clienteUpdated.getNome(), "John Doe");

        Integer countDel = clienteDAO.excluir(clienteDB);
        Assert.assertTrue(countDel == 1);

    }
}
