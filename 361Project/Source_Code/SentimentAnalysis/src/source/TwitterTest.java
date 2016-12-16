package source;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import java.util.List;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import java.util.Arrays;
import java.util.*;
import java.io.*;


public class TwitterTest {
	public static void main(String[] args) throws TwitterException {
//        TwitterUser tu = new TwitterUser(-1, "FoxNews", "Fox News", null);
//        int id = TweetConnector.persistUser(tu);
//        tu.setId(id);
        System.setErr(new PrintStream((new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        })));
        TweetConnector.persistTweets(TweetConnector.pullTweets(args[0]));
        TwitterUser user;
        double tWeight =0.70, eWeight = 0.2, hWeight = 0.1;
        //Check for options
        for(int i = 0; i< args.length; i++){
            if(args[i].equals("-t")){
                tWeight = Double.parseDouble(args[i + 1]);
                i++;
            } else if (args[i].equals("-e")) {
                eWeight = Double.parseDouble(args[i + 1]);
                i++;
            } else {
                if (args[i].equals("-h")) {
                    hWeight = Double.parseDouble(args[i + 1]);
                    i++;
                }
            }
        }
        if(args.length > 0){
            System.out.println(args[0]);
            user = TweetConnector.getUser(args[0]);
            List<source.Tweet> tweets = TweetConnector.getTweets(user);
            TweetSentimentAnalysis TSA = new TweetSentimentAnalysis(null, eWeight,hWeight, tWeight, null);
            double count = 0.0;
            TweetParser tp = new TweetParser();
            System.out.printf("Analysing tweets using the following weights; text=%f,emoji=%f,hashtag=%f\n", tWeight, eWeight, hWeight);
            for (Tweet tweet:tweets
                 ) {

                if(tweet.getEmojis().equals("")){
                    tweet.setEmojis(tp.parseEmoji(tweet));
                }
                if(tweet.getHashtags().equals("")){
                    tweet.setHashtags(tp.parseHashtag(tweet));
                }
                if(tweet.getPlainText().equals("")){
                    tweet.setPlainText(tp.parseText(tweet));
                }
                TSA.setTweet(tweet);
                double sentiment = TSA.analyzeTweet();

                System.out.printf("%s\nSentiment: %s\n", tweet.getOriginalPost(), TSA.SentimentConverter(sentiment));
                count += sentiment;
            }
            double overall = count/tweets.size();
            System.out.printf("\n\nOverall Sentiment: %s", TSA.SentimentConverter(overall));
        }else{
            printUsage();
        }



	}
    public static void printUsage(){
        System.out.println("Usage: TwitterTest userHandle [textWeight emojiWeight hashtagWeight]");
    }

	public static String calculateSentiment(String tweet){
		/*TwitterUser andrew = new TwitterUser(0, "HuskersRock", "Andrew Schmidt", "");
		Sentiment angry = new Sentiment("Angry", 44);
		KeyWord[] dict = {new KeyWord("Angry", 22, angry),new KeyWord("!", 3, angry)};
		String[] emojis = {"1f1e8", "1f1e8", "1f1e8"};
		String[] hashtags = {"nebraska", "huskers"};
		TwitterUser[] toUsers = {andrew};
		Tweet angryTweet = new Tweet(0, andrew, null, emojis, hashtags, "This is MAD ANDREW we should make all polling stations have big jaws ads in front of the voter!!!!", "TEST", null, false);
		TweetSentimentAnalysis TSA = new TweetSentimentAnalysis(angryTweet,33,33,33,toUsers,dict );
		int test = TSA.getEmojiWeight();
		NLP.init();
		int sentimentRes = NLP.findSentiment(angryTweet.getPlainText());
		System.out.println(angryTweet.getPlainText()+ " : "+ sentimentRes);*/
        NLP.init();
        int sentiment = NLP.findSentiment(tweet);

        if(sentiment == 0){
            return "Very Negative";
        }else if(sentiment == 1){
            return "Negative";
        }else if(sentiment == 2){
            return "Neutral";
        }else if(sentiment == 3){
            return "Positive";
        }else if(sentiment == 4){
            return "Very Positive";
        }
        return "unknown";

	}

}
