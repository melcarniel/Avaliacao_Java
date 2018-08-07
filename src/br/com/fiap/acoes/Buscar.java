package br.com.fiap.acoes;

import br.com.fiap.helpers.Acesso;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Buscar implements ExecutarAcao{
	
	private String hashtag;

	@Override
	public void executaAcao() {
		
		try {
			
			Twitter twitter = Acesso.recuperaContextoTwitter();
			
			Query query = new Query(hashtag);
			query.setSince("2018-07-01");
			query.setUntil("2018-07-31");
			QueryResult result;
			int contador = 0;
			
			result = twitter.search(query);
			
			while(result.hasNext()) {
				query = result.nextQuery();
				
				for (Status status : result.getTweets()) {
					contador ++;
					System.out.println("Usuário: " + status.getUser().getScreenName());
					System.out.println("Mensagem: " + status.getText());
					System.out.println("Data de Criação: " + status.getCreatedAt());
					System.out.println("Número de Favoritos: " + status.getFavoriteCount());
					System.out.println("Geolocalização: " + status.getGeoLocation());
					System.out.println("Lugar: " + status.getPlace());
					System.out.println("Número de Retweets: " + status.getRetweetCount());
				}
				result = twitter.search(query);
			}
			
			System.out.println("Tag ???: " + contador + " tweets");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

}
