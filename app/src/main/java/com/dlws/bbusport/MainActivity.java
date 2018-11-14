package com.dlws.bbusport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.dlws.bbusport.activity.first_page.FirstPageActivity;


public class MainActivity extends AppCompatActivity {
    private Button mapButton;
    private Button firstPageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapButton=findViewById(R.id.map);
        firstPageButton=findViewById(R.id.first_page);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, com.dlws.bbusport.activity.map_page.MapActivity.class);
                startActivity(intent);
            }
        });
        firstPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,FirstPageActivity.class);
                startActivity(intent);
            }
        });
    }

}
