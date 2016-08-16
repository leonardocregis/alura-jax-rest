package br.com.alura.loja;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


	private static final String PROJETO_NOME = "Teste 1";
	private String path;

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
	public void testaQueAConexaoComOServidorFunciona() {
		WebTarget target = geraWebTarget();
		ProjetoDAO projetoDAO = new ProjetoDAO();
		long idProjeto = 1l;
		Projeto p = projetoDAO.busca(idProjeto);
		Response conteudo = target.path("/" + getPathtoResource()+"/"+idProjeto).request().get();
		Assert.assertTrue(conteudo.getEntity().toString().contains("1"));
	}

	@Test
	public void testAdicaoDeProjeto(){
		Projeto projeto = new Projeto(PROJETO_NOME, 0, 2016);
		
		Entity<Projeto> entity  = Entity.entity(projeto,MediaType.APPLICATION_XML);
		String pathToAdd = "/"+getPathtoResource();
		Response response = getTarget().path(pathToAdd).request(MediaType.APPLICATION_XML).post(entity);
		Assert.assertEquals(response.getStatus(),201);
		String stringResponse=  response.getHeaderString("Location");
		long idProjeto =extraiId(stringResponse);
		projeto = new ProjetoDAO().busca(new Long(idProjeto));
		String pathToGet = "/" + getPathtoResource()+"/"+idProjeto;
		response= getTarget().path(pathToGet).request(MediaType.APPLICATION_XML).get();
		String result = response.readEntity(String.class);
		Assert.assertTrue(result.contains(PROJETO_NOME));
		
	}
	
	@Test
	public void testRemocaoProjeto(){
		Projeto projeto = new Projeto(PROJETO_NOME, 0, 2016);
		Entity<Projeto> entity  = Entity.entity(projeto,MediaType.APPLICATION_XML);
		Response response = getTarget().path("/"+getPathtoResource()).request(MediaType.APPLICATION_XML).post(entity);
		Assert.assertEquals(response.getStatus(),201);
		String stringResponse=  response.getHeaderString("Location");
		long idProjeto =extraiId(stringResponse);
		projeto = new ProjetoDAO().busca(new Long(idProjeto));
		response =  getTarget().path("/" + getPathtoResource()+"/"+idProjeto).request().delete();
		Assert.assertEquals(200,response.getStatus());
		
		projeto  =  getTarget().path("/" + getPathtoResource()+"/"+idProjeto).request(MediaType.APPLICATION_XML).get(Projeto.class);
		Assert.assertEquals(200,response.getStatus());
		Assert.assertEquals(projeto, null);

	}

	private String getPathtoResource() {
		if (this.path == null) {
			this.path = getAnotationValue(ProjetoResource.class);
		}
		return this.path;
	}
}
