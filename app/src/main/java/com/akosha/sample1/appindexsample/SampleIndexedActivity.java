package com.akosha.sample1.appindexsample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class SampleIndexedActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final Uri BASE_APP_URI = Uri.parse("android-app://com.akosha.sample1.appindexsample/http/abc.kush.in/");
    private GoogleApiClient mClient;

    private Uri mUrl;
    private String mTitle = "indexedTitle";
    private String mTitle1 = "indexedTitle1";
    private String mDescription = "indexedDesc";
    private TextView textView;
    private String indexStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_indexed);
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.APP_INDEX_API).build();
        textView = (TextView) findViewById(R.id.textIndexed);
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();
        String data = intent.getDataString();
        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            String title = data.substring(data.lastIndexOf("/") + 1);
            indexStr = title;
            showText(title);
        }
    }

    public void showText(String text) {
        if (text != null) {
            textView.setText(text);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mClient.connect();

        if (indexStr != null) {
            final String TITLE = mTitle;
            final Uri APP_URI = BASE_APP_URI.buildUpon().appendPath(indexStr).build();

            Action viewAction = Action.newAction(Action.TYPE_VIEW, TITLE, APP_URI);
            PendingResult<Status> result = AppIndex.AppIndexApi.start(mClient, viewAction);

            result.setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    if (status.isSuccess()) {
                        Log.d(TAG, "App Indexing API: Recorded recipe "
                                + mTitle + " view successfully.");
                    } else {
                        Log.e(TAG, "App Indexing API: There was an error recording the recipe view."
                                + status.toString());
                    }
                }
            });

        }

    }

    @Override
    protected void onStop() {
        if (indexStr != null) {
            final String TITLE = mTitle;
            final Uri APP_URI = BASE_APP_URI.buildUpon().appendPath(indexStr).build();

            Action viewAction = Action.newAction(Action.TYPE_VIEW, TITLE, APP_URI);
            PendingResult<Status> result = AppIndex.AppIndexApi.end(mClient, viewAction);

            result.setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    if (status.isSuccess()) {
                        Log.d(TAG, "App Indexing API: Recorded recipe "
                                + mTitle + " view end successfully.");
                    } else {
                        Log.e(TAG, "App Indexing API: There was an error recording the recipe view."
                                + status.toString());
                    }
                }
            });

            mClient.disconnect();
            super.onStop();

        }
    }
}
