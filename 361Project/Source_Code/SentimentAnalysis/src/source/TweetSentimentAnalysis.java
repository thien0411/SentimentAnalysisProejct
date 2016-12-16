package source;

public class TweetSentimentAnalysis {
	
	protected Tweet tweet;
	
	protected double emojiWeight ;
	protected double hashtagWeight ;
	protected double textWeight;


	/**
	 * Constructor for the tweet sentiment analysis
	 *
	 * @param tweet
	 * @param emojiWeight
	 * @param hashtagWeight
	 * @param textWeight
	 * @param atUsers
	 */
	public TweetSentimentAnalysis(Tweet tweet, double emojiWeight, double hashtagWeight, double textWeight,
			TwitterUser[] atUsers) {
		super();
		this.tweet = tweet;
		this.emojiWeight = emojiWeight;
		this.hashtagWeight = hashtagWeight;
		this.textWeight = textWeight;
	}

	/**
	 * uses NLP to analyze a tweet
	 * @return the sentiment (floating point value from 0 to 4)
	 */
	public double analyzeTweet(){
		String plainText = this.tweet.getPlainText();
		NLP.init();
		int plainTextSentiment, emojiTextSentiment, hashtagTextSentiment;
		if(this.tweet.getPlainText() == ""){
			plainTextSentiment = 2;
		}else{
			plainTextSentiment = NLP.findSentiment(this.tweet.getPlainText());
		}
		if(this.tweet.getEmojis().equals("")){
			emojiTextSentiment = plainTextSentiment;
		}else{
			emojiTextSentiment = NLP.findSentiment(this.tweet.getEmojis());
		}
		if(this.tweet.getHashtags().equals("")){
			hashtagTextSentiment = plainTextSentiment;
		}else{
			hashtagTextSentiment = NLP.findSentiment(this.tweet.getHashtags());
		}
		System.out.printf("\nPlain: %d\nEmoji: %d\nHashtag: %d\n", plainTextSentiment, emojiTextSentiment, hashtagTextSentiment);
		return ((double)plainTextSentiment*textWeight +
				(double)emojiTextSentiment*emojiWeight +
				(double)hashtagTextSentiment*hashtagWeight);

	}
	public String SentimentConverter(double sentiment){
		String retVal = Double.toString(sentiment) + " - ";
		sentiment = Math.ceil(sentiment);
		if(sentiment == 0){
			retVal += "Very Negative";
		}else if(sentiment == 1){
			retVal += "Negative";
		}else if(sentiment == 2){
			retVal += "Neutral";
		}else if(sentiment == 3){
			retVal += "Positive";
		}else if(sentiment == 4){
			retVal += "Very Positive";
		}
		return retVal;
	}

	public Tweet getTweet() {
		return tweet;
	}


	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}


	public double getEmojiWeight() {
		return emojiWeight;
	}


	public void setEmojiWeight(double emojiWeight) {
		this.emojiWeight = emojiWeight;
	}


	public double getHashtagWeight() {
		return hashtagWeight;
	}


	public void setHashtagWeight(double hashtagWeight) {
		this.hashtagWeight = hashtagWeight;
	}


	public double getTextWeight() {
		return textWeight;
	}


	public void setTextWeight(double textWeight) {
		this.textWeight = textWeight;
	}

	
	
}
