package com.codepath.apps.TwitterClient.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hachong on 12/13/15.
 */
public class User {

    private long uid;
    private String name;
    private String screenName;
    private String profileImageUrl;


    public static User fromJSON(JSONObject jsonObject){
        User user = new User();
        try{
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getLong("id");
            user.screenName= jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url");

        }catch(JSONException e){
            e.printStackTrace();
        }


        return user;
    }


    public long getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }


}
