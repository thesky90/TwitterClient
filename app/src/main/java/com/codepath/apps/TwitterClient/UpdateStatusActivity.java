package com.codepath.apps.TwitterClient;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

public class UpdateStatusActivity extends AppCompatActivity {

    EditText etStatus;
    TwitterClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);
        etStatus = (EditText) findViewById(R.id.etStatus);
        client = TwitterApplication.getRestClient();
    }


    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, UpdateStatusActivity.class);
        return i;
    }

    public void onTweet(View v){
        String status = null;
        if(etStatus.getText() != null) {
            status = etStatus.getText().toString();
        }
        if(status != null || status.equals("")){
            client.updateStatus(new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    System.out.println("updated!");
                    Toast.makeText(getBaseContext(),"updated!",Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    System.out.println("failed!");
                }
            },status);
        }

    }
}
