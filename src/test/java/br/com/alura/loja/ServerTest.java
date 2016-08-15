package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

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
		Client client = ClientBuilder.newClient();
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

	
	
}