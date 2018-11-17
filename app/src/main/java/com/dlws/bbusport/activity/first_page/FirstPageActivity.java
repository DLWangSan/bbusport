package com.dlws.bbusport.activity.first_page;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.dlws.bbusport.R;
import com.dlws.bbusport.fragment.FragmentMine;
import com.dlws.bbusport.fragment.FragmentNews;
import com.dlws.bbusport.fragment.FragmentSport;
import com.dlws.bbusport.view.MainBottomView;

import org.zackratos.ultimatebar.UltimateBar;

public class FirstPageActivity extends AppCompatActivity {

    private static final String FRAGMENT_BASE_TAG = "base_fragment";
    private MainBottomView mBottomView;
    private int mSelectPosition = 1;
    private MainBottomView.OnBottomChooseListener mBottomChooseListener = new BottomChooseListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        //状态栏透明
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();

        initView();

        selectPositionFragment(mSelectPosition);
    }

    private void initView() {
        mBottomView = findViewById(R.id.main_bottom_tab);
        mBottomView.setBottomCLickListener(mBottomChooseListener);
    }

    //初始化一个Fragment，并将其添加到指定位置
    private void initFragment(int position){

        Fragment fragment;
        switch (position){

            case 0:
                fragment = new FragmentNews();
                break;

            case 1:
                fragment =  new FragmentSport();
                break;

            default:
                fragment = new FragmentMine();
                break;
        }
        getSupportFragmentManager().beginTransaction().add(R.id.main_content_ll,fragment,getFragmentTagByPosition(position)).commit();
    }

    //得到指定Tag的Fragment
    private Fragment getFragmentByTag(int position){
        return  getSupportFragmentManager().findFragmentByTag(getFragmentTagByPosition(position));
    }
    //得到每个Fragment对应的tag
    private String getFragmentTagByPosition(int position){

        return FRAGMENT_BASE_TAG + position;
    }

    private void selectPositionFragment(int position){
        Fragment selectFragment = getFragmentByTag(position);

        if (selectFragment == null){
            initFragment(position);
        } else {
            getSupportFragmentManager().beginTransaction().show(selectFragment).commit();
        }
    }

    private class BottomChooseListener implements MainBottomView.OnBottomChooseListener {

        @Override
        public void onClick(int oldPosition, int selectPosition) {

            Fragment nowFragment = getFragmentByTag(oldPosition);
            getSupportFragmentManager().beginTransaction().hide(nowFragment).commit();
            selectPositionFragment(selectPosition);

            mSelectPosition = selectPosition;
        }
    }
}
