package com.akosha.sample1.appindexsample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.akosha.sample1.appindexsample.fragment.MainFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.MainDataCallBack{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        //// TODO: 01/05/16 something useful
    }
}
