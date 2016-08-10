package br.com.alura.loja.modelo;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

public class Projeto implements Convertivel{
	
	private String nome;
	long id;
	int anoDeInicio;
	
	public Projeto(String nome, long id, int anoDeInicio) {
		this.nome = nome;
		this.id = id;
		this.anoDeInicio = anoDeInicio;
	}

	public String getNome() {
		return nome;
	}
	public long getId() {
		return id;
	}
	public int getAnoDeInicio() {
		return anoDeInicio;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setAnoDeInicio(int anoDeInicio) {
		this.anoDeInicio = anoDeInicio;
	}

	public String toXML(){
		return new XStream().toXML(this);
	}

	public String toJSON() {
		return new Gson().toJson(this);
	}
	
	
}
