package com.codepath.apps.TwitterClient.models;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by hachong on 12/13/15.
 */



public class Tweet {

    private String body;
    private long uid;
    private User user;



    private String createAt;



    private String relativeTimeStamp;



    public static Tweet fromJSON(JSONObject jsonObject){
    Tweet tweet = new Tweet();
        try {

            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.relativeTimeStamp = getRelativeTimeAgo(tweet.createAt);
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray){
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        for(int i = 0; i < jsonArray.length();i++){
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if(tweet != null){
                    tweets.add(tweet);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }


        return tweets;
    }


    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreateAt() {
        return createAt;
    }

    public User getUser() {
        return user;
    }

    public String getRelativeTimeStamp() {
        return relativeTimeStamp;
    }

    private static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
