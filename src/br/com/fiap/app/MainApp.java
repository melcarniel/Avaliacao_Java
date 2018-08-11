package br.com.fiap.app;

import br.com.fiap.acoes.Buscar;
import br.com.fiap.acoes.ExecutarAcao;
import br.com.fiap.acoes.Postar;

public class MainApp {

	public static void main(String[] args) {		
		postar(buscar("#java9"));
	}
	
	private static String buscar(String hashtag) {
	
		Buscar acao = new Buscar();
		acao.executaAcao(hashtag);
		return acao.getTweet().toString();
		
	}
	
	private static void postar(String tweet) {
		Postar acao = new Postar();
		
		acao.executaAcao(tweet);
	}

}
