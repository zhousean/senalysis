package com.wt.twitter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.semantria.core.Session;
import com.semantria.interfaces.ISerializer;
import com.semantria.objects.output.DocAnalyticData;
import com.semantria.objects.output.Document;
import com.semantria.serializer.JsonSerializer;


public class SentiAnalyzor
{
    private static final int TIMEOUT_BEFORE_GETTING_RESPONSE = 5000; //in millisec

	public List<AnalysisResult> analyze(String keywords, int maxTweets, String startDate, String endDate, String lang, String locale, GeoCode geocode)
	{
		// Use correct Semantria API credentias here
	    String key = "ecaf1cb4-87e3-4d85-b9fb-501e4969d891";
	    String secret = "5ee144ca-49a3-471d-a7bb-54b78cea9362";
		
	    List<String> initialTexts = searchTweets(keywords, maxTweets, startDate, endDate, lang, locale, geocode);
	    ArrayList<AnalysisResult> analysisResults = new ArrayList<AnalysisResult>();

		System.out.println("Total " + initialTexts.size() + " tweets are retrieved.");
	    System.out.println("Semantria service demo ...");
		
		// Creates JSON serializer instance
		ISerializer jsonSerializer = new JsonSerializer();
		// Initializes new session with the serializer object and the keys.
		Session session = Session.createSession(key, secret, jsonSerializer);
		session.setCallbackHandler(new CallbackHandler());
		for(String text : initialTexts)
		{
			String uid = UUID.randomUUID().toString();
			// Creates a sample document which need to be processed on Semantria
			// Queues document for processing on Semantria service
			if( session.queueDocument( new Document( uid, text )) == 202)
			{
				System.out.println("Document " + uid + " queued succsessfully.");
			}
		}
		System.out.println();
		try
		{
			// As Semantria isn't real-time solution you need to wait some time before getting of the processed results
            // In real application here can be implemented two separate jobs, one for queuing of source data another one for retreiving
            // Wait ten seconds while Semantria process queued document
			Thread.currentThread().sleep(TIMEOUT_BEFORE_GETTING_RESPONSE);
			List<DocAnalyticData> processed = new ArrayList<DocAnalyticData>();
			System.out.println("Requesting of the processed results...");
			
			while(processed.size() < initialTexts.size())
			{
				// Requests processed results from Semantria service
				List<DocAnalyticData> temp = session.getProcessedDocuments();
				processed.addAll(temp);
				if(processed.size() >= initialTexts.size()) break;
                System.out.println("waiting...");
				Thread.currentThread().sleep(TIMEOUT_BEFORE_GETTING_RESPONSE);
			}
			// Output results
			Float totalScore = new Float(0.0);
			for(DocAnalyticData doc : processed)
			{
				System.out.println("Document " + doc.getId() + ". Sentiment score: " + Float.toString(doc.getSentimentScore()));
				System.out.println("Document content: " + doc.getSummary());
				System.out.println("---------");
				totalScore += doc.getSentimentScore();
				
				AnalysisResult result = new AnalysisResult(doc.getSummary(), doc.getSentimentScore());
				analysisResults.add(result);
			}
			System.out.println("Average document sentiment score: " + totalScore);
			return analysisResults;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	private List<String> searchTweets(String keywords, int maxTweets, String startDate, String endDate, String lang, String locale, GeoCode geocode) {

	    ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("MlKJgaZNQ2lxiqTjdjNhzQ")
		  .setOAuthConsumerSecret("IxvMj1cFp3bKXxfqYg44HDhX1N6Iu9fhaiPyIDb16A")
		  .setOAuthAccessToken("37570084-OFgPdVAoVCxMi91nL4B7xqS4PgBLFRiPp9FuOWA")
		  .setOAuthAccessTokenSecret("vo61YMsAj7o4m2OkpyQQSW5Pc347e4q494wy4qm6DO0");
		
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		
		List<String> tweetTexts = new ArrayList<String>();
		try {
			Query query = new Query(keywords);

			if(startDate != null) query.setSince(startDate);
			if(endDate != null) query.setUntil(endDate);
			if(lang != null) query.setLang(lang);
			if(locale != null) query.setLocale(locale);
			
			if(geocode != null) {
				GeoLocation geoLocation = new GeoLocation(geocode.getLatitude(), geocode.getLongitude());
				query.setGeoCode(geoLocation, geocode.getRadius(), geocode.getUnit());
			}

			QueryResult result;
			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					System.out.println("@" + tweet.getUser().getScreenName()
							+ " - " + tweet.getCreatedAt() + " - " + tweet.getText());
					tweetTexts.add(tweet.getText());
				}
			} while ( ((query = result.nextQuery()) != null) && (tweetTexts.size() < maxTweets) );

		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
			System.exit(-1);
		}
		
		return tweetTexts;
	}
}
