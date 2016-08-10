package br.com.alura.loja;

import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Assert;
import org.junit.Test;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;
import br.com.alura.loja.resource.ProjetoResource;

public class ProjetoResourceTest extends ServerTest  {


	@Test
	public void testaQueAConexaoComOServidorFunciona() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://"+Servidor.SERVER+":" +Servidor.PORTA);
		String path = ProjetoResource.class.getAnnotation(Path.class).value();
		ProjetoDAO projetoDAO = new ProjetoDAO();
		long idProjeto = 1l;
		Projeto p = projetoDAO.busca(idProjeto);
		String conteudo = target.path("/" + path+"/"+idProjeto).request().get(String.class);
		Assert.assertTrue(conteudo.contains(p.getNome()));
	}

}
