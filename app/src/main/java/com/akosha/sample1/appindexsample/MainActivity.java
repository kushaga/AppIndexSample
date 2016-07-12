package com.akosha.sample1.appindexsample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.akosha.sample1.appindexsample.fragment.MainFragment;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainFragment.MainDataCallBack {

    private GoogleApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        MainFragment fragment = MainFragment.newInstance();
        attach(fragment);

        BgService.startBgService(this);
    }


    public void attach(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        String fragmentName = fragment.getClass().getSimpleName();
        Fragment fragmentById = fragmentManager.findFragmentById(R.id.main_fra_lay);

        if (null == fragmentById) {
            fragmentManager.beginTransaction().add(R.id.main_fra_lay, fragment).addToBackStack(fragmentName).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.main_fra_lay, fragment).addToBackStack(fragmentName).commit();
        }
    }

    @Override
    public void getMsg(String msg) {
        //// TODO: 01/05/16 something useful
        Intent intent = new Intent(this, BookCabActivity.class);
        startActivity(intent);
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

    private List<Action> getRestaurantTags() {
        ArrayList<Action> actionArrayList = new ArrayList<>();

        for (String cabsTag : AppIndexApplication.getInstance().restaurantSet) {
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

    private void tagRestaurant() {
        for (Action action : getRestaurantTags()) {
            AppIndex.AppIndexApi.start(mClient, action);
        }
    }

    private void unTagBookCab() {
        for (Action action : getCabTags()) {
            AppIndex.AppIndexApi.end(mClient, action);
        }
    }

    private void unTagRestaurant() {
        for (Action action : getRestaurantTags()) {
            AppIndex.AppIndexApi.end(mClient, action);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mClient.connect();
        tagBookCab();
        tagRestaurant();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unTagBookCab();
        unTagRestaurant();
        mClient.disconnect();
    }
}
