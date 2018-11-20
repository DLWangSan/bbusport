package com.dlws.bbusport.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlws.bbusport.R;
import com.dlws.bbusport.Users;
import com.dlws.bbusport.activity.map_page.MapActivity;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.dlws.bbusport.MainActivity.usersNow;

/**
 "我的"页面的fragment
 * */
public class FragmentMine extends android.support.v4.app.Fragment {

    private TextView nameText;
    private TextView xuehaoText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameText=view.findViewById(R.id.name);
        xuehaoText=view.findViewById(R.id.xuehao);


        nameText.setText(usersNow.getName());
        xuehaoText.setText(usersNow.getId());


    }



}
