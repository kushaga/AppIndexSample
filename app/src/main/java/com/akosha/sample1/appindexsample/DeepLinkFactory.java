package com.akosha.sample1.appindexsample;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kushagarlall on 01/05/16.
 */
public class DeepLinkFactory {

    private static final String TAG = DeepLinkFactory.class.getSimpleName();
    public static final String FIRST = "appindexkushagar";
    private static final String SECOND = "secondappindexkushagar";
    private static final String FIRST1 = "helpkushagar";
    private static final String UBER = "cabuber5";


    public static Intent getIntent(Uri uri) {
        if (uri == null) {
            return defaultIntent();
        }

        Log.d(TAG, uri.toString());

        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments == null || pathSegments.size() == 0) {
            return defaultIntent();
        }
        Map<String, String> queryParams = getQueryParams(uri);
        switch (pathSegments.get(0).toLowerCase()) {
            case FIRST:
                return getIntent(FIRST);
            case SECOND:
                return getIntent(SECOND);
            case FIRST1:
                return getIntent(SECOND);
            case UBER:
                return getIntent(UBER);
            default:
                return getIntent(FIRST);
        }
    }


    public static Intent getIntent(String type) {
        switch (type) {
            case FIRST:
                return new Intent(AppIndexApplication.getInstance(), MainActivity.class);
            case SECOND:
                return new Intent(AppIndexApplication.getInstance(), FirstActivity.class);
            case UBER:
                return new Intent(AppIndexApplication.getInstance(),BookCabActivity.class);
            default:
                return new Intent(AppIndexApplication.getInstance(), MainActivity.class);
        }
    }

    public static Intent defaultIntent() {
        Intent intent = new Intent(AppIndexApplication.getInstance(), MainActivity.class);
        return intent;
    }

    private static Map<String, String> getQueryParams(Uri uri) {
        Map<String, String> map = new HashMap<>();
        Set<String> queryParamsName = uri.getQueryParameterNames();
        if (queryParamsName == null || queryParamsName.size() == 0) {
            return map;
        }
        Iterator<String> iterator = queryParamsName.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = uri.getQueryParameter(key);
            map.put(key, value);
        }
        return map;
    }
}