package br.com.alura.loja.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;

import com.thoughtworks.xstream.XStream;


@Path("projetos")
public class ProjetoResource {

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String busca(@PathParam("id") long id){
		return new ProjetoDAO().busca(id).toJSON();
	}

	
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes
	public String adiciona(String conteudo){
        Projeto carrinho = (Projeto) new XStream().fromXML(conteudo);
        new ProjetoDAO().adiciona(carrinho);
        return "<status>sucesso</status>";
	}
}
