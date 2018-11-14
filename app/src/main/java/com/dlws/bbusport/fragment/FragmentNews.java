package com.dlws.bbusport.fragment;


import android.graphics.Rect;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlws.bbusport.R;
import com.dlws.bbusport.fragment.recycler.FragmentLoadRecycler;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页面---展示信息的Fragment
 */
public class FragmentNews extends android.support.v4.app.Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

//    @Override
//    protected RequestResult requestDataInBackground(boolean isRefresh) {
//        return null;
//    }
//
//    @Override
//    protected int getItemSize() {
//        return 0;
//    }
//
//    @Override
//    protected void setItemOffset(Rect outRect, int position) {
//
//    }
//
//    @Override
//    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent) {
//        return null;
//    }
//
//    @Override
//    protected void bindingViewHolder(RecyclerView.ViewHolder holder, int position) {
//
//    }
}
