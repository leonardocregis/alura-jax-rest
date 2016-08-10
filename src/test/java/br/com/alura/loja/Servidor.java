package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {

	public static void main(String[] args){
        URI uri = URI.create("http://localhost:8099/");
        ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
		System.out.println("Servidor rodando");
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
