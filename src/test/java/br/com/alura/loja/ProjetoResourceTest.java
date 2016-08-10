package br.com.alura.loja;

import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;
import br.com.alura.loja.resource.ProjetoResource;

public class ProjetoResourceTest implements ServerConfigs {

	private HttpServer httpserver;

	@Before
	public void init (){
		httpserver = new Servidor().createInstance();
	}
	
	@After
	public void finish(){
		httpserver.stop();
	}
	
	@Test
	public void testaQueAConexaoComOServidorFunciona() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:" + PORTA);
		String path = ProjetoResource.class.getAnnotation(Path.class).value();
		ProjetoDAO projetoDAO = new ProjetoDAO();
		Projeto p = projetoDAO.busca(1l);
		String conteudo = target.path("/" + path).request().get(String.class);
		Assert.assertTrue(conteudo.contains(p.getNome()));
	}

}
