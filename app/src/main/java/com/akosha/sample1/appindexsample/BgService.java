package com.akosha.sample1.appindexsample;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;


public class BgService extends Service {

    private static final String TAG = BgService.class.getSimpleName();
    private GoogleApiClient mClient;

//    public BgService() {
//        super("BgService");
//    }

//    @Override
//    protected void onHandleIntent(Intent intent) {
//        if (intent != null) {
//            final String action = intent.getAction();
//            //get data and do indexing
//        }
//        mClient.connect();
//        tag();
//    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void startBgService(Context context) {
//        mClient = new GoogleApiClient.Builder(context).addApi(AppIndex.API).build();
        context.startService(new Intent(context, BgService.class));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        mClient.connect();
        tag();
//        unTag();
//        mClient.disconnect();
    }

    private List<Action> getMultipleTags() {
        ArrayList<Action> actionArrayList = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            titles.add("helpchatUber " + i);
        }

        for (int i = 0; i < 10; i++) {
            final Uri APP_URI = AppIndexApplication.getInstance().BASE_APP_URI.buildUpon().appendPath(titles.get(i)).build();
            Action viewAction = Action.newAction(Action.TYPE_ADD, titles.get(i), APP_URI);
            actionArrayList.add(viewAction);
        }

        return actionArrayList;
    }

    //send tags to deepLinkhandler activity , and then tag it from there


    private void tag() {
        List<Action> actions = getMultipleTags();

        Log.d(TAG, "tag start");

        for (int i = 0; i < 10; i++) {
            AppIndex.AppIndexApi.start(mClient, actions.get(i));
        }
    }

    private void unTag() {
        List<Action> actions = getMultipleTags();

        Log.d(TAG, "tag stop");

        for (int i = 0; i < 10; i++) {
            AppIndex.AppIndexApi.end(mClient, actions.get(i));
        }
    }

//    @Override
//    public void onStart(Intent intent, int startId) {
//        super.onStart(intent, startId);
//
//    }
//


    @Override
    public void onDestroy() {
        Log.d(TAG, "on destroy");
        unTag();
        mClient.disconnect();
        super.onDestroy();
    }

}

