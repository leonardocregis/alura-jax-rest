package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.After;
import org.junit.Before;

public class ServerTest implements ServerConfigs{

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
		this.target = client.target("http://"+Servidor.SERVER+":" +Servidor.PORTA);
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