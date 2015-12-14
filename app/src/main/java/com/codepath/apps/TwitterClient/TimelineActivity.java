package com.codepath.apps.TwitterClient;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.TwitterClient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private SwipeRefreshLayout swipeContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        tweets = new ArrayList<Tweet>();
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {

            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                populateTimeline(aTweets.lastId);
                return true;
            }
        });


        aTweets = new TweetsArrayAdapter(this,tweets);
        lvTweets.setAdapter(aTweets);
        client = TwitterApplication.getRestClient();
        populateTimeline(0);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tweets = new ArrayList<Tweet>();
                populateTimeline(0);
                swipeContainer.setRefreshing(false);
                aTweets.notifyDataSetChanged();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    protected void onResume() {
        System.out.println("onResume called");
        super.onResume();
        tweets = new ArrayList<Tweet>();
        if(lvTweets == null) {
            lvTweets = (ListView) findViewById(R.id.lvTweets);
            client = TwitterApplication.getRestClient();
        }
        aTweets = new TweetsArrayAdapter(this,tweets);
        lvTweets.setAdapter(aTweets);
        populateTimeline(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public void onAddTweet(MenuItem mi){
        Intent i = UpdateStatusActivity.newIntent(TimelineActivity.this);
        startActivity(i);
    }

    //send API request to get the timeline Json
    // fill the listview by creating the tweet object from the Json
    private void populateTimeline(long lastId){
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            //Success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                aTweets.addAll(Tweet.fromJSONArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject response) {

            }
        },lastId);
    }
}
