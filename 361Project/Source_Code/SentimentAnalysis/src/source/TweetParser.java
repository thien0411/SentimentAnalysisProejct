package source;
import java.util.*;

public class TweetParser {
	
	protected Tweet tweet;

	
	public TweetParser() {
	}
	
	public String[] parseEmoji(Tweet tweet){
		List<String> emoji = new ArrayList<String>();
		String text = tweet.getOriginalPost();
		if(text.contains("\\u")){
			//if tweet post contains an emoji
			for (int i=0; i < text.length(); i++) {
				String emojiText = "";//keep track of the current emoji text
				//for every emoji go through and put emoji into list
				if (text.charAt(i) == '\\') { //if its an emoji
					for(int j = i; j+1 < text.length(); j++){ //start at emoji and grab the text
						if(text.charAt(j+1) != ' ') {
							emojiText += text.charAt(j);
						}
						else{
							break;
						}
					}
					emoji.add(TweetConnector.getEmojiText(emojiText));//add the current emoji to list
				}
			}
		}
		return emoji.toArray(new String[emoji.size()]);
	}

	
	public String [] parseHashtag(Tweet tweet){
		List<String> tags = new ArrayList<String>();
		String text = tweet.getOriginalPost();
		if(tweet.getOriginalPost().contains("#")){
			//if tweet post contains a hashtag
			for (int i=0; i < text.length(); i++) {
				String hashtext = "";//keep track of the current hashtag text
				//for every hashtag go through and put hashtag into array
				if (text.charAt(i) == '#') { //if its a hashtag
					for(int j = i; j+1 < text.length(); j++){ //start at hash symbol and grab the text
						if(text.charAt(j+1) != ' ') {
							hashtext += text.charAt(j+1);
						}
						else{
							break;
						}
					}
					tags.add(hashtext);//add the current hashtext to the hashtag array
				}
			}
		}
		
		return tags.toArray(new String[tags.size()]);
	}
	
	
	
	public String parseText(Tweet tweet){
		String text = tweet.getOriginalPost();
		//Remove all hashtags
		// \p{L} matches any unicode letter and '+' means one or more
		String textonly = text.replaceAll("#\\p{L}+", ""); //removes hash and space before it - will be a problem if tweets are not properly spaced
		//Remove @ mentions
		textonly = textonly.replaceAll("@\\p{L}+", "");
		textonly = textonly.replaceAll(" [^A-Za-z0-9] ", ""); //remove remaining nonalphanumeric characters
		textonly = textonly.replaceAll("[.]", "");
		textonly = textonly.replaceAll("[,]", "");
		textonly = textonly.replaceAll("[!]", "");
		textonly = textonly.replaceAll("[?]", "");
		return textonly;
	}
	
	public String[] parseAtUser(Tweet tweet){
		List<String> mentions = new ArrayList<String>();
		String text = tweet.getOriginalPost();
		if(tweet.getOriginalPost().contains("@")){
			//if tweet post contains an atUser
			for (int i=0; i < text.length(); i++) {
				String atMention = "";//keep track of the current atUser text
				//for every atMention go through and put mention into array
				if (text.charAt(i) == '@') {
					for(int j = i; j+1 < text.length(); j++){ //start at @ symbol and grab the text
						//trying to check if next character is nonalphanumeric
						//int nextChar = (int) text.charAt(i+1);(nextChar >= 48 && nextChar <= 57) || (nextChar>= 65 && nextChar <= 90) || (nextChar>=97 && nextChar<=122)
						if(text.charAt(j+1) != ' ') {//stuck with checking if nextchar is a space
							atMention += text.charAt(j+1);
						}
						else{
							break;
						}
					}
					mentions.add(atMention);//add the current mention into the array
				}
			}
		}

		return mentions.toArray(new String[mentions.size()]);
	}
	
	
	
	
	public Tweet getTweet() {
		return tweet;
	}


	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}


	
	

}
