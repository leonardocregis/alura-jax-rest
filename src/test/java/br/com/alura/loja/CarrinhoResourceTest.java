package br.com.alura.loja;

import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Projeto;
import br.com.alura.loja.resource.CarrinhoResource;
import br.com.alura.loja.resource.ProjetoResource;

public class CarrinhoResourceTest extends ServerTest {

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {

		WebTarget target = getTarget();
		String path = CarrinhoResource.class.getAnnotation(Path.class).value();
		CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
		long idCarrinho = 1l;
		Carrinho carrinho = carrinhoDAO.busca(idCarrinho);
		String conteudo = target.path("/" + path+"/"+idCarrinho).request().get(String.class);
		Assert.assertTrue(conteudo.contains(carrinho.getRua()));

	}
	
	@Test
	public void testAdicaoDeCarrinho(){
		//TODO adicionar teste para verificar ser realmente foi inserido o projeto, atualmente nao foi possivel pois teria que reorganizar o retorno trazendo o id do projeto inserido
		String path = CarrinhoResource.class.getAnnotation(Path.class).value();
		
		Carrinho carrinho = new Carrinho();
		Entity<String> entity  = Entity.entity(carrinho.toXML(),MediaType.APPLICATION_XML);
		Response response = getTarget().path("/"+path).request().post(entity);
		Assert.assertEquals("<status>sucesso</status>", response.readEntity(String.class));
		
	}
}
