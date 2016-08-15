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
		String path = ProjetoResource.class.getAnnotation(Path.class).value();
		ProjetoDAO projetoDAO = new ProjetoDAO();
		long idProjeto = 1l;
		Projeto p = projetoDAO.busca(idProjeto);
		String conteudo = target.path("/" + path+"/"+idProjeto).request().get(String.class);
		Assert.assertTrue(conteudo.contains(p.getNome()));
	}

	@Test
	public void testAdicaoDeProjeto(){
		Projeto projeto = new Projeto(PROJETO_NOME, 0, 2016);
		
		Entity<String> entity  = Entity.entity(projeto.toXML(),MediaType.APPLICATION_XML);
		Response response = getTarget().path("/"+getPathtoResource()).request().post(entity);
		Assert.assertEquals(response.getStatus(),201);
		String stringResponse=  response.getHeaderString("Location");
		long idProjeto =extraiId(stringResponse);
		projeto = new ProjetoDAO().busca(new Long(idProjeto));
		String conteudo = getTarget().path("/" + getPathtoResource()+"/"+idProjeto).request().get(String.class);
		Assert.assertTrue(conteudo.contains(PROJETO_NOME));
		
	}
	

	private String getPathtoResource() {
		if (this.path == null) {
			this.path = getAnotationValue(ProjetoResource.class);
		}
		return this.path;
	}
}
