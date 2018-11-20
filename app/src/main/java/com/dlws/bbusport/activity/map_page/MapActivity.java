package com.dlws.bbusport.activity.map_page;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.trace.TraceLocation;
import com.amap.api.trace.TraceOverlay;
import com.amap.api.trace.TraceStatusListener;
import com.dlws.bbusport.R;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.trace.LBSTraceClient;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements TraceStatusListener {


    private AMap aMap;
    private MapView mapView;
    private Button stopButton;
    private Polygon polygon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //状态栏透明
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();


        mapView=(MapView)findViewById(R.id.amapView);
        mapView.onCreate(savedInstanceState);

        aMap = mapView.getMap();
        aMap.setMyLocationEnabled(true);

//        startTrace();

        /*初始化*/
        setUpMap();
        stopButton=findViewById(R.id.stop);
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.getUiSettings().setRotateGesturesEnabled(false);
            aMap.getUiSettings().setZoomControlsEnabled(false);
        }

//        //记录轨迹
//        runButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LBSTraceClient lbsTraceClient = LBSTraceClient.getInstance(getBaseContext());
//            }
//        });

//        stopButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                stopTrace();
//            }
//        });


        List<String> permissionList = new ArrayList<>();

        if(ContextCompat.checkSelfPermission(MapActivity.this, Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if(ContextCompat.checkSelfPermission(MapActivity.this, Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if(ContextCompat.checkSelfPermission(MapActivity.this, Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if(!permissionList.isEmpty()){
            String[] perssions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MapActivity.this,perssions,1);
        }else{
            requestLocation();
        }
    }
    public void requestLocation(){
        initLocation();
    }
    private void initLocation(){
        //设置基本样式
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        //去除圆圈（设为透明色）
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色

        //去除logo
        aMap.getUiSettings() .setLogoBottomMargin(-50);
//        myLocationStyle.interval(1000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);

        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));

    }
    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if (grantResults.length>0)
                {
                    for(int result: grantResults){
                        if(result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this, "缺少必要权限！", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this,"未知错误！",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }


    @Override
    public void onTraceStatus(List<TraceLocation> list, List<LatLng> list1, String s) {

    }

    private void setUpMap() {
//        aMap.setOnMapClickListener(this);
        // 绘制一个长方形
        PolygonOptions pOption = new PolygonOptions();
        pOption.add(new LatLng(32.8931220000, 117.4246420000));
        pOption.add(new LatLng(32.8932390000, 117.4265950000));
        pOption.add(new LatLng(32.8913790000, 117.4267880000));
        pOption.add(new LatLng(32.8911900000, 117.4248730000));

//        pOption.add(new LatLng(32.8931220000, 117.4246420000));

        polygon = aMap.addPolygon(pOption.strokeWidth(4)
                .strokeColor(Color.argb(50, 1, 50, 1))
                .fillColor(Color.argb(50, 1, 50, 1)));


        PolygonOptions pOption2 = new PolygonOptions();
        pOption2.add(new LatLng(32.8886490000, 117.4250390000));
        pOption2.add(new LatLng(32.8868830000, 117.4252640000));
        pOption2.add(new LatLng(32.8867890000, 117.4240950000));
        pOption2.add(new LatLng(32.8885720000, 117.4238750000));

//        pOption.add(new LatLng(32.8931220000, 117.4246420000));

        polygon = aMap.addPolygon(pOption2.strokeWidth(4)
                .strokeColor(Color.argb(50, 1, 50, 1))
                .fillColor(Color.argb(50, 1, 50, 1)));

//
    }
}
