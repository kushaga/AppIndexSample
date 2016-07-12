package com.akosha.sample1.appindexsample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by kushagarlall on 12/07/16.
 */
public class NewDeepLinkHandlerActivity extends AppCompatActivity {

    private static final String TAG = NewDeepLinkHandlerActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d(TAG, "oncreate");
//        Uri uri = getIntent().getData();
////        Log.d(TAG, "tag");
//        Log.d(TAG, uri.toString());
//
//        Intent intent = DeepLinkIntentFactory.getInstance().parseAndGetIntent(uri);
//        if (intent != null) {
//            Log.d(TAG, "startActivity");
//            startActivity(intent);
//            finish();
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onstart");
        Uri uri = getIntent().getData();
        Log.d(TAG, uri.toString());

        Intent intent = DeepLinkIntentFactory.getInstance().parseAndGetIntent(uri);
        if (intent != null) {
            startActivity(intent);
            finish();
        }
    }
}
