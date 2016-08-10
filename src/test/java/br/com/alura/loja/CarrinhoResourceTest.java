package br.com.alura.loja;

import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Assert;
import org.junit.Test;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.resource.CarrinhoResource;

public class CarrinhoResourceTest extends ServerTest {

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://" + Servidor.SERVER + ":"+ Servidor.PORTA);
		String path = CarrinhoResource.class.getAnnotation(Path.class).value();
		CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
		long idCarrinho = 1l;
		Carrinho carrinho = carrinhoDAO.busca(idCarrinho);
		String conteudo = target.path("/" + path+"/"+idCarrinho).request().get(String.class);
		System.out.println(conteudo);
		Assert.assertTrue(conteudo.contains(carrinho.getRua()));

	}
}
