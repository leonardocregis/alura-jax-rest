package br.com.alura.loja;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Before;

public class ServerTest implements ServerConfigs{

	private static final String URI = "http://"+Servidor.SERVER+":" +Servidor.PORTA;
	private Servidor servidor;
	private WebTarget target;

	public ServerTest() {
		super();
	}

	@Before
	public void init() {
		getServidor().start();
	}

	@After
	public void finish() {
		this.servidor.stop();
	}

	protected WebTarget geraWebTarget() {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(new LoggingFilter());
		Client client = ClientBuilder.newClient(clientConfig);
		this.target = client.target(URI);
		return target;
	}

	protected WebTarget geraWebTarget(String subPath) {
		Client client = ClientBuilder.newClient();
		this.target = client.target(URI+"/"+subPath);
		return target;		
	}
	
	public Servidor getServidor() {
		if (servidor == null) {
			servidor = new Servidor();
		}
		return servidor;
		
	}

	public WebTarget getTarget() {
		if (target == null) {
			target = geraWebTarget();
		}
		return target;
	}

	protected long extraiId(String stringResponse) {
		Pattern p = Pattern.compile("/(\\d)");
		Matcher m = p.matcher(stringResponse);
		long idProjeto = 0;
		if (m.find()){
			idProjeto = Long.parseLong(m.group(1));
		}
		return idProjeto;
	}

	
	
}