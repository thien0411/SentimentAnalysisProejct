
package source;

public class TwitterUser {

	protected int id;
	protected String handle;
	protected String name;
	protected String overallSentiment;
	Tweet[] tweets = null;
	
	
	
	/*
	public Tweet getTweets(){
		
		
		return;
		
	}

	*/
	
	public TwitterUser(int id, String handle, String name, String overallSentiment) {
		super();
		this.id = id;
		this.handle = handle;
		this.name = name;
		this.overallSentiment = overallSentiment;
	}



	public String getHandle() {
		return handle;
	}



	public void setHandle(String handle) {
		this.handle = handle;
	}





	public void get(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}


	public String getOverallSentiment() {
		return overallSentiment;
	}



	public void setOverallSentiment(String overallSentiment) {
		this.overallSentiment = overallSentiment;
	}



	public int getId() { return id; }



	public void setId(int id) { this.id = id; }
	

}