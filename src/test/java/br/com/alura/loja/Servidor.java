package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor implements ServerConfigs{

	private URI uri;
	private ResourceConfig config;

	public Servidor(){
		this.uri = URI.create("http://"+SERVER+":"+PORTA+"/");
		this.config = new ResourceConfig().packages(PATH_TO_PACKAGES);
	}

	public HttpServer createInstance(){
		return GrizzlyHttpServerFactory.createHttpServer(uri, config);
	}
	
	public static void main(String[] args){
		System.out.println("Servidor rodando");
		Servidor servidor = new Servidor();
		HttpServer server = servidor.createInstance();
	    try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			server.stop();
			System.out.println("Servidor parado");
		}
		
	}
}
