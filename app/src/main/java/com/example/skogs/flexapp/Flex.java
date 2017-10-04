package com.example.skogs.flexapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by skogs on 2017-08-09.
 */
public class Flex extends Fragment {
    private TextView flexText;
    private MainActivity mA;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.flex, container, false);
        mA = (MainActivity)getActivity();

        flexText = (TextView)view.findViewById(R.id.flexTime);
        flexText.setText(mA.fH.getFlexData(this.getContext()));

        return view;
    }

    @Override
    public void onResume(){
        flexText.setText(mA.fH.getFlexData(this.getContext()));
        super.onResume();
    }
}
