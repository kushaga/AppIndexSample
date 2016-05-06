package com.akosha.sample1.appindexsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.akosha.sample1.appindexsample.fragment.MainFragment;

/**
 * Created by kushagarlall on 01/05/16.
 */
public class FirstActivity extends AppCompatActivity implements MainFragment.MainDataCallBack {

    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textMain);
        textView.setText("First Activity");
        MainFragment fragment = MainFragment.newInstance();
        attach(fragment);
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

    }
}
