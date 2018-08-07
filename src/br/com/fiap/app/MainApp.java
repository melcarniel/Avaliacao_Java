package br.com.fiap.app;

import br.com.fiap.acoes.Buscar;
import br.com.fiap.acoes.ExecutarAcao;
import br.com.fiap.acoes.Postar;

public class MainApp {

	public static void main(String[] args) {
		
		buscar("#java9");
		postar();

	}
	
	private static void buscar(String hashtag) {
	
		Buscar acao = new Buscar();
		acao.setHashtag(hashtag);
		acao.executaAcao();
		
	}
	
	private static void postar() {
		Postar acao = new Postar();
		acao.executaAcao();
	}

}
