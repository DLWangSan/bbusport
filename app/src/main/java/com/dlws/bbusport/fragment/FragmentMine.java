package com.dlws.bbusport.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private RelativeLayout ticeLayout;
    private RelativeLayout xuefenLayout;
    private RelativeLayout rewardLayout;
    private RelativeLayout clubLayout;
    private RelativeLayout myCompeteLayout;
    private RelativeLayout settingsLayout;


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

        ticeLayout=view.findViewById(R.id.rel_tccj);
        xuefenLayout=view.findViewById(R.id.rel_tyxf);
        rewardLayout=view.findViewById(R.id.rel_hjjl);
        clubLayout=view.findViewById(R.id.rel_club);
        myCompeteLayout=view.findViewById(R.id.rel_competition);
        settingsLayout=view.findViewById(R.id.rel_shezhi);


        ticeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
            }
        });
        xuefenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();
            }
        });
        rewardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(getContext(), "3", Toast.LENGTH_SHORT).show();
            }
        });
        clubLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(getContext(), "4", Toast.LENGTH_SHORT).show();
            }
        });
        myCompeteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(getContext(), "5", Toast.LENGTH_SHORT).show();
            }
        });

        settingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "6", Toast.LENGTH_SHORT).show();
            }
        });


    }



}
