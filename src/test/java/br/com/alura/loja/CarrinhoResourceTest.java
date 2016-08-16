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
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.resource.CarrinhoResource;

public class CarrinhoResourceTest extends ServerTest {

	private static final int QUANTIDADE_CARRINHO_20 = 20;
	private static final int QUANTIDADE_PRODUTO_10 = 10;
	private static final Double VALOR_PRODUTO_100 = 100.00;
	private static final String NOME_PRODUTO = "Nome Produto";
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
		Carrinho conteudo = target.path("/" + getPathtoResource()+"/"+idCarrinho).request().get(Carrinho.class);
		Assert.assertTrue(conteudo.getRua().equals(carrinho.getRua()));

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

	@Test
	public void testAlteraCarrinho(){
		Carrinho carrinho = new Carrinho();
		carrinho.setRua(RUA_TESTE);
		carrinho.adiciona(new Produto(0, NOME_PRODUTO, VALOR_PRODUTO_100, QUANTIDADE_PRODUTO_10));

		Entity<String> entity  = Entity.entity(carrinho.toXML(),MediaType.APPLICATION_XML);
		Response response = getTarget().path("/"+getPathtoResource()).request().post(entity);
		Assert.assertEquals(response.getStatus(),201);
		String stringResponse=  response.getHeaderString("Location");
		long idCarrinho =extraiId(stringResponse);
		carrinho = new CarrinhoDAO().busca(new Long(idCarrinho));
		Produto produtoCarrinho = carrinho.getProdutos().get(0);
		produtoCarrinho.setQuantidade(QUANTIDADE_CARRINHO_20);
		entity  = Entity.entity(carrinho.toXML(),MediaType.APPLICATION_XML);
		response = getTarget().path("/" + getPathtoResource()+"/"+idCarrinho+"/produtos/"+produtoCarrinho.getId()).request(MediaType.APPLICATION_XML).post(entity);
		
		carrinho = new CarrinhoDAO().busca(new Long(idCarrinho));
		produtoCarrinho = carrinho.getProdutos().get(0);
		Assert.assertEquals(QUANTIDADE_CARRINHO_20,produtoCarrinho.getQuantidade());
	}
	
	@Test
	public void testAlteraCarrinhoEspecificaQuantidade(){
		Carrinho carrinho = new Carrinho();
		carrinho.setRua(RUA_TESTE);
		carrinho.adiciona(new Produto(0, NOME_PRODUTO, VALOR_PRODUTO_100, QUANTIDADE_PRODUTO_10));

		Entity<String> entity  = Entity.entity(carrinho.toXML(),MediaType.APPLICATION_XML);
		Response response = getTarget().path("/"+getPathtoResource()).request().post(entity);
		Assert.assertEquals(response.getStatus(),201);
		String stringResponse=  response.getHeaderString("Location");
		long idCarrinho =extraiId(stringResponse);
		carrinho = new CarrinhoDAO().busca(new Long(idCarrinho));
		Produto produtoCarrinho = carrinho.getProdutos().get(0);
		produtoCarrinho.setQuantidade(QUANTIDADE_CARRINHO_20);
		entity  = Entity.entity(carrinho.toXML(),MediaType.APPLICATION_XML);
		response = getTarget().path("/" + getPathtoResource()+"/"+idCarrinho+"/produtos/"+produtoCarrinho.getId()+"/quantidade").request(MediaType.APPLICATION_XML).post(entity);
		
		carrinho = new CarrinhoDAO().busca(new Long(idCarrinho));
		produtoCarrinho = carrinho.getProdutos().get(0);
		Assert.assertEquals(QUANTIDADE_CARRINHO_20,produtoCarrinho.getQuantidade());		
	}
	
}
