package br.com.fiap.acoes;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

import br.com.fiap.helpers.Acesso;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;

public class Buscar implements ExecutarAcao {

	private StringBuffer tweet;
	
	@Override
	public void executaAcao(String hashtag) {
		tweet  = new StringBuffer();
		tweet.append("@michelpf ");
		tweet.append(hashtag + "\n");
		int totalTweets = 0;
		int totalRT = 0;
		int totalFavoritos = 0;
		long maxID = -1;
		ArrayList<Status> listaTweets = new ArrayList<Status>();

		try {

			Twitter twitter = Acesso.recuperaContextoTwitter();

			Map<String, RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus("search");
			RateLimitStatus searchTweetsRateLimit = rateLimitStatus.get("/search/tweets");
			for (int queryNumber = 0; queryNumber < 150; queryNumber++) {
				if (searchTweetsRateLimit.getRemaining() == 0) {
					// Theread de 2s para esperar o limite do Twitter
					Thread.sleep((searchTweetsRateLimit.getSecondsUntilReset() + 2) * 1000l);
				}

				Query q = new Query(hashtag);

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate dataUntil = LocalDate.now().plusDays(1); // adiciona 1 dia para pegar o dia atual
				LocalDate dataSince = LocalDate.now().minusDays(5); // 5 dias atras da data atual (7 dias ate data
																	// atual)

				q.setSince(dataSince.format(formatter));
				q.setUntil(dataUntil.format(formatter));

				q.setCount(100); // Limite de tweets

				if (maxID != -1) {
					q.setMaxId(maxID - 1);
				}

				QueryResult r = twitter.search(q);

				if (r.getTweets().size() == 0) {
					break;
				}

				for (Status s : r.getTweets()) {
					listaTweets.add(s);

					if (maxID == -1 || s.getId() < maxID) {
						maxID = s.getId();
					}

					totalRT += s.getRetweetCount();
					totalFavoritos += s.getFavoriteCount();
				}

				searchTweetsRateLimit = r.getRateLimitStatus();
			}

			//ordena por ordem crescente de data
			listaTweets.sort((p1, p2) -> p1.getCreatedAt().compareTo(p2.getCreatedAt()));
			
			SimpleDateFormat format = new SimpleDateFormat("dd");
			String primeiraData = format.format( listaTweets.get(0).getCreatedAt());
			
			for (Status s : listaTweets) {
				if(!primeiraData.equals(format.format(s.getCreatedAt()))) {
				
					tweet.append("Dia:" + primeiraData);
					tweet.append(" Qtd:"+ totalTweets);
					tweet.append(" RTs:" + totalRT);
					tweet.append(" Fav:"+ totalFavoritos  + " \n");
					
					totalFavoritos = 0;
					totalRT = 0;
					totalTweets = 0;
					primeiraData = format.format(s.getCreatedAt());
					
				}
				
				totalRT += s.getRetweetCount();
				totalFavoritos += s.getFavoriteCount();
				totalTweets++;
				
			}
			
			//pegar os do ultimo dia
			tweet.append("Dia:" + primeiraData);
			tweet.append(" Qtd:"+ totalTweets);
			tweet.append(" RTs:" + totalRT);
			tweet.append(" Fav:"+ totalFavoritos  + " \n");
			
					
			// ordenando por data
			listaTweets.sort((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));
			//tweets mais novo e antigo
			format = new SimpleDateFormat("dd/MM/yyyy");
			tweet.append(format.format(listaTweets.get(0).getCreatedAt())  + "\n");
			tweet.append(format.format(listaTweets.get(listaTweets.size() - 1).getCreatedAt()) + "\n");
	
			// ordenando por nome
			listaTweets.sort((p1, p2) -> p1.getUser().getName().compareTo(p2.getUser().getName()));

			tweet.append(listaTweets.get(0).getUser().getName() + "\n");
			tweet.append(listaTweets.get(listaTweets.size() - 1).getUser().getName() + "\n");
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(tweet);

	}
	public StringBuffer getTweet() {
		return tweet;
	}

	public void setTweet(StringBuffer tweet) {
		this.tweet = tweet;
	}

}
