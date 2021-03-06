package com.codepath.apps.twitterclient.network;

import android.content.Context;
import android.util.Log;

import com.codepath.apps.twitterclient.config.TwitterConfig;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;
/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1";
	public static final String REST_CONSUMER_KEY = TwitterConfig.TWITTER_KEY;
	public static final String REST_CONSUMER_SECRET = TwitterConfig.TWITTER_SECRET;
    public static final String REST_CALLBACK_URL = "oauth://cptwitterclient";
    
	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}



    public void getLoggedInUser(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        RequestParams params = new RequestParams();
        getClient().get(apiUrl, params, handler);
    }


    public void getOlderTimelineEntries(AsyncHttpResponseHandler handler, long max_id) {
        getOlderTimelineEntries(handler, max_id, 25);
    }

    public void getOlderTimelineEntries(AsyncHttpResponseHandler handler, long max_id, int count) {
        RequestParams params = new RequestParams();
        params.put("count", count);
        params.put("max_id", max_id);
        getTimelineEntries(params, handler);
    }

    public final int DEFAULT_QTY = 25;

    public void getNewTimelineEntries(AsyncHttpResponseHandler handler) {
        getNewTimelineEntries(handler, DEFAULT_QTY);
    }

    public void getNewTimelineEntries(AsyncHttpResponseHandler handler, int count) {
        RequestParams params = new RequestParams();
        params.put("count", count);
        getTimelineEntries(params, handler);
    }

    public void getNewTimelineEntries(AsyncHttpResponseHandler handler, long since_id) {
        getNewTimelineEntries(handler, since_id, DEFAULT_QTY);
    }


    public void getNewTimelineEntries(AsyncHttpResponseHandler handler, long since_id, int count) {
        RequestParams params = new RequestParams();
        params.put("count", count);
        params.put("since_id", since_id);
        getTimelineEntries(params, handler);
    }

    public void postNewTweet(String status, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();

        params.put("status", status);

        Log.d("client", "POST: " + apiUrl);
        Log.d("client", "Params: " + params.toString());
        getClient().post(apiUrl, params, handler);
    }

    // private

    private void getTimelineEntries(RequestParams params, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        Log.d("client", "GET: " + apiUrl);
        Log.d("client", "Params: " + params.toString());
        getClient().get(apiUrl, params, handler);
    }



		// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}


	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}