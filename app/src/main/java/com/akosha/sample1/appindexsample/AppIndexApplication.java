package com.akosha.sample1.appindexsample;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by kushagarlall on 01/05/16.
 */
public class AppIndexApplication extends Application {

    //    private static final String DEFAULT_URL = "helpchat://helpchat/";
    public static final Uri BASE_APP_URI = Uri.parse("android-app://com.akosha.sample1.appindexsample/http/abc.kush.in/");

    public static final Uri BASE_APP_URI1 = Uri.parse("android-app://com.akosha.sample1.appindexsample/helpchat/helpchat/cabs");

    public static final Uri BASE_APP_URI2 = Uri.parse("android-app://com.akosha.sample1.appindexsample/helpchat/helpchat/cabs");
//    public static final Uri BASE_APP_URI2 = Uri.parse(DEFAULT_URL);

    private static AppIndexApplication instance;

    Set<String> cabSet = new HashSet<String>();
    Set<String> restaurantSet = new HashSet<String>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        cabSet.add("olacab");
        cabSet.add("ubercab");
        cabSet.add("recharge");
        cabSet.add("quick recharge");
        restaurantSet.add("zomato");
        restaurantSet.add("seashell");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("Application", "on terminate");
        stopService(new Intent(this, BgService.class));
    }

    public static AppIndexApplication getInstance() {
        return instance;
    }
}
