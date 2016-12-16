package source;

public class Tweet {

	protected long id;
	protected TwitterUser user;
	protected TwitterUser atUser;
	protected String [] emojis;
	protected String [] hashtags;
	protected String plainText;
	protected String originalPost;
	protected double Sentiment;
	protected boolean isAnalyzed;
	
	
	public Tweet(long id, TwitterUser user, TwitterUser atUser, String[] emojis, String[] hashtags, String plainText,
			String originalPost, double sentiment, boolean isAnalyzed) {
		super();
		this.id = id;
		this.user = user;
		this.atUser = atUser;
		this.emojis = emojis;
		this.hashtags = hashtags;
		this.plainText = plainText;
		this.originalPost = originalPost;
		this.Sentiment = sentiment;
		this.isAnalyzed = isAnalyzed;
	}

	
	
	public TwitterUser getUser() {
		return user;
	}

	public void setUser(TwitterUser user) {
		this.user = user;
	}

	public TwitterUser getAtUser() {
		return atUser;
	}

	public void setAtUser(TwitterUser atUser) {
		this.atUser = atUser;
	}

	public String getEmojis() {
		String concat = "";
		if(this.emojis != null) {
			for (String emoji : this.emojis
					) {
				concat += emoji;
			}
		}
		return concat;
	}

	public void setEmojis(String[] emojis) {
		this.emojis = emojis;
	}

	public String getHashtags() {
		String concat = "";
		if(this.hashtags != null){
			for (String hashtag:this.hashtags
					) {
				concat += hashtag;
			}
		}
		return concat;
	}

	public void setHashtags(String[] hashtags) {
		this.hashtags = hashtags;
	}

	public String getPlainText() {
		if(plainText == null){
			plainText = "";
		}
		return plainText;
	}

	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}

	public String getOriginalPost() {
		return originalPost;
	}

	public void setOriginalPost(String originalPost) {
		this.originalPost = originalPost;
	}

	public double getSentiment() {
		return Sentiment;
	}

	public void setSentiment(double sentiment) {
		Sentiment = sentiment;
	}

	public boolean isAnalyzed() {
		return isAnalyzed;
	}

	public void setAnalyzed(boolean isAnalyzed) {
		this.isAnalyzed = isAnalyzed;
	}

	public long getId() { return id; }

	public void setId(int id) { this.id = id; }

	
	
	
	
	

}
