package com.akosha.sample1.appindexsample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by kushagarlall on 01/05/16.
 */
public class DeepLinkHandlerActivity extends AppCompatActivity {

    private GoogleApiClient mClient;
    private static final Uri BASE_APP_URI = Uri.parse("android-app://com.akosha.sample1.appindexsample/http/www.kush.in/");
    private String action;
    private Uri mUrl;
    private String mTitle;
    private String mDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getData();
        Log.d("TAG","tag");
        Log.e("TAG",uri.toString());
        mUrl = uri;
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        action = getIntent().getAction();

        mTitle = "SampleTitle";
        mDescription = "SampleDescription";

        Intent intent = DeepLinkFactory.getIntent(uri);
        if (intent != null) {
            startActivity(intent);
            finish();
        }
    }


    public Action getAction() {
        Thing object = new Thing.Builder()
                .setName(mTitle)
                .setDescription(mDescription)
                .setUrl(mUrl)
                .build();

        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        mClient.connect();
        final Uri APP_URI = BASE_APP_URI.buildUpon().appendPath(DeepLinkFactory.FIRST).build();

        Action viewAction = Action.newAction(Action.TYPE_VIEW, mTitle, APP_URI);

        // Call the App Indexing API view method
        PendingResult<Status> result = AppIndex.AppIndexApi.start(mClient, viewAction);

        result.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                if (status.isSuccess()) {
                    Log.d("TAG", "App Indexing API: Recorded recipe "
                            + mTitle + " view successfully.");
                } else {
                    Log.e("TAG", "App Indexing API: There was an error recording the recipe view."
                            + status.toString());
                }
            }
        });
    }

    @Override
    public void onStop() {
//        AppIndex.AppIndexApi.end(mClient, getAction());
//        mClient.disconnect();

        final Uri APP_URI = BASE_APP_URI.buildUpon().appendPath(DeepLinkFactory.FIRST).build();

        Action viewAction = Action.newAction(Action.TYPE_VIEW, mTitle, APP_URI);
        PendingResult<Status> result = AppIndex.AppIndexApi.end(mClient, viewAction);

        result.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                if (status.isSuccess()) {
                    Log.d("TAG", "App Indexing API: Recorded recipe "
                            + mTitle + " view end successfully.");
                } else {
                    Log.e("TAG", "App Indexing API: There was an error recording the recipe view."
                            + status.toString());
                }
            }
        });

        mClient.disconnect();
        super.onStop();
    }
}
