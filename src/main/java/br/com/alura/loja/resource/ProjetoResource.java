package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;

import com.thoughtworks.xstream.XStream;


@Path("projetos")
public class ProjetoResource {

	
	private final  String PATH;


	public ProjetoResource(){
		StringBuilder sb = new StringBuilder();
		sb.append("/").append(ProjetoResource.class.getAnnotation(Path.class).value()).append("/");
		this.PATH = sb.toString();
	}
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id){
		Projeto projeto = new ProjetoDAO().busca(id);
		if (projeto !=null ){
			return projeto.toXML();
		}
		return "";
	}

	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(String conteudo){
        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        new ProjetoDAO().adiciona(projeto);
        URI uri = URI.create(PATH+projeto.getId());
        return Response.created(uri).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response removeProduto(@PathParam("id")long id){
		ProjetoDAO projetoDAO = new ProjetoDAO();
		projetoDAO.remove(id);
		return Response.ok().build();
	}
	
}
