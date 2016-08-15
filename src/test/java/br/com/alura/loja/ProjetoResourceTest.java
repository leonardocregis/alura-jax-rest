package br.com.alura.loja;

import javax.ws.rs.Path;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;
import br.com.alura.loja.resource.ProjetoResource;

public class ProjetoResourceTest extends ServerTest  {


	@Test
	public void testaQueAConexaoComOServidorFunciona() {
		WebTarget target = geraWebTarget();
		String path = ProjetoResource.class.getAnnotation(Path.class).value();
		ProjetoDAO projetoDAO = new ProjetoDAO();
		long idProjeto = 1l;
		Projeto p = projetoDAO.busca(idProjeto);
		String conteudo = target.path("/" + path+"/"+idProjeto).request().get(String.class);
		Assert.assertTrue(conteudo.contains(p.getNome()));
	}

	@Test
	public void testAdicaoDeProjeto(){
		String path = ProjetoResource.class.getAnnotation(Path.class).value();
		
		Projeto projeto = new Projeto("Teste1", 0, 2016);
		Entity<String> entity  = Entity.entity(projeto.toXML(),MediaType.APPLICATION_XML);
		Response response = getTarget().path("/"+path).request().post(entity);
		Assert.assertEquals("<status>sucesso</status>", response.readEntity(String.class));
		
	}
}
