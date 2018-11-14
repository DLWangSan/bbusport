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

/**
 "我的"页面的fragment
 * */
public class FragmentMine extends android.support.v4.app.Fragment {

    private TextView mSportMessageTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSportMessageTv = (TextView) view.findViewById(R.id.mine_sport_message_tv);
        mSportMessageTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*显示所有运动记录的Activity*/
//                Intent intent = new Intent(getActivity(), ActivitySportList.class);
//                startActivity(intent);
            }
        });
    }

}
