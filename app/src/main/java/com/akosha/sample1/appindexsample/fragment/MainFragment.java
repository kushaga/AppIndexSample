package com.akosha.sample1.appindexsample.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.akosha.sample1.appindexsample.R;

/**
 * Created by kushagarlall on 01/05/16.
 */
public class MainFragment extends BaseFragment {

    private Button button;
    private MainDataCallBack callBack;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_layout, container, false);
        callBack = (MainDataCallBack) getActivity();
        Button but = (Button) view.findViewById(R.id.main_frag_butt);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send data to activity
                callBack.getMsg("Main Fragment Callback");
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface MainDataCallBack {
        public void getMsg(String msg);
    }
}

