package source;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides many static functions used to connect program to DB
 *
 * Created by Christian on 11/10/16.
 */

public class TweetConnector {


//    public static void main(String[] args){
//        TwitterUser user = new TwitterUser(1, "cjungck", "Christian Jungck", null);
//        List<Tweet> tweets = TweetConnector.getTweets(user);
//        for(Tweet tweet : tweets) {
//            System.out.println();
//            System.out.println(tweet.getUser().getName());
//            System.out.println(tweet.getUser().getHandle());
//            System.out.println(tweet.getOriginalPost());
//            System.out.println();
//        }
//    }



    /**
     * Grabs 100 most recent tweets for a given user
     *
     * @param user the Twitter user whose tweets you desire to read/analyze
     */
    public static List<Tweet> getTweets(TwitterUser user){
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Tweet> tweets = new ArrayList<>();

        System.out.println("Connecting...");
        Connection conn = DatabaseInfo.getConnection();
        System.out.println("Connected");

        System.out.println("Creating statement");

        String handle = user.getHandle();
        String query = "SELECT TWEETS.ID, TWEETS.ORIGINAL_TEXT, TWEETS.IS_ANALYZED, TWEETS.SENTIMENT " +
                "FROM TWEETS JOIN USERS ON TWEETS.USER_ID = USERS.ID " +
                "WHERE USERS.HANDLE = ? LIMIT 100";

        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, handle);
            //System.out.println(ps.toString());
            rs = ps.executeQuery();
            while(rs.next()) {
                tweets.add(new Tweet(rs.getInt("ID"), user, null, null, null, null,
                        rs.getString("ORIGINAL_TEXT"), rs.getDouble("SENTIMENT"), rs.getBoolean("IS_ANALYZED")));
            }
            rs.close();
            ps.close();
            conn.close();
            System.out.println("Is everything closed? (Should have three trues) " + rs.isClosed() + " " +
                ps.isClosed() + " " + conn.isClosed());
        } catch (SQLException e) {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally{
            //finally block used to close resources
            try {
                if(rs != null && !rs.isClosed())
                    rs.close();
                if(ps != null && !ps.isClosed())
                    ps.close();
                if(conn != null && !conn.isClosed())
                    conn.close();
            } catch (SQLException e) {
                System.out.println("SQLException: ");
                e.printStackTrace();
            }
        }
        return tweets;
    }

    public static void persistTweets(List<Tweet> tweets){
        PreparedStatement ps = null;
        ResultSet rs = null;

        System.out.println("Connecting...");
        Connection conn = DatabaseInfo.getConnection();
        System.out.println("Connected");

        System.out.println("Creating statement");

        String query = "INSERT INTO TWEETS (USER_ID, ORIGINAL_TEXT, IS_ANALYZED, SENTIMENT) VALUES (?,?,?,?)";

        try {
            for (Tweet tweet: tweets) {
                ps = conn.prepareStatement(query);
                ps.setInt(1, tweet.getUser().getId()); //must ensure that when user is persisted, the id is returned
                ps.setString(2, tweet.getOriginalPost());
                ps.setBoolean(3, tweet.isAnalyzed());
                ps.setDouble(4, tweet.getSentiment());
//                rs = ps.executeQuery();
                ps.executeUpdate();
            }
            //rs.close();
            ps.close();
            conn.close();
            System.out.println("Is everything closed? (Should have three trues) " + /*rs.isClosed() +*/ " " +
                    ps.isClosed() + " " + conn.isClosed());
        } catch (SQLException e) {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally{
            //finally block used to close resources
            try {
//                if(rs != null && !rs.isClosed())
//                    rs.close();
                if(ps != null && !ps.isClosed())
                    ps.close();
                if(conn != null && !conn.isClosed())
                    conn.close();
            } catch (SQLException e) {
                System.out.println("SQLException: ");
                e.printStackTrace();
            }
        }
    }

    /**
     * This function persists a user to the DB
     * @param user a Twitter user to persist
     * @return the DB-generated user ID so Java knows this (makes foreign key constraints easier to deal with)
     *  note:  a -1 return value indicates the user ID was not retrieved properly
     */
    public static int persistUser(TwitterUser user){
        int userID = -1;
        PreparedStatement ps = null;
        ResultSet rs = null;

        System.out.println("Connecting...");
        Connection conn = DatabaseInfo.getConnection();
        System.out.println("Connected");

        System.out.println("Creating statement");

        String query = "INSERT INTO USERS (HANDLE, NAME_OF_USER, OVERALL_SENTIMENT)" +
                " VALUES (?,?,?)";
        String query2 = "SELECT ID FROM USERS WHERE HANDLE = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, user.getHandle());
            ps.setString(2, user.getName());
            ps.setString(3, user.getOverallSentiment());
            //rs = ps.executeQuery();
            ps.executeUpdate();

            ps = conn.prepareStatement(query2);
            ps.setString(1, user.getHandle());
            rs = ps.executeQuery();
            while(rs.next()) {
                userID = rs.getInt("ID");
            }
            rs.close();
            ps.close();
            conn.close();
            System.out.println("Is everything closed? (Should have three trues) " + /*rs.isClosed() +*/ " " +
                    ps.isClosed() + " " + conn.isClosed());
        } catch (SQLException e) {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally{
            //finally block used to close resources
            try {
                if(rs != null && !rs.isClosed())
                    rs.close();
                if(ps != null && !ps.isClosed())
                    ps.close();
                if(conn != null && !conn.isClosed())
                    conn.close();
            } catch (SQLException e) {
                System.out.println("SQLException: ");
                e.printStackTrace();
            }
        }
        return userID;
    }

    /**
     *
     * @param handle the user's handle to search
     * @return the Twitter user with the handle passed into the method
     */
    public static TwitterUser getUser(String handle){
        TwitterUser user = null;
        String sql = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        System.out.println("Connecting...");
        Connection conn = DatabaseInfo.getConnection();
        System.out.println("Connected");

        System.out.println("Creating statement");

        String query = "SELECT * FROM USERS WHERE HANDLE = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, handle);
            rs = ps.executeQuery();
            while(rs.next()) {
                user = new TwitterUser(rs.getInt("ID"), rs.getString("HANDLE"), rs.getString("NAME_OF_USER"),
                        rs.getString("OVERALL_SENTIMENT"));
            }
            rs.close();
            ps.close();
            conn.close();
            System.out.println("Is everything closed? (Should have three trues) " + rs.isClosed() + " " +
                    ps.isClosed() + " " + conn.isClosed());
        } catch (SQLException e) {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally{
            //finally block used to close resources
            try {
                if(rs != null && !rs.isClosed())
                    rs.close();
                if(ps != null && !ps.isClosed())
                    ps.close();
                if(conn != null && !conn.isClosed())
                    conn.close();
            } catch (SQLException e) {
                System.out.println("SQLException: ");
                e.printStackTrace();
            }
        }
        return user;
    }

    public static String getEmojiText(String emojiCode){
        String emojiText = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        System.out.println("Connecting...");
        Connection conn = DatabaseInfo.getConnection();
        System.out.println("Connected");

        System.out.println("Creating statement");

        String query = "SELECT EMOJI_STRING FROM EMOJIS WHERE EMOJI_CODE = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, emojiCode);
            rs = ps.executeQuery();
            while(rs.next()) {
                emojiText = rs.getString("EMOJI_STRING");
            }
            rs.close();
            ps.close();
            conn.close();
            System.out.println("Is everything closed? (Should have three trues) " + rs.isClosed() + " " +
                    ps.isClosed() + " " + conn.isClosed());
        } catch (SQLException e) {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally{
            //finally block used to close resources
            try {
                if(rs != null && !rs.isClosed())
                    rs.close();
                if(ps != null && !ps.isClosed())
                    ps.close();
                if(conn != null && !conn.isClosed())
                    conn.close();
            } catch (SQLException e) {
                System.out.println("SQLException: ");
                e.printStackTrace();
            }
        }
        return emojiText;
    }

    public static List<Tweet> pullTweets(String handle) throws TwitterException {
        String name = handle;
        //oauth
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey("6ZWAC4ClE8QwFMxyqkbrpMP3Y").setOAuthConsumerSecret("PWJtI3NdV7Q5t2DF1VeyKvrQatJAzDWAFDH5brzDplG19cNMlN").setOAuthAccessToken("793831517441011712-L5jOU27p92EukTehxv7XrXfN7aVnLvw").setOAuthAccessTokenSecret("ZCHQMMDZureEMPvnkuJIqmTSGqVmeHDOzrdlsaa5qLSuU");
        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        //  handle = twitter.verifyCredentials().getScreenName();
        //First param of Paging() is the page number, second is the number per page
        Paging paging = new Paging(1, 20);
        //get a list of 100 statuses from CNN
        List<Status> statuses = twitter.getUserTimeline(handle, paging);
        List<Tweet> tweets = new ArrayList<Tweet>();
        TwitterUser tu = new TwitterUser(0, handle, name, null);
        tu.setId(TweetConnector.persistUser(tu));
        System.out.println("Showing @" + handle + "'s user timeline.");
        String[] temp = new String[10];

        for (Status status : statuses) {
            //For every status retrieved, make this into a tweet class
            Tweet t = new Tweet(status.getId(), tu, tu, temp, temp, "", status.getText(), -1, false );
            tweets.add(t);
            //TweetParser tp = new TweetParser(t, temp, temp, temp, status.getText());
            //tp.parseEmoji(t);
            //tp.setHashtags(tp.parseHashtag(t));
            //tp.setText(tp.parseText(t));
            //tp.setAtUsers(tp.parseAtUser(t));
            //tp.setEmojis(tp.parseEmoji(t));
            //System.out.printf("Tweet: %-150s Hashtags: %-25s Text: %-150s  AtUser: %-25s Emojis: \n", status.getText(), Arrays.toString(tp.getHashtags()), tp.getText(), Arrays.toString(tp.getAtUsers()));//,  Arrays.toString(tp.getAtUsers()), Arrays.toString(tp.getEmojis()
            //calculateSentiment(tp.getText());
        }

        return tweets;
        //Update Status test
        //Just to see if connection to Twitter is working
        //String latestStatus = "\uD83D\uDCF7";
        //Status st = twitter.updateStatus(latestStatus);
        //System.out.println("Successfully updated the status to [" + st.getText() + "].");
    }
}