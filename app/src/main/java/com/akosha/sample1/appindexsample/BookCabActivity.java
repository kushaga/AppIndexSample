package com.akosha.sample1.appindexsample;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class BookCabActivity extends AppCompatActivity {

    private static final String TAG = BookCabActivity.class.getSimpleName();
    private GoogleApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_cab);
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private List<Action> getCabTags() {
        ArrayList<Action> actionArrayList = new ArrayList<>();

        for (String cabsTag : AppIndexApplication.getInstance().cabSet) {
            final Uri APP_URI = AppIndexApplication.getInstance().BASE_APP_URI2.buildUpon().appendPath(cabsTag).build();
            Action viewAction = Action.newAction(Action.TYPE_ADD, cabsTag, APP_URI);
            actionArrayList.add(viewAction);
        }

        return actionArrayList;
    }

    private void tagBookCab() {
        for (Action action : getCabTags()) {
            AppIndex.AppIndexApi.start(mClient, action);
        }
    }

    private void unTagBookCab() {
        for (Action action : getCabTags()) {
            AppIndex.AppIndexApi.end(mClient, action);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mClient.connect();
        tagBookCab();
    }

    @Override
    protected void onStop() {
        unTagBookCab();
        mClient.disconnect();
        super.onStop();
    }
}


//service -- to get all the uri's and index in the googleAPIClient