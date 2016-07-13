package com.akosha.sample1.appindexsample;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.util.List;

/**
 * Created by kushagarlall on 12/07/16.
 */
public class DeepLinkIntentFactory {

    private static final String TAG = DeepLinkIntentFactory.class.getSimpleName();
    private static final DeepLinkIntentFactory instance = new DeepLinkIntentFactory();

    private DeepLinkIntentFactory() {

    }

    public static DeepLinkIntentFactory getInstance() {
        return instance;
    }

    public Intent parseAndGetIntent(Uri uri) {

        //associate deeplinks with activity

        Log.d(TAG, uri.toString());

        List<String> pathSegments = uri.getPathSegments();

        Intent intent = null;
        String tagKey = pathSegments.get(0).toLowerCase();

        Log.d(TAG, "tagKey:" + tagKey);

        if (AppIndexApplication.getInstance().cabSet.contains(tagKey)) {
            intent = new Intent(AppIndexApplication.getInstance(), BookCabActivity.class);
        } else if (AppIndexApplication.getInstance().restaurantSet.contains(tagKey)) {
            intent = new Intent(AppIndexApplication.getInstance(), RestaurantActivity.class);
        } else {
            intent = new Intent(AppIndexApplication.getInstance(), BookCabActivity.class);
        }

        return intent;
    }

}
