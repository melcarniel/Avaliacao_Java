package br.com.fiap.helpers;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Acesso {

	private static final String TOKEN = "52464434-iYgAGPA8WcOU7TC61cslr5SO5OgV6vnrLG0UdTPF8";
	private static final String TOKEN_SECRET = "oGAFFgJxn4DMRAFaEwju45SXoBrI3jYxF5NHwej2P3tZt";

	private static final String OAUTH_1 = "twwKFl2EtiOcRNenUIPsXJJpZ";
	private static final String OAUTH_2 = "gsCExNLcMT07KGs1jsxJnh9p5qX6biLaFEF1IASiA34GbgMvap";

	private static Twitter twitter;

	public static Twitter recuperaContextoTwitter() {

		if (twitter == null) {

			TwitterFactory factory = new TwitterFactory();
			AccessToken accessToken = new AccessToken(TOKEN, TOKEN_SECRET);
			twitter = factory.getSingleton();
			twitter.setOAuthConsumer("twwKFl2EtiOcRNenUIPsXJJpZ", "gsCExNLcMT07KGs1jsxJnh9p5qX6biLaFEF1IASiA34GbgMvap");
			twitter.setOAuthAccessToken(accessToken);

		}

		return twitter;

	}

}
