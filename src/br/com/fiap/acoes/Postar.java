package br.com.fiap.acoes;

import br.com.fiap.helpers.Acesso;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Postar implements ExecutarAcao{

	@Override
	public void executaAcao(String tweet) {
				
		try { 
			Twitter twitter = Acesso.recuperaContextoTwitter();						
			twitter.updateStatus(tweet);
			System.out.println("Tweet postado com sucesso!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
