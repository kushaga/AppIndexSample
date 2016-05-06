package com.akosha.sample1.appindexsample;

import android.app.Application;

/**
 * Created by kushagarlall on 01/05/16.
 */
public class AppIndexApplication extends Application {
    private static AppIndexApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static AppIndexApplication getInstance(){
        return instance;
    }
}
