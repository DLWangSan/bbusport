package com.dlws.bbusport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

    private String id;
    private String pwd;
    static Boolean flag;    //判断账号密码匹配
    static Users usersNow;      //当前用户

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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=idEditText.getText().toString();
                pwd=pwdEditText.getText().toString();
//                Toast.makeText(MainActivity.this, "正在登录...", Toast.LENGTH_SHORT).show();
                if(checkUsers()){
                    Intent intent=new Intent(MainActivity.this,FirstPageActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, usersNow.getName()+",欢迎！", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /*待完善*/
    protected boolean checkUsers(){
        test();
        /*下面的延迟函数是为了给子线程留下充足的时间，只是临时对策。正确的
        * 处理逻辑应该是判断子线程是否完成操作。若果没有，加载动画，如果已经
        * 完成，则继续进行。这部分有时间继续完善！*/
        try{
            Thread.sleep(300);}catch (Exception e){e.printStackTrace();
        }
        if(flag){
            return true;
        }else {
            return false;
        }
    }

    /*新线程发起网络请求，已完成*/
    protected void test()
    {
        new Thread(new Runnable() {
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
        }).start();

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
