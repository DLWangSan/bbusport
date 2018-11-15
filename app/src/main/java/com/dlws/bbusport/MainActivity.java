package com.dlws.bbusport;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dlws.bbusport.activity.first_page.FirstPageActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText idEditText;
    private EditText pwdEditText;
    private CheckBox remPwdCheckBox;

    private String id;
    private String pwd;
    static Boolean flag;    //判断账号密码匹配
    static Users usersNow;      //当前用户

    private boolean isHideFirst = true;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //状态栏透明
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();

        loginButton = findViewById(R.id.ok);
        idEditText=findViewById(R.id.id);
        pwdEditText=findViewById(R.id.pwd);
        remPwdCheckBox=findViewById(R.id.remembPwd);

        id=idEditText.getText().toString();
        pwd=pwdEditText.getText().toString();
        remPwdCheckBox.setChecked(true);

        /*加载本地保存的账号密码*/
        SharedPreferences sPreferences=getSharedPreferences("users", MODE_PRIVATE);
        String idSaved=sPreferences.getString("id","");
        String pwdSaved = sPreferences.getString("pwd","");
        idEditText.setText(idSaved);
        pwdEditText.setText(pwdSaved);



        /*记住密码小眼睛，余童提供代码，已完成*/
        final Drawable[] drawables = pwdEditText.getCompoundDrawables();
        final int eyeWidth = drawables[2].getBounds().width();
        final Drawable drawableEyeOpen = getResources().getDrawable(R.drawable.zhengyan);
        drawableEyeOpen.setBounds(drawables[2].getBounds());
        pwdEditText.setOnTouchListener(new View.OnTouchListener() {
                                           @Override
                                           public boolean onTouch(View v, MotionEvent event) {
                                               if (event.getAction() == MotionEvent.ACTION_UP) {
                                                   float et_pwdMinX = v.getWidth() - eyeWidth - pwdEditText.getPaddingRight();
                                                   float et_pwdMaxX = v.getWidth();
                                                   float et_pwdMinY = 0;
                                                   float et_pwdMaxY = v.getHeight();
                                                   float x = event.getX();
                                                   float y = event.getY();
                                                   if (x < et_pwdMaxX && x > et_pwdMinX && y > et_pwdMinY && y < et_pwdMaxY) {
                                                       isHideFirst = !isHideFirst;
                                                       if (isHideFirst) {
                                                           pwdEditText.setCompoundDrawables(null,
                                                                   null,
                                                                   drawables[2], null);

                                                           pwdEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                                       } else {
                                                           pwdEditText.setCompoundDrawables(null, null, drawableEyeOpen, null);
                                                           pwdEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                                       }
                                                   }
                                               }
                                               return false;
                                           }

                                       }
        );



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                id=idEditText.getText().toString();
                pwd=pwdEditText.getText().toString();

                /*判断记住密码是否被勾选*/
                if(remPwdCheckBox.isChecked())
                {
                    remberPwd(id,pwd);
                }else{
                    deletePwd();
                }


//               通过验证则跳转
                try {
                    if(checkUsers()){
                        Intent intent=new Intent(MainActivity.this,FirstPageActivity.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, usersNow.getName()+",欢迎！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /*勾选记住密码跳转的方法
    * 实际操作为通过SharedPreferences
    * 将账号密码保存到user文件中
    * 已完成*/
    private void remberPwd(String id1, String pwd1){
        SharedPreferences.Editor editor = getSharedPreferences("users", MODE_PRIVATE).edit();
        editor.putString("id", id1);
        editor.putString("pwd", pwd1);
        editor.apply();
    }
    /*不勾选记住密码跳转的方法
     * 实际操作为通过SharedPreferences
     * 将user文件中信息清空
     * 已完成*/
    private void deletePwd(){
        SharedPreferences.Editor editor = getSharedPreferences("users", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
    /*待完善*/
    protected boolean checkUsers() throws InterruptedException {
//        test();

        Thread thread=new Thread(){
            @Override
            public void run() {
                try{
                    OkHttpClient client =new OkHttpClient();
                    Request request =new Request.Builder()
                            .url("http://dlws.show.0552web.com/androidtest/bbusportUsers.json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSON(responseData);


                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        };

        thread.start();
        /*下面的延迟函数是为了给子线程留下充足的时间，只是临时对策。正确的
        * 处理逻辑应该是判断子线程是否完成操作。若果没有，加载动画，如果已经
        * 完成，则继续进行。这部分有时间继续完善！*/
//        try{
//            Thread.sleep(1000);}catch (Exception e){e.printStackTrace();
//        }
        thread.join();




        if (flag) {
            return true;
        } else {
            return false;
        }
    }

    /*新线程发起网络请求，已完成*/
    protected void test()
    {



//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    OkHttpClient client =new OkHttpClient();
//                    Request request =new Request.Builder()
//                            .url("http://dlws.show.0552web.com/androidtest/bbusportUsers.json")
//                            .build();
//                    Response response = client.newCall(request).execute();
//                    String responseData = response.body().string();
//                    parseJSON(responseData);
//
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }

    /*解析JSON数据，已完成*/
    private void parseJSON(String responseData) {
        Gson gson = new Gson();
        List<Users> usersList = gson.fromJson(responseData, new TypeToken<List<Users>>() {
        }.getType());

        for (Users users : usersList) {
            if (users.getId().equals(id)) {
                if (users.getPwd().equals(pwd)) {
                    flag = true;
                    usersNow=users;
                    break;
                } else {
                    flag = false;
                    continue;
                }

            } else {
                flag = false;
                continue;
            }
        }
    }



}
