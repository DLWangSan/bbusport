package com.dlws.bbusport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dlws.bbusport.activity.first_page.FirstPageActivity;

import org.zackratos.ultimatebar.UltimateBar;


public class MainActivity extends AppCompatActivity {
    private Button login;
    private EditText id;
    private EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //状态栏透明
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();

        login = findViewById(R.id.ok);
        id=findViewById(R.id.id);
        pwd=findViewById(R.id.pwd);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNumOK()){
                    Intent intent = new Intent(MainActivity.this,FirstPageActivity.class);
                    startActivity(intent);
                }else{
                    //
                }
            }
        });
    }
    protected boolean checkNumOK(){
        String idreal;
        String pwdreal;
        idreal = id.getText().toString();
        pwdreal=pwd.getText().toString();
        long idd= Long.parseLong(idreal);
        long pwdd=Long.parseLong(pwdreal);
        if((idd>=2031001) && idd<=2031040)
        {
            if (pwdd==idd-2030000)
            {
                return true;
            }else{
                Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            Toast.makeText(this, "账号不存在", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

}
