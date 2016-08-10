package br.com.alura.loja;

import org.junit.After;
import org.junit.Before;

public class ServerTest {

	private Servidor servidor;

	public ServerTest() {
		super();
	}

	@Before
	public void init() {
		this.servidor = new Servidor();
		this.servidor.start();
	}

	@After
	public void finish() {
		this.servidor.stop();
	}

}