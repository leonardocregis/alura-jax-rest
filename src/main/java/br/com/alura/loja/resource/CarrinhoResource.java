package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

import com.thoughtworks.xstream.XStream;

@Path("carrinhos")
public class CarrinhoResource {

	private final String PATH;

	public CarrinhoResource() {
		StringBuilder sb = new StringBuilder();
		sb.append("/")
				.append(CarrinhoResource.class.getAnnotation(Path.class)
						.value()).append("/");
		this.PATH = sb.toString();
	}

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Carrinho busca(@PathParam("id") long id) {
		CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
		Carrinho carrinho = carrinhoDAO.busca(id);
		if (carrinho ==null)
			throw new WebApplicationException(Status.NOT_FOUND);
		return carrinho;
	}

	@POST
	@Produces(MediaType.APPLICATION_XML)
	public Response adiciona(Carrinho carrinho) {
		new CarrinhoDAO().adiciona(carrinho);
		URI uri = URI.create(PATH + carrinho.getId());
		return Response.created(uri).build();
	}

	@Path("{id}/produtos/{produtoId}")
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response alteraProduto(@PathParam("id") long id,@PathParam("produtoId") long produtoId, String conteudo) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		if ( carrinho == null )
			throw new WebApplicationException(Status.NOT_FOUND);
		Produto produto = (Produto) new XStream().fromXML(conteudo);
		carrinho.troca(produto);
		return Response.ok().build();
	}

	@PUT
	@Path("{id}/produtos/{produtoId}/quantidade")
	@Consumes(MediaType.APPLICATION_XML)
	public Response alteraQuantidadeProduto(@PathParam("id")long id, @PathParam("produtoId")long produtoId,String conteudo){
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        if (carrinho == null)
        	throw new WebApplicationException(Status.NOT_FOUND);
        Produto produto = (Produto) new XStream().fromXML(conteudo);
        carrinho.trocaQuantidade(produto);
        return Response.ok().build();
	}
	
}
