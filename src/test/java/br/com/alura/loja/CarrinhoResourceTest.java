package br.com.alura.loja;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Path;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.resource.CarrinhoResource;

public class CarrinhoResourceTest extends ServerTest {

	private static final String RUA_TESTE = "Rua Teste";
	private String path;

	private String getPathtoResource() {
		if (this.path == null) {
			this.path = getAnotationValue(CarrinhoResource.class);
		}
		return this.path;
	}
	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {

		WebTarget target = getTarget();
		CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
		long idCarrinho = 1l;
		Carrinho carrinho = carrinhoDAO.busca(idCarrinho);
		String conteudo = target.path("/" + getPathtoResource()+"/"+idCarrinho).request().get(String.class);
		Assert.assertTrue(conteudo.contains(carrinho.getRua()));

	}
	
	static String getAnotationValue(@SuppressWarnings("rawtypes") Class klass) {
		Pattern p = Pattern.compile("value=(.*)\\)");
		@SuppressWarnings("unchecked")
		Matcher m = p.matcher(klass.getAnnotation(Path.class).toString());
		String result= " not found";
		if ( m.find()){
			 result = m.group(1);
		}
		return result;
	}
	
	@Test
	public void testAdicaoDeCarrinho(){
		
		Carrinho carrinho = new Carrinho();
		carrinho.setRua(RUA_TESTE);
		Entity<String> entity  = Entity.entity(carrinho.toXML(),MediaType.APPLICATION_XML);
		Response response = getTarget().path("/"+getPathtoResource()).request().post(entity);
		Assert.assertEquals(response.getStatus(),201);
		String stringResponse=  response.getHeaderString("Location");
		long idCarrinho =extraiId(stringResponse);
		carrinho = new CarrinhoDAO().busca(new Long(idCarrinho));
		String conteudo = getTarget().path("/" + getPathtoResource()+"/"+idCarrinho).request().get(String.class);
		Assert.assertTrue(conteudo.contains(RUA_TESTE));

		
	}

}
